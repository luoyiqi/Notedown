package com.example.noah.notes;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Test;


public class MarkupTest extends ApplicationTestCase<Application> {

    public MarkupTest() {
        super(Application.class);
    }

    @Test
    public void testBold() {
        String testString = "**This is bold**, **this isn't.";

        // Render
        String newString = MarkupRenderer.render(testString);
        String expectedString = "<strong>This is bold</strong>, **this isn't.";
        assertEquals(newString, expectedString);

        // Edit
        String newString2 = MarkupRenderer.editor(testString);
        String expectedString2 = "<strong>*This is bold*</strong>, **this isn't.";
        assertEquals(newString2, expectedString2);

        // Preview
        /***/
    }

    @Test
    public void testItalic() {
        String testString = "*This is italics*, **this isn't.";
        String newString = MarkupRenderer.render(testString);

        // Render
        String expectedString = "<i>This is italics</i>, **this isn't.";
        assertEquals(newString, expectedString);

        // Edit
        /***/

        // Preview
        /***/
    }
}
