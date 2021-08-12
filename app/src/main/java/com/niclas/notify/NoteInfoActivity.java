package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteInfoActivity extends AppCompatActivity {
    private final int REQUESTCODE = 200;
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_noteinfo);
        NoteModel theNote = getNoteData();
        fabRemoveBtnClicked(theNote);
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

    private NoteModel getNoteData() {
        Bundle bundle = getIntent().getExtras();
        NoteModel theNote = (NoteModel) bundle.getSerializable("theNote");

        TextView textView = findViewById(R.id.textView);
        textView.setText("Title: "+theNote.getTitle()+"\nText: " + theNote.getNoteText() +"\nId: " +theNote.getId());
        return theNote;
    }

    private void removeNote(NoteModel removeNote)
    {
        Intent intent = new Intent();
        Bundle newBundle = new Bundle();
        newBundle.putSerializable("removeNote",removeNote);
        intent.putExtras(newBundle);
        setResult(REQUESTCODE,intent);
        finish();
    }

}
