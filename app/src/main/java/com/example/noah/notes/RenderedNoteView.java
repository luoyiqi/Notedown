package com.example.noah.notes;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
            Log.i("WOO!", readIn);
            noteView.setText(Html.fromHtml(MarkupRenderer.render(readIn), new Html.ImageGetter() {
                @Override public Drawable getDrawable(String source) {
                    Log.i(":P!!!",source);

                    Drawable d;
                    Uri yourUri = Uri.parse(source);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(yourUri);
                        d = Drawable.createFromStream(inputStream, yourUri.toString() );
                    } catch (FileNotFoundException e) {
                        d = getResources().getDrawable(R.drawable.default_image);
                    }

                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    return d;
                }
            }, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
