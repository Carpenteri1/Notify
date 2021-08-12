package com.niclas.notify;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<NoteModel> listOfNotes = new ArrayList<NoteModel>();
    private ArrayList<String> listOfTitles;
    private SharedPreferences.Editor sharedPreferences;
    private final int EMPTYLIST = 0;
    private final int REQUESTCODE = 100;
    private final String FILENAME = "Notify";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        fabNewNoteClicked();
        listViewItemClicked();

    }

    private void listViewItemClicked() {

            ListView listView = findViewById(R.id.dynamicList);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView parentAdapter, View view,
                                        int position, long id) {
                    getSpecificNoteData(listOfNotes.get(position));
                }
            });
    }

    private void getSpecificNoteData(NoteModel theNote){
        Intent intent = new Intent(MainActivity.this,NoteInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("theNote",theNote);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }
    private void fabNewNoteClicked(){

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE)
        {
            listOfNotes.add((NoteModel) data.getExtras().getSerializable("newNote"));
            saveToFileSystem(FILENAME);
            Snackbar.make(findViewById(R.id.mainLayout),"Saved",2000).show();
            getData();

        }
    }

    private void getData(){
        if(listOfNotes.size() > EMPTYLIST){
            ListView listView = findViewById(R.id.dynamicList);
            listView.setAdapter(getTitlesFromNoteList());
        }else if(loadFromFileSystem(FILENAME)){
            getData();
        }
    }
    private boolean saveToFileSystem(String fileName) {
        boolean fileSaved = true;
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listOfNotes);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileSaved = false;
        }
        return fileSaved;
    }

    private boolean loadFromFileSystem(String fileName){
        boolean fileLoaded = true;
        try {
            FileInputStream fin = openFileInput(fileName);
            ObjectInputStream oin = new ObjectInputStream(fin);

            listOfNotes = (ArrayList<NoteModel>)oin.readObject();
            oin.close();
        } catch(Exception e) {
            e.printStackTrace();
            fileLoaded = false;
        }
        return fileLoaded;
    }

    private ArrayAdapter<String> getTitlesFromNoteList(){
        listOfTitles = new ArrayList<String>();
        for(int i =0 ;i < listOfNotes.size();i++) {
            listOfTitles.add(listOfNotes.get(i).getTitle());
        }
        return new ArrayAdapter<String>(com.niclas.notify.MainActivity.this,android.R.layout.simple_list_item_1,listOfTitles);
    }

}