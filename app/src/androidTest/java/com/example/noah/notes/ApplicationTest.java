package com.example.noah.notes;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.EditText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @MediumTest
    public void testNoteCreate() {
        createApplication();

        Note newNote = new LocalNote(getContext(), "testNote");

        newNote.write(getContext(), "testing, 1234");

    }
}