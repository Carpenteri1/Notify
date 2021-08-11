package com.niclas.notify;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity{

    private ArrayList<NoteModel> listOfNotes;
    private final int StringEmpty = 0;

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
                        saveNote(newNote);
                    }
                }
            });
    }

    private void saveNote(NoteModel newNote)
    {
        Intent intent = new Intent();
        Bundle newBundle = new Bundle();
        newBundle.putSerializable("newNote",newNote);
        intent.putExtras(newBundle);
        setResult(100,intent);
        finish();
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
