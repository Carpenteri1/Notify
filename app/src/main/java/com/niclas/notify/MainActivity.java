package com.niclas.notify;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private ArrayList<NoteModel> listOfNotes = new ArrayList<NoteModel>();
    private ArrayList<String> listOfTitles;
    private SharedPreferences.Editor sharedPreferences;
    private final int EMPTYLIST = 0;
    private final int REQUESTCODEONE = 100;
    private final int REQUESTCODETWO = 200;
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
        startActivityForResult(intent, REQUESTCODETWO);
    }

    private void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        startActivityForResult(intent, REQUESTCODEONE);
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
        if(requestCode == REQUESTCODEONE)
        {
            NoteModel newNote = (NoteModel) data.getExtras().getSerializable("newNote");
            newNote.setId(listOfNotes.size()+1);
            listOfNotes.add(newNote);

            saveToFileSystem(FILENAME);
            Snackbar.make(findViewById(R.id.mainLayout),"Notify Saved",2000).show();
            getData();

        }
        else if(requestCode == REQUESTCODETWO){
            NoteModel noteModel = (NoteModel) data.getExtras().getSerializable("removeNote");
            for(int i = 0;i<=listOfNotes.size();i++){
                if(noteModel.getId() == listOfNotes.get(i).getId()){
                    listOfNotes.remove(listOfNotes.get(i));
                }
            }
            saveToFileSystem(FILENAME);
            Snackbar.make(findViewById(R.id.mainLayout),"Notify Removed",2000).show();
            getData();
        }
    }

    private void getData(){

        if(loadFromFileSystem(FILENAME)){
            ListView listView = findViewById(R.id.dynamicList);
            listView.setAdapter(getTitlesFromNoteList());
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