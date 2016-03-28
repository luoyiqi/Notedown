package com.example.noah.notes;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class RenderedNoteViewController extends AppCompatActivity {

    TextView noteView;
    Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendered_note_view);

        currentNote = (Note) getIntent().getSerializableExtra("note");

        // Show the Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(currentNote.getName());

        noteView = (TextView) findViewById(R.id.renderedText);

        // Load the file
        String readIn = "";
        try {
            readIn = currentNote.read(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Render the file as HTML, replacing images with Drawables.
        noteView.setText(Html.fromHtml(MarkupRenderer.render(readIn), new Html.ImageGetter() {
            @Override public Drawable getDrawable(String source) {

                Drawable d;
                try {
                    Uri yourUri = Uri.parse(source);
                    InputStream inputStream = getContentResolver().openInputStream(yourUri);
                    d = Drawable.createFromStream(inputStream, yourUri.toString() );
                } catch (Exception e) {
                    d = getResources().getDrawable(R.drawable.default_image);
                }

                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        }, null));
    }

}
