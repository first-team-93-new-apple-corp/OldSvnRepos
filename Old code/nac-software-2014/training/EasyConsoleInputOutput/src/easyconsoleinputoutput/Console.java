/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easyconsoleinputoutput;

import java.io.*;

/**
 * Does that same thing as the class System.out.PrintStream.
 * @author Evans Chen
 */
public class Console
{
    
    /**
     * Constructor for object Console.
     */
    public Console()
    {
        
    }
    
    /**
     * @param str The String to print.
     */
    public static void print(String str)
    {
        System.out.print(str);
    }
    
    /**
     * @param b The boolean to print.
     */
    public static void print(boolean b)
    {
        System.out.print(toString(b));
    }
    
    /**
     * @param c The character to print.
     */
    public static void print(char c)
    {
        System.out.print(toString(c));
    }
    
    /**
     * @param i The integer to print.
     */
    public static void print(int i)
    {
        System.out.print(toString(i));
    }
    
    /**
     * @param l The long integer to print.
     */
    public static void print(long l)
    {
        System.out.print(toString(l));
    }
    
    /**
     * @param f The floating point number to print.
     */
    public static void print(float f)
    {
        System.out.print(toString(f));
    }
    
    /**
     * @param d The double precision floating point number to print.
     */
    public static void print(double d)
    {
        System.out.print(toString(d));
    }
    
    /**
     * @param obj The Object to print.
     */
    public static void print(Object obj)
    {
        System.out.print(toString(obj));
    }
    
    /**
     * Prints nothing.
     */
    public static void print()
    {
        System.out.print("");
    }
    
    /**
     * @param str The String to print on a new line.
     */
    public static void printLine(String str)
    {
        System.out.println(str);
    }
    
    /**
     * @param b The boolean to print on a new line.
     */
    public static void printLine(boolean b)
    {
        System.out.println(toString(b));
    }
    
    /**
     * @param c The character to print on a new line.
     */
    public static void printLine(char c)
    {
        System.out.println(toString(c));
    }
    
    /**
     * @param i The integer to print on a new line.
     */
    public static void printLine(int i)
    {
        System.out.println(toString(i));
    }
    
    /**
     * @param l The long integer to print on a new line.
     */
    public static void printLine(long l)
    {
        System.out.println(toString(l));
    }
    
    /**
     * @param f The floating point number to print on a new line.
     */
    public static void printLine(float f)
    {
        System.out.println(toString(f));
    }
    
    /**
     * @param d The double precision floating point number to print on a new line.
     */
    public static void printLine(double d)
    {
        System.out.println(toString(d));
    }
    
    /**
     * @param obj The Object to print on a new line.
     */
    public static void printLine(Object obj)
    {
        System.out.println(toString(obj));
    }
    
    /**
     * Prints an empty line.
     */
    public static void printLine()
    {
        System.out.println();
    }
    
    /**
     * @param stringToDisplay The String to display before asking the user for input.
     * @return The user's input.
     */
    public static String getInput(String stringToDisplay)
    {
        
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in));
        
        String userInput = null;
        
        print(stringToDisplay);
        
        try
        {
            userInput = lineReader.readLine();
        }
        catch (IOException ioe)
        {
            printLine("ERROR OCCURRED ATTEMPTING TO READ INPUT");
        }
        
        return (userInput);
    }
    
    /**
     * A default for getInput that displays no string.
     * @return The user's input.
     */
    public static String getInput()
    {
        return (getInput(""));
    }
    
    private static String toString(boolean booleanToConvert)
    {
        return (booleanToConvert ? "true" : "false");
    }
    
    private static String toString(char charToConvert)
    {
        return (String.valueOf(charToConvert));
    }
    
    private static String toString(int intToConvert)
    {
        return (String.valueOf(intToConvert));
    }
    
    private static String toString(long longToConvert)
    {
        return (String.valueOf(longToConvert));
    }
    
    private static String toString(float floatToConvert)
    {
        return (String.valueOf(floatToConvert));
    }
    
    private static String toString(double doubleToConvert)
    {
        return (String.valueOf(doubleToConvert));
    }
    
    private static String toString(Object object)
    {
        return (String.valueOf(object));
    }
}