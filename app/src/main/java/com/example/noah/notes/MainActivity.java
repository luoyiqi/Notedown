package com.example.noah.notes;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List l = new ArrayList<Note>();

    ListView list;
    Note[] web;
    Integer[] imageId;

    public void refresh() {
        File[] files = getFilesDir().listFiles();
        web = new Note[files.length];
        for(int i=0; i<files.length; i++) {
            web[i] = new Note(getApplicationContext(), files[i].getName());
        }
        NotePreview adapter = new NotePreview(this, web);
        list=(ListView) findViewById(R.id.noteview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                loadNote(web[+position].filename);
            }
        });

    }

    public void newNote(View v) {
        int count = 1;
        String newname = "New Note "+Integer.toString(count);
        File newfile = new File(getFilesDir(), newname);
        while(newfile.exists()) {
            count++;
            newname = "New Note "+Integer.toString(count);
            newfile = new File(getFilesDir(), newname);
        }
        //increment while exists
        new Note(getApplicationContext(), newname);
        loadNote(newname);
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
        //setTheme();
        refresh();
    }


    public void setTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
