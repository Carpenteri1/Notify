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
    private final int REMOVEREQUISTCODE = 200;
    private final int EDITREQUISTCODE = 300;
    private ArrayList<String> noteListValues;
    private boolean editButtonClicked = false;
    private boolean removeButtonClicked = false;
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_noteinfo);
        NoteModel theNote = getNoteData();

        if(fabRemoveBtnClicked(theNote))
            removeNote(theNote);

        if(fabEditButtonClicked(theNote))
            editNote(theNote);
    }

    private boolean fabEditButtonClicked(NoteModel theNote){
        editButtonClicked = false;
        FloatingActionButton editButton = findViewById(R.id.fabEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButtonClicked = true;
            }
        });
        return editButtonClicked;
    }

    private boolean fabRemoveBtnClicked(NoteModel theNote){
        removeButtonClicked = false;
        FloatingActionButton removeButton = findViewById(R.id.fabRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButtonClicked = true;
            }
        });
        return removeButtonClicked;
    }

    private NoteModel getNoteData() {
        Bundle bundle = getIntent().getExtras();
        return setNoteData((NoteModel) bundle.getSerializable("theNote"));
    }

    private NoteModel setNoteData(NoteModel theNote){

        TextView titleValue = findViewById(R.id.NoteTitle);
        TextView textValue = findViewById(R.id.NoteText);

        textValue.setText(theNote.getNoteText());
        titleValue.setText(theNote.getTitle());

        return theNote;
    }
    private void removeNote(NoteModel theNote)
    {
        Intent intent = newIntent().putExtras(newBundle("removeNote",theNote));
        setResult(REMOVEREQUISTCODE,intent);
        finish();
    }

    private void editNote(NoteModel theNote)
    {
        Intent intent = newIntent().putExtras(newBundle("editNote",theNote));
        setResult(EDITREQUISTCODE,intent);
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
