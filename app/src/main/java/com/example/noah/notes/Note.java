package com.example.noah.notes;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by nosha on 20/03/2016.
 */
public interface Note extends Serializable {

    public String read() throws IOException;

    public void write(String string);

    public void rename(String newname);

    public void delete();

    public String preview();

    public String getName();

}
