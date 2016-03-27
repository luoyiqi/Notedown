package com.example.noah.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
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
        currentNote = (Note) getIntent().getSerializableExtra("note");
        String filename = currentNote.getName();

        title = (EditText) findViewById(R.id.edittext_title);
        note = (EditText) findViewById(R.id.edittext_note);

        final TextWatcher tw = new TextWatcher() {
            private String lastValue = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newValue = note.getText().toString();
                if(!newValue.equals(lastValue)) {
                    lastValue = newValue;
                    Integer start = note.getSelectionStart();
                    Integer stop = note.getSelectionEnd();
                    note.setText(Html.fromHtml(MarkupRenderer.preview(s.toString())));
                    note.setSelection(start, stop);
                    currentNote.write(getApplicationContext(), note.getText().toString());
                }
            }
        };
        note.addTextChangedListener(tw);


        title.setText(currentNote.getName());
        title.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            String newtitle = title.getText().toString();
                            currentNote.rename(getApplicationContext(), newtitle);
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
        currentNote.rename(getApplicationContext(), newtitle);
    }

    public void loadNote() {
        try {
            note.setText(Html.fromHtml(MarkupRenderer.preview(currentNote.read(getApplicationContext()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNote(View v) {
        Intent intent = new Intent(getApplicationContext(), RenderedNoteView.class);
        intent.putExtra("note", currentNote);
        startActivity(intent);
    }
}
