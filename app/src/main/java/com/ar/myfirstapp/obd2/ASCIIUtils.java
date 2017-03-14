package com.ar.myfirstapp.obd2;

import java.io.ByteArrayOutputStream;

/**
 * Created by Arun Soman on 3/12/2017.
 */

public class ASCIIUtils {
    public final static String toChar[]= {
            " ",
            "!",
            "\"",
            "#",
            "$",
            "%",
            "&",
            "'",
            "(",
            ")",
            "*",
            "+",
            ",",
            "-",
            ".",
            "/",
 "0",
 "1",
 "2",
 "3",
 "4",
 "5",
 "6",
 "7",
 "8",
 "9",
 ":",
 ";",
 "<",
 "=",
 ">",
 "?",
 "@",
 "A",
 "B",
 "C",
 "D",
 "E",
 "F",
 "G",
 "H",
 "I",
 "J",
 "K",
 "L",
 "M",
 "N",
 "O",
 "P",
 "Q",
 "R",
 "S",
 "T",
 "U",
 "V",
 "W",
 "X",
 "Y",
 "Z",
 "[",
 "\\",
 "]",
 "^",
 "_",
 "`",
 "a",
 "b",
 "c",
 "d",
 "e",
 "f",
 "g",
 "h",
 "i",
 "j",
 "k",
 "l",
 "m",
 "n",
 "o",
 "p",
 "q",
 "r",
 "s",
 "t",
 "u",
 "v",
 "w",
 "x",
 "y",
 "z",
 "{",
 "|",
 "}",
 "~",
};

public static final
    int[] toCharBytes(byte[] raw){
    int[] res = new int[raw.length];
    int i = 0;
    for(byte ab: raw){
      //  res[i++] =
    }
    return  res;
}

public static String toString(byte[] raw){
    return new String(raw);
}

private ByteArrayOutputStream bos = new ByteArrayOutputStream();
public int[] toIntArray(byte[] raw){
    bos.reset();
    String str = toString(raw);
    String[] tupple = str.split(" ");
    for(String s: tupple){

    }
    return null;
}
}
