package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText titleValue;
    private EditText textValue;
    private final String NOTE_KEY = "DataGet";
    private final int RESULTCODE_EDITNOTE = 400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        NoteModel theNote = getNoteData(NOTE_KEY);
        saveButtonClicked(theNote);
    }
    private void saveButtonClicked(NoteModel noteModel){
        Button saveButton = findViewById(R.id.saveEditButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveChanges(noteModel);
            }
        });
    }
    private NoteModel getNoteData(String keyValue) {
        Bundle bundle = getIntent().getExtras();
        return setNoteData((NoteModel) bundle.getSerializable(keyValue));
    }
    private Intent newIntent(){
        return new Intent();
    }
    private Bundle newBundle(String keyValue,NoteModel theNote){
        Bundle newBundle = new Bundle();
        newBundle.putSerializable(keyValue,theNote);
        return newBundle;
    }


    private NoteModel setNoteData(NoteModel theNote){
        textValue = findViewById(R.id.editText);
        titleValue = findViewById(R.id.editTitle);

        textValue.setText(theNote.getNoteText());
        titleValue.setText(theNote.getTitle());

        return theNote;
    }
    private String getTitleValue(){
        titleValue = findViewById(R.id.editTitle);
        return titleValue.getText().toString();
    }
    private String getTextValue(){
        textValue = findViewById(R.id.editText);
        return textValue.getText().toString();
    }

    private void saveChanges(NoteModel noteModel){
        noteModel.setTitle(getTitleValue());
        noteModel.setNoteText(getTextValue());
        setResult(RESULTCODE_EDITNOTE,newIntent().putExtras(newBundle(NOTE_KEY,noteModel)));
        finish();
    }


}
