package com.niclas.notify;

import org.json.JSONArray;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class NoteModel  implements Serializable {

    private String title;
    private String text;
    //private Date date;
    //private Time time;

    public String getTitle(){ return title; }
    public void setTitle(String newTitle){ title = newTitle; }
    public void setNoteText(String newText){ text = newText; }
    public String getNoteText(){ return text; }
}
