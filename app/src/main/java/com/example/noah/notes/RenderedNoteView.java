package com.example.noah.notes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import java.io.IOException;

public class RenderedNoteView extends AppCompatActivity {

    TextView noteView;
    Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendered_note_view);

        currentNote = (Note) getIntent().getSerializableExtra("note");

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(currentNote.getName());

        noteView = (TextView) findViewById(R.id.renderedText);

        try {
            String readIn = currentNote.read(getApplicationContext());
            noteView.setText(Html.fromHtml(MarkupRenderer.render(readIn)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
