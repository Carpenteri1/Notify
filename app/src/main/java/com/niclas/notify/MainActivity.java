package com.niclas.notify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteModel newNote;
    private ArrayList<NoteModel> listOfNotes = new ArrayList<NoteModel>();
    private ArrayList<String> listOfTitles;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedpreferences;
    private final int EMPTYLIST = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.dynamicList);
        int i = listView.getCount();
        getData();
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });

    }
    private void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        if(listOfNotes != null)
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("listOfNotes",listOfNotes);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    private void getData(){
        listOfNotes = (ArrayList<NoteModel>) getIntent().getSerializableExtra("updatedListOfNotes");
        if(listOfNotes != null){
            ListView listView = findViewById(R.id.dynamicList);
            listView.setAdapter(getListOfTitles());
            Snackbar.make(findViewById(R.id.mainLayout),"Saved",4000).show();
        }

    }
    private ArrayAdapter<String> getListOfTitles(){
        listOfTitles = new ArrayList<String>();

        for(int i =0 ;i < listOfNotes.size();i++) {
            listOfTitles.add(listOfNotes.get(i).getTitle());
        }
        return new ArrayAdapter<String>(com.niclas.notify.MainActivity.this,android.R.layout.simple_list_item_1,listOfTitles);
    }

}