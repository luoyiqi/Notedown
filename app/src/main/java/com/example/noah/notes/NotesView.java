package com.example.noah.notes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesView extends Fragment {

    ListView list;
    String[] web = {
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7"
    } ;
    Integer[] imageId = {
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram,
            R.drawable.diagram

    };

    public NotesView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes_view, container, false);


        String[] forecastArray = {
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10"
        };


        //List<String> weekForecast = new ArrayList<>(Arrays.asList(forecastArray));

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.text_view_layout, R.id.list_item_id,weekForecast);

        //ListView view = (ListView) rootView.findViewById(R.id.noteview);
        //view.setAdapter(adapter);




        super.onCreate(savedInstanceState);

        CustomList adapter = new CustomList(getActivity(), web, imageId);
        list=(ListView) rootView.findViewById(R.id.noteview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;

    }

}
