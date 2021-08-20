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
    private final int NO_REQUESTCODE = 0;

    private final int RESULTCODE_NEWNOTE = 100;
    private final int RESULTCODE_REMOVENOTE = 200;
    private final int RESULTCODE_REQUEST_EDIT = 300;
    private final int RESULTCODE_EDITNOTE = 400;

    private final String NEWNOTE_KEY = "newNote";
    private final String NOTE_KEY = "DataGet";

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
                    Intent intent = new Intent(MainActivity.this,NoteInfoActivity.class);
                    getSpecificNoteData(listOfNotes.get(position),intent);
                }
            });
    }

    private void getSpecificNoteData(NoteModel theNote,Intent intent){
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTE_KEY,theNote);
        intent.putExtras(bundle);
        startActivityForResult(intent,NO_REQUESTCODE);
    }

    private void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        startActivityForResult(intent, NO_REQUESTCODE);
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
        if(resultCode == RESULTCODE_NEWNOTE)
        {
            NoteModel newNote = (NoteModel) data.getExtras().getSerializable(NEWNOTE_KEY);
            newNote.setId(listOfNotes.size()+1);
            listOfNotes.add(newNote);
            if(saveToFile("notify edited"));
                getData();
        }
        else{ // if note already, and want to remove or edit
            NoteModel noteModel = (NoteModel) data.getExtras().getSerializable(NOTE_KEY);
            int index = -1;

            if(resultCode == RESULTCODE_REMOVENOTE){
                index = getNoteIndex(noteModel);
                if(index != -1){
                    listOfNotes.remove(listOfNotes.get(index));
                    if(saveToFile("notify edited"));
                        getData();
                }
            }
            else if(resultCode == RESULTCODE_REQUEST_EDIT){
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                getSpecificNoteData(noteModel,intent);
            }
            else if(resultCode == RESULTCODE_EDITNOTE){
                index = getNoteIndex(noteModel);
                if(index != -1){
                    if(updateNoteValue(index,noteModel) &&
                            saveToFile("notify edited"))
                        getData();

                }

            }

        }

    }

    private boolean updateNoteValue(int index, NoteModel note){
        boolean updatedValues = true;
        try{
            listOfNotes.get(index).setNoteText(note.getNoteText());
            listOfNotes.get(index).setTitle(note.getTitle());
        }catch (Exception e)
        {
            updatedValues = false;
        }
        return updatedValues;
    }
    private boolean saveToFile(String message){
        Snackbar.make(findViewById(R.id.mainLayout),message,2000).show();
        return saveToFileSystem(FILENAME);
    }
    private int getNoteIndex(NoteModel noteModel){
        for(int i = 0;i <= listOfNotes.size();i++){
            if(noteModel.getId() == listOfNotes.get(i).getId()){
                return i;
            }
        }
        return -1;
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
        for(int i =0 ;i < listOfNotes.size();i++)
        {
            listOfTitles.add(listOfNotes.get(i).getTitle());
        }
        return new ArrayAdapter<String>(com.niclas.notify.MainActivity.this,android.R.layout.simple_list_item_1,listOfTitles);
    }

}