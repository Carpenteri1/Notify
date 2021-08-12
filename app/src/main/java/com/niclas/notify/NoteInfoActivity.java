package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteInfoActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_noteinfo);
        getNoteData();

    }

    private void getNoteData() {
        Bundle bundle = getIntent().getExtras();
        NoteModel theNote = (NoteModel) bundle.getSerializable("theNote");

        TextView textView = findViewById(R.id.textView);
        textView.setText("Title: "+theNote.getTitle()+"\nText: " + theNote.getNoteText());
    }

}
