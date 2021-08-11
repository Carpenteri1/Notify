package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.*;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity
{
    private  ArrayList<NoteModel> savedDatalist;
    private ArrayList<NoteModel> listOfNotes;
    private final int StringEmpty = 0;
    private NoteModel noteModel = new NoteModel();

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_newnote);

        Button saveButton = findViewById(R.id.saveButton);

           saveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    NoteModel newNote = new NoteModel();
                    newNote.setTitle(getTitleValue());
                    newNote.setNoteText(getNoteTextValue());

                    if(checkInput(newNote.getTitle(), newNote.getNoteText()))
                    {
                        listOfNotes = updateDataToListOfNotes(newNote);
                        saveNote();
                    }
                }
            });
    }

    private void saveNote()
    {
        Intent intent = new Intent(NewNoteActivity.this, com.niclas.notify.MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("updatedListOfNotes",listOfNotes);

        intent.putExtras(bundle);
        startActivity(intent);

    }
    private ArrayList<NoteModel> updateDataToListOfNotes(NoteModel newNote) {
        listOfNotes = (ArrayList<NoteModel>) getIntent().getSerializableExtra("listOfNotes");

        if (listOfNotes != null) {
            listOfNotes.add(newNote);
        } else {
            listOfNotes = new ArrayList<NoteModel>();
            listOfNotes.add(newNote);
        }
        return listOfNotes;
    }
    private boolean emptyTitleFieldsWarning(){
        Snackbar.make(findViewById(R.id.newNoteLayout),"Title field can't be empty",5000).show();
        return false;
    }
    private boolean emptyNoteFieldsWarning(){
        Snackbar.make(findViewById(R.id.newNoteLayout),"Text field can't be empty",5000).show();
        return false;
    }
    private boolean emptyFieldsWarning(){
        Snackbar.make(findViewById(R.id.newNoteLayout),"Fields can't be empty",5000).show();
        return false;
    }


    private boolean checkInput(String title, String text){
        boolean inputLegit = true;

        if(text.length() == StringEmpty &&
                title.length() == StringEmpty)
        {
            inputLegit = emptyFieldsWarning();
        }
        else if(title.length() == StringEmpty)
        {
            inputLegit = emptyTitleFieldsWarning();
        }
        else if(text.length() == StringEmpty)
        {
            inputLegit = emptyNoteFieldsWarning();
        }

        return inputLegit;
    }

    private String getTitleValue(){
        EditText inputOne = findViewById(R.id.inputOne);
        return inputOne.getText().toString();
    }
    private String getNoteTextValue(){
        EditText inputTwo = findViewById(R.id.inputTwo);
        return inputTwo.getText().toString();
    }
}
