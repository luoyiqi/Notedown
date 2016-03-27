package com.example.noah.notes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotePreview extends ArrayAdapter<String>{

    private final Activity context;
    private String[] names;
    private Integer[] imageId;
    private String[] previews;

    static public String[] names(Note[] notes) {
        String[] nameList = new String[notes.length];
        for(int i=0;i<notes.length;i++) {
            nameList[i] = notes[i].filename;
        }
        return nameList;
    }

    public NotePreview(Activity context,
                       Note[] notes) {
        super(context, R.layout.list_single, names(notes));
        this.context = context;
        previews = new String[notes.length];
        imageId = new Integer[notes.length];
        for(int i=0;i<notes.length;i++) {
            previews[i] = notes[i].preview();
            imageId[i] = R.drawable.diagram;
        }
        names = names(notes);

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtNote = (TextView) rowView.findViewById(R.id.txt2);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(names[position]);
        txtNote.setText(previews[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}