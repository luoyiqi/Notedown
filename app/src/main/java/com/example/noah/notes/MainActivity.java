package com.example.noah.notes;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List l = new ArrayList<Note>();

    ListView list;
    String[] web;
    Integer[] imageId;

    public void refresh() {
        File[] files = getFilesDir().listFiles();
        web = new String[files.length];
        imageId = new Integer[files.length];
        for(int i=0; i<files.length; i++) {
            web[i] = files[i].getName();
            imageId[i] = R.drawable.diagram;
        }
        CustomList adapter = new CustomList(this, web, imageId);
        list=(ListView) findViewById(R.id.noteview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                loadNote(web[+position]);
            }
        });

    }

    public void newNote(View v) {
        String newtitle = "New Note 1";
        //increment while exists
        new Note(getApplicationContext(), "New Note 1");
        loadNote("New Note 1");
    }

    public void editNote() {

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void loadNote(String filename) {
        Intent intent = new Intent(getApplicationContext(), NoteView.class);
        intent.putExtra("filename",filename);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }
}
