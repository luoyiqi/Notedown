package com.example.noah.notes;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;


public class MarkupTest extends ApplicationTestCase<Application> {

    public MarkupTest() {
        super(Application.class);
    }

    @Test
    public void testBold() {
        String testString = "**This is bold**, **this isn't.";
        String newString = MarkupRenderer.render(testString);

        // Render
        String expectedString = "<strong>This is bold</strong>, **this isn't.";
        assertEquals(newString, expectedString);

        // Edit
        /***/

        // Preview
        /***/
    }
}
