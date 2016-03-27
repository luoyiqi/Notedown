package com.example.noah.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class RenderedNoteView extends AppCompatActivity {

    TextView noteView;
    Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendered_note_view);

        String filename = getIntent().getStringExtra("filename");

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(filename);

        noteView = (TextView) findViewById(R.id.renderedText);

        currentNote = new LocalNote(getApplicationContext(), filename);

        try {
            noteView.setText(MarkupRenderer.render(currentNote.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
