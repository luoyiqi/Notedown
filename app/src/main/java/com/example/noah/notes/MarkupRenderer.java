package com.example.noah.notes;

/* Authored by Noah Ingham */

public class MarkupRenderer {

    /**
     * Generate HTML for a proper render
     * @param markup The string to parse
     * @return The ouput string
     */
    static public String render(String markup) {

        // A simple Regex-based Markdown renderer
        // TODO: Create pairs of patterns and replacements, iterating through the list to call replaceAll on it.
        // Inspired by: https://gist.github.com/jbroadway/2836900

        markup = markup.replaceAll("&nbsp;"," ");
        markup = markup.replaceAll("<br />","\n");
        markup = markup.replaceAll("\\*\\*([a-zA-Z0-9])(.*?)\\*\\*","<strong>$1$2</strong>");
        markup = markup.replaceAll("\\*([a-zA-Z0-9])(.*?)\\*","<i>$1$2</i>");
        markup = markup.replaceAll("(^|\n)####(.*?)($|(?=\n))","$1<b>$2</b>");
        markup = markup.replaceAll("(^|\n)###(.*?)($|(?=\n))","$1<big><b>$2</b></big>");
        markup = markup.replaceAll("(^|\n)##(.*?)($|(?=\n))","$1<big><big><b>$2</b></big></big>");
        markup = markup.replaceAll("(^|\n)#(.*?)($|(?=\n))","$1<big><big><big><big><b>$2</b></big></big></big></big>");
        markup = markup.replaceAll("!\\[(.*?)\\]\\((.*?)\\)","<img src=\"$2\" />");
        markup = markup.replaceAll("(^|\n) ","$1&nbsp;");
        markup = markup.replaceAll("  ","&nbsp;&nbsp;");
        markup = markup.replaceAll("\n","<br />");
        return markup;
    }

    /**
     * Generate HTML for the EditText
     * @param markup The string to parse
     * @return The ouput string
     */
    static public String editor(String markup) {
        markup = markup.replaceAll("&nbsp;"," ");
        markup = markup.replaceAll("<br />","\n");
        markup = markup.replaceAll("\\*\\*\\*([a-zA-Z0-9])(.*?)\\*\\*\\*","<i><font color='#CF8353'>&#42<strong><font color='#00ADC4'>&#42&#42$1$2&#42&#42</font></strong>&#42</font></i>");
        markup = markup.replaceAll("\\*\\*([a-zA-Z0-9])(.*?)\\*\\*","<strong><font color='#00ADC4'>&#42&#42$1$2&#42&#42</font></strong>");
        markup = markup.replaceAll("\\*([a-zA-Z0-9])(.*?)\\*","<i><font color='#CF8353'>*$1$2*</font></i>");
        markup = markup.replaceAll("(^|\n)####(.*?)($|(?=\n))","$1<b><font color='#7A6AAE'>####$2</font></b>");
        markup = markup.replaceAll("(^|\n)###(.*?)($|(?=\n))","$1<b><big><font color='#7A6AAE'>###$2</font></big></b>");
        markup = markup.replaceAll("(^|\n)##(.*?)($|(?=\n))","$1<b><big><big><font color='#7A6AAE'>##$2</font></big></big></b>");
        markup = markup.replaceAll("(^|\n)#(.*?)($|(?=\n))","$1<b><big><big><big><font color='#7A6AAE'>#$2</font></big></big></big></b>");
        markup = markup.replaceAll("!\\[(.*?)\\]\\((.*?)\\)","<font color='#7DC962'>![<strong>$1</strong>]($2)</font>");
        markup = markup.replaceAll("(^|\n) ","$1&nbsp;");
        markup = markup.replaceAll("  ","&nbsp;&nbsp;");
        markup = markup.replaceAll("\n","<br />");
        return markup;
    }

    /**
     * Generate HTML for the previews
     * @param markup The string to parse
     * @return The ouput string
     */
    static public String preview(String markup) {
        markup = markup.replaceAll("&nbsp;"," ");
        markup = markup.replaceAll("<br />","\n");
        markup = markup.replaceAll("\\*\\*([a-zA-Z0-9])(.*?)\\*\\*","<strong>$1$2</strong>");
        markup = markup.replaceAll("\\*([a-zA-Z0-9])(.*?)\\*","<i>$1$2</i>");
        markup = markup.replaceAll("(^|\n)####(.*?)($|(?=\n))","$1<b>$2</b>");
        markup = markup.replaceAll("(^|\n)###(.*?)($|(?=\n))","$1<b>$2</b>");
        markup = markup.replaceAll("(^|\n)##(.*?)($|(?=\n))","$1<b>$2</b>");
        markup = markup.replaceAll("(^|\n)#(.*?)($|(?=\n))","$1<b>$2</b>");
        markup = markup.replaceAll("\n","<br />");
        return markup;
    }

}
