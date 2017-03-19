package com.ar.myfirstapp;

import android.util.Log;

import com.ar.myfirstapp.obd2.ASCIIUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        byte[] data = {55, 69, 56, 32, 48, 54, 32, 52, 49, 32, 48, 48, 32, 66, 69, 32, 51, 69, 32, 66, 56, 32, 49, 66, 32};
        String str = ASCIIUtils.toString(data);
        System.out.print(str);
    }
}