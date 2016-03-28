package com.example.noah.notes;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import org.junit.Test;

import java.io.IOException;


public class LocalNoteTest extends ApplicationTestCase<Application> {

    public LocalNoteTest() {
        super(Application.class);
    }

    @Test
    public void testLocalFile() {
        Context context = getContext();

        // Create
        Note note = new LocalNote(context, "testLocalNote");

        // Rename
        note.rename(context, "testLocalNote2");

        // Write
        note.write(context, "hello");

        // Read
        try { assertEquals("hello", note.read(context));}
        catch (IOException e) { e.printStackTrace(); }

        // Delete
        note.delete(context);
    }
}
