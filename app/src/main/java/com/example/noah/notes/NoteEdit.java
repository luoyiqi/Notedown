package com.example.noah.notes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class NoteEdit extends AppCompatActivity {

    Note currentNote;
    EditText title;
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        currentNote = (Note) getIntent().getSerializableExtra("note");
        String filename = currentNote.getName();

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Note");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#282A35")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        }


        setTheme(R.style.DarkTheme);

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
                    Log.i("HI","still here");
                    lastValue = newValue;
                    Integer start = note.getSelectionStart();
                    Integer stop = note.getSelectionEnd();
                    Log.i("HI","'"+Html.fromHtml(" a".replaceAll("(^|\n)  ","$0&nbsp;")).toString()+"'");
                    Log.i("HI","'"+" a".replaceAll("(^|\n) ","$0&nbsp;")+"'");
                    Log.i("HI","'"+s.toString()+"'");
                    Log.i("HI","'"+MarkupRenderer.editor(s.toString())+"'");
                    note.setText(Html.fromHtml(MarkupRenderer.editor(s.toString())));
                    note.setSelection(start, stop);
                    currentNote.write(getApplicationContext(), s.toString().replace("\n","<br />"));
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void loadNote() {
        try {
            note.setText(Html.fromHtml(MarkupRenderer.editor(currentNote.read(getApplicationContext()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean renderNote(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), RenderedNoteView.class);
        intent.putExtra("note", currentNote);
        startActivity(intent);
        return true;
    }
}
