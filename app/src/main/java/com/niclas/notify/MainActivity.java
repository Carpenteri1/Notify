package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetData();
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });

    }
    public void newNote(){
        Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
        startActivity(intent);
    }

    public void GetData(){
        Intent intent = getIntent();
        String valueOne = intent.getStringExtra("inputOne");
        String valueTwo = intent.getStringExtra("inputTwo");
        if(valueOne != null &&
        valueTwo != null){

            View v = findViewById(R.id.mainLayout);
            TextView textView = findViewById(R.id.textView);
            TextView textView2 = findViewById(R.id.textView2);
            textView.setText(valueOne);
            textView2.setText(valueTwo);

            Snackbar.make(v,"Saved",4000).show();
        }
    }
}