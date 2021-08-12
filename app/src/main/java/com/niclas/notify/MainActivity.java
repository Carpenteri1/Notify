package com.niclas.notify;

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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<NoteModel> listOfNotes = new ArrayList<NoteModel>();
    private ArrayList<String> listOfTitles;
    private final int EMPTYLIST = 0;
    private final int REQUESTCODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivity(intent);
    }

    private void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        startActivityForResult(intent, REQUESTCODE);
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
        if(requestCode == REQUESTCODE)
        {
            listOfNotes.add((NoteModel) data.getExtras().getSerializable("newNote"));
            getData();

        }
    }

    private void getData(){
        if(listOfNotes.size() > EMPTYLIST){
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