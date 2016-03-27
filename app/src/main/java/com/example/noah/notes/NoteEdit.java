package com.example.noah.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class NoteEdit extends Activity {

    Note currentNote;
    EditText title;
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        String filename = getIntent().getStringExtra("filename");

        title = (EditText) findViewById(R.id.edittext_title);
        note = (EditText) findViewById(R.id.edittext_note);

        currentNote = new LocalNote(getApplicationContext(), filename);

        title.setText(currentNote.getName());
        title.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            String newtitle = title.getText().toString();
                            currentNote.rename(newtitle);
                            title.setText(currentNote.getName());
                        }
                    }
                }
        );

        note.requestFocus();

        loadNote();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String newtitle = title.getText().toString();
        currentNote.rename(newtitle);
        currentNote.write(note.getText().toString());
    }

    public void loadNote() {
        try {
            note.setText(currentNote.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNote(View v) {
        Intent intent = new Intent(getApplicationContext(), RenderedNoteView.class);
        intent.putExtra("filename", currentNote.getName());
        startActivity(intent);
    }
}
