package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteInfoActivity extends AppCompatActivity {
    private final int REQUESTCODE_REMOVENOTE = 200;
    private final int REQUESTCODE_EDITNOTE = 300;

    private final String NOTE_KEY = "DataGet";

    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_noteinfo);
        NoteModel theNote = getNoteData(NOTE_KEY);

        fabRemoveBtnClicked(theNote);
        fabEditButtonClicked(theNote);

    }

    private void fabEditButtonClicked(NoteModel theNote){
        FloatingActionButton editButton = findViewById(R.id.fabEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(theNote);
            }
        });
    }

    private void fabRemoveBtnClicked(NoteModel theNote){
        FloatingActionButton removeButton = findViewById(R.id.fabRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeNote(theNote);
            }
        });
    }

    private NoteModel getNoteData(String keyValue) {
        Bundle bundle = getIntent().getExtras();
        return setNoteData((NoteModel) bundle.getSerializable(keyValue));
    }

    private NoteModel setNoteData(NoteModel theNote){

        TextView titleValue = findViewById(R.id.NoteTitle);
        TextView textValue = findViewById(R.id.NoteText);

        textValue.setText(theNote.getNoteText());
        titleValue.setText(theNote.getTitle());

        return theNote;
    }
    private void removeNote(NoteModel theNote){
        setResult(REQUESTCODE_REMOVENOTE, newIntent().putExtras(newBundle(NOTE_KEY,theNote)));
        finish();
    }

    private void editNote(NoteModel theNote)
    {
        setResult(REQUESTCODE_EDITNOTE, newIntent().putExtras(newBundle(NOTE_KEY,theNote)));
        finish();
    }

    private Intent newIntent(){
        return new Intent();
    }
    private Bundle newBundle(String keyValue,NoteModel theNote){
        Bundle newBundle = new Bundle();
        newBundle.putSerializable(keyValue,theNote);
        return newBundle;
    }

}
