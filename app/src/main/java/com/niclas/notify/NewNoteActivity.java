package com.niclas.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity
{

    EditText inputOne;
    EditText inputTwo;
    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_newnote);
        inputOne = findViewById(R.id.inputOne);
        inputTwo = findViewById(R.id.inputTwo);
        Button saveButton = findViewById(R.id.saveButton);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inputOne.getText().length() > 0 &&
                    inputTwo.getText().length() > 0)
                    {
                        saveNote(view);
                    }else{
                        changeView();
                    }
                }
            });
    }

    public void saveNote(View v){
        Intent intent = new Intent(NewNoteActivity.this, com.niclas.notify.MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("inputOne",inputOne.getText().toString());
        bundle.putString("inputTwo",inputTwo.getText().toString());

        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void changeView(){
        Intent intent = new Intent(NewNoteActivity.this, com.niclas.notify.MainActivity.class);
        startActivity(intent);
    }
}
