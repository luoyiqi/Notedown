package com.example.noah.notes;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotePreview extends ArrayAdapter<String>{

    private final Activity context;
    private List<String> names;
    private Integer[] imageId;
    private String[] previews;

    /**
     * Generate the List of names for the adapter
     * @param notes
     * @return
     */
    static public List<String> names(Note[] notes) {
        List<String> nameList = new ArrayList<>();
        for(int i=0;i<notes.length;i++) {
            nameList.add(notes[i].getName());
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
            previews[i] = notes[i].preview(context);
            imageId[i] = R.drawable.note3;
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
        txtTitle.setText(names.get(position));
        txtNote.setText(Html.fromHtml(previews[position]));

        LinearLayout surface = (LinearLayout) rowView.findViewById(R.id.surface);
        LinearLayout trash = (LinearLayout) rowView.findViewById(R.id.trash);

        // Check for clicks to open the note
        final String name = names.get(position);
        surface.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).loadNote(name);
                }
            }
        });

        // Check for clicks to delete the note
        trash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).deleteNote(name);
                }
            }
        });

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}