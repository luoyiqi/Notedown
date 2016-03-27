package com.example.noah.notes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nosha on 20/03/2016.
 */
public class Note {

    String filename;
    FileOutputStream outputStream; /* Writes to a file in app’s internal directory */
    Context root;
    Integer image;

    public Note(Context root, String filename) {
        this.filename = filename;
        this.root = root;
        image = R.drawable.diagram;
    }

    public String read() throws IOException {
        File file = new File(root.getFilesDir(), filename);
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = input.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public void write(String string) {
        try {
            outputStream = root.openFileOutput(filename , root.MODE_PRIVATE);
            outputStream.write(string.getBytes()); outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rename(String newname) {
        File fileold = new File(root.getFilesDir(), filename);
        File filenew = new File(root.getFilesDir(), newname);
        fileold.renameTo(filenew);
        filename = newname;
    }

    public String preview() {
        String previewString = "";
        try {
            previewString = read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previewString;
    }

}
