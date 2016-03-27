package com.example.noah.notes;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by nosha on 27/03/2016.
 */
public class MarkupRenderer {

    static public String render(String markup) {
        markup = markup.replaceAll("\\*\\*(.*?)\\*\\*","<strong>$1</strong>");
        markup = markup.replaceAll("\\*(.*?)\\*","<i>$1</i>");
        markup = markup.replaceAll("\n", "<br />");
        return markup;
    }

    static public String preview(String markup) {
        //markup = markup.replaceAll("\\*\\*\\*(.*?)\\*\\*\\*","<strong><font color='#00ADC4'>&#42&#42$<i><font color='#CF8353'>&#421&#42</font></i>&#42&#42</font></strong>");
        markup = markup.replaceAll("\\*\\*(.*?)\\*\\*","<strong><font color='#00ADC4'>&#42&#42$1&#42&#42</font></strong>");
        markup = markup.replaceAll("\\*(.*?)\\*","<i><font color='#CF8353'>*$1*</font></i>");
        markup = markup.replaceAll("\n","<br />");
        return markup;
    }

}
