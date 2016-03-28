package com.example.noah.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Note[] web;
    Map<String,Note> noteMap;
    Integer[] imageId;
    NotePreview adapter;

    /**
     * refresh reloads the list of note previews
     */
    public void refresh() {
        File[] files = getFilesDir().listFiles();

        // Sort by modification date
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });

        // Various required data structures
        web = new Note[files.length];
        noteMap = new HashMap<>(files.length);
        for(int i=0; i<files.length; i++) {
            String name = files[i].getName();
            Note note = new LocalNote(this, name);
            noteMap.put(name, note);
            web[i] = note;
        }

        // Create list adapter
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

    /**
     * newNote creates a new local storage note
     * @param v the required View parameter
     */
    public void newNote(View v) {
        int count = 1;
        String newname = R.string.new_note+Integer.toString(count);
        File newfile = new File(getFilesDir(), newname);

        // Increment name while file already exists
        while(newfile.exists()) {
            count++;
            newname = R.string.new_note+Integer.toString(count);
            newfile = new File(getFilesDir(), newname);
        }
        new LocalNote(getApplicationContext(), newname);
        loadNote(newname);
    }


    /**
     * Triggered if the activity has been resumed and then refresh
     */
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    /**
     * Load a local storage note
     * @param filename the note to load
     */
    public void loadNote(String filename) {
        Intent intent = new Intent(getApplicationContext(), EditNoteViewController.class);
        Note newnote = new LocalNote(this, filename);
        intent.putExtra("note", newnote);
        startActivity(intent);
    }

    /**
     * Deletea note
     * @param filename the note to delete
     */
    public void deleteNote(String filename) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.delete_title);
        adb.setMessage(R.string.delete_body + filename + "?");
        final String filenameFinal = filename;
        adb.setNegativeButton(R.string.cancel, null);
        adb.setPositiveButton(R.string.confirm, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                noteMap.get(filenameFinal).delete(getApplicationContext());
                refresh();
            }
        });
        adb.show();

    }

    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }

}
