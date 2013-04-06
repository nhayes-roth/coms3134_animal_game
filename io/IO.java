package io;

import java.util.*;
import java.io.*;

public final class IO {
    public static BufferedReader stdin = 
        new BufferedReader(new InputStreamReader(System.in));
    public static PrintStream stdout = System.out;
    public static PrintStream stderr = System.err;

    public static boolean affirmative (String yn) {
        return (yn.charAt(0) == 'y') || (yn.charAt(0) == 'Y');
    }
    
    public static String readFile (String name) {
        String s = "", line;
        try {
            BufferedReader f = new BufferedReader(new FileReader(name));
            while ((line = f.readLine()) != null) s += line + "\n";
            f.close();
        }
        catch (IOException e) { stderr.println("Can't open file: " + name); }
        return s;
    }
    
    public static String prompt (String s) {
        try {
            stdout.print(s);
            stdout.flush();
            return stdin.readLine();
        }
        catch (IOException e) { stderr.println(e); return ""; }
    }
    
    public static int promptInt (String s, int low, int high) {
        try {
            stdout.print(s + " [" + low + " to " + high + "] ");
            stdout.flush();
            int result = Integer.parseInt(stdin.readLine());
            if (result < low || result > high) throw new IOException("Out of range.");
            return result;
        }
        catch (Exception e) { stderr.println(e); return low - 1; }
    }
}