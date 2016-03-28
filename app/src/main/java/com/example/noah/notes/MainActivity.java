package com.example.noah.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Note[] web;
    Map<String,Note> noteMap;
    Integer[] imageId;
    NotePreview adapter;

    public void refresh() {
        File[] files = getFilesDir().listFiles();

        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });

        web = new Note[files.length];
        noteMap = new HashMap<>(files.length);
        for(int i=0; i<files.length; i++) {
            String name = files[i].getName();
            Note note = new LocalNote(this, name);
            noteMap.put(name, note);
            web[i] = note;
        }
        adapter = new NotePreview(this, web);
        list=(ListView) findViewById(R.id.noteview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                loadNote(web[+position].getName());
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
        new LocalNote(getApplicationContext(), newname);
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
        Intent intent = new Intent(getApplicationContext(), NoteEdit.class);
        Note newnote = new LocalNote(this, filename);
        intent.putExtra("note", newnote);
        startActivity(intent);
    }

    public void deleteNote(String filename) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete " + filename + "?");
        final String filenameFinal = filename;
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                noteMap.get(filenameFinal).delete(getApplicationContext());
                refresh();
            }
        });
        adb.show();

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
