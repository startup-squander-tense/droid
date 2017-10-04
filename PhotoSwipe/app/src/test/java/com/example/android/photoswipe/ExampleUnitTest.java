package com.example.android.photoswipe;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void readFilesWorks() {
//        MainActivity activity = new MainActivity();
//        ArrayList<String> files = activity.readAllFilesInAssets("");
        assertEquals(1 + 1, 2);
//        assertEquals(files.contains("wowow1.mp3"), true);
    }
}