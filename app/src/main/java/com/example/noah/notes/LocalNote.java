package com.example.noah.notes;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Authored by Noah Ingham */

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

    /**
     * Reads the note
     * @param root The context used to access to file.
     * @return The contents of the note
     * @throws IOException If the note doesn't exist
     */
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

    /**
     * Writes to the note.
     * @param root The context used to access to file.
     * @param string The string to write.
     */
    public void write(Context root, String string) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date date = new Date();
            string = string.replace("insertdate", dateFormat.format(date));
            FileOutputStream outputStream = root.openFileOutput(filename , root.MODE_PRIVATE);
            outputStream.write(string.getBytes()); outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Renames the note
     * @param root The context used to access to file.
     * @param newname The new name.
     */
    public void rename(Context root, String newname) {
        File fileold = new File(root.getFilesDir(), filename);
        File filenew = new File(root.getFilesDir(), newname);

        // Check if name is already used.
        if(filenew.exists() == false) {
            fileold.renameTo(filenew);
            filename = newname;
        } else {
            if(!newname.equals(filename)) {
                Toast.makeText(root, "Name already exists.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Delete the note.
     * @param root The context used to access to file.
     */
    public void delete(Context root) {
        File file = new File(root.getFilesDir(), filename);
        file.delete();
    }

    /**
     * Generate a preview string of the note.
     * @param root The context used to access to file.
     * @return The generated preview.
     */
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
