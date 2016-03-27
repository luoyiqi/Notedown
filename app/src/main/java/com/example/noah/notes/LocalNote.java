package com.example.noah.notes;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nosha on 20/03/2016.
 */
public class LocalNote implements Note {

    String filename;
    Integer image;

    public String getName() {
        return filename;
    }

    public LocalNote(Context root, String filename) {
        this.filename = filename;
        image = R.drawable.diagram;
    }

    public String read(Context root) throws IOException {
        File file = new File(root.getFilesDir(), filename);
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = input.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public void write(Context root, String string) {
        try {
            FileOutputStream outputStream = root.openFileOutput(filename , root.MODE_PRIVATE);
            outputStream.write(string.getBytes()); outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rename(Context root, String newname) {
        File fileold = new File(root.getFilesDir(), filename);
        File filenew = new File(root.getFilesDir(), newname);
        if(filenew.exists() == false) {
            fileold.renameTo(filenew);
            filename = newname;
        } else {
            if(!newname.equals(filename)) {
                Toast.makeText(root, "Name already exists.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(Context root) {
        File file = new File(root.getFilesDir(), filename);
        file.delete();
    }

    public String preview(Context root) {
        String previewString = "";
        try {
            previewString = MarkupRenderer.preview(read(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previewString;
    }

}
