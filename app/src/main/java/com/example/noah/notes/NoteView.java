package com.example.noah.notes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class NoteView extends Activity {

    Note currentNote;
    EditText title;
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String filename = getIntent().getStringExtra("filename");
        setContentView(R.layout.activity_note_view);

        title = (EditText) findViewById(R.id.edittext_title);
        note = (EditText) findViewById(R.id.edittext_note);

        currentNote = new Note(getApplicationContext(), filename);
        title.setText(currentNote.filename);

        title.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            String newtitle = title.getText().toString();
                            currentNote.rename(newtitle);
                        }
                    }
                }
        );

        loadNote();
    }

    public void loadNote() {
        try {
            note.setText(currentNote.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNote(View v) {
        currentNote.write(note.getText().toString());
    }
}
