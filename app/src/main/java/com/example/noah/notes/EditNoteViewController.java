package com.example.noah.notes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class EditNoteViewController extends AppCompatActivity {

    Note currentNote;
    EditText title;
    EditText note;

    // Used for adding images
    static final int PICK_IMAGE = 1;

    /**
     * Create a new EditNoteViewController
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_view);
        currentNote = (Note) getIntent().getSerializableExtra("note");

        // Create Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.edit_note);

        // Set colors
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        }
        setTheme(R.style.DarkTheme);

        title = (EditText) findViewById(R.id.edittext_title);
        note = (EditText) findViewById(R.id.edittext_note);

        // Check for changes to the text
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

                // Make sure we don't enter infinite loop of making changes and triggering this
                if(!newValue.equals(lastValue)) {
                    lastValue = newValue;

                    // Get cursor position
                    Integer start = note.getSelectionStart();
                    Integer stop = note.getSelectionEnd();

                    // Transofrm text
                    note.setText(Html.fromHtml(MarkupRenderer.editor(s.toString())));

                    // Reset cursor position
                    note.setSelection(start, stop);

                    // Save the note every change
                    currentNote.write(getApplicationContext(), s.toString().replace("\n","<br />"));
                }
            }
        };
        note.addTextChangedListener(tw);

        // Set title
        title.setText(currentNote.getName());
        // Only rename the file when focus has been lost
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

        // Focus on the note
        note.requestFocus();

        loadNote();
    }

    /**
     * Make sure there isn't an unsaved change to the title when we leave
     */
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

    /**
     * loadNote loads a note
     */
    public void loadNote() {
        try {
            note.setText(Html.fromHtml(MarkupRenderer.editor(currentNote.read(getApplicationContext()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A menu button call to render the preview, opening a new Activity
     * @param item The item pressed
     * @return
     */
    public boolean renderNote(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), RenderedNoteViewController.class);
        intent.putExtra("note", currentNote);
        startActivity(intent);
        return true;
    }

    /**
     * A menu button call to add a photo, opening the Gallery for selection
     * @param item The item pressed
     * @return
     */
    public boolean addPhoto(MenuItem item) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        return true;
    }

    /**
     * Activity Result callback
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // When we get a photo back from the Gallery
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();

                note.getText().insert(note.getSelectionStart(), "!["+R.string.new_image+"]("+selectedImage.toString()+")");
            }
        }
    }

}
