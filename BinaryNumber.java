/**
 * 2021F CS 570-B. Homework Assignment 1.
 * Define a class BinaryNumber that represents binary numbers and a few simple operations on them, as indicated below.
 * This assignment requests that a number of operations be supported. They are divided into two groups.
 * The first is a set basic operations, the second is slightly more challenging and addresses addition of binary numbers.
 *
 * CWID: 20007427
 * @Truong
 * @Date 09/10/2021
 */

import java.util.Arrays;

public class BinaryNumber {
    //Define the field: array
    private int data[];

    //Define the field: overflow
    private boolean overflow = false;

    //Access overflow
    public boolean getOverflow() {
       return overflow;
    }

    //Create a binary number of length consisting only of zeros
    public BinaryNumber(int length) {
        data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = 0;
        }
    }

    //Create a binary number given a string
    public BinaryNumber(String str) {
        int length = str.length(); //define length of binary number equals to the length of input string
        data = new int[length];
        for (int i = 0; i < length; i++) {
            char a = str.charAt(i); //return the char value at the specified index
            data[i] = Character.getNumericValue(a); //return the int value that the specified char represents
        }
    }

    //Determine the length of a binary number
    public int getLength() {
        return data.length;
    }

    //Obtain a digit of a binary number given an index
    public int getDigit(int index) {
        if (index >= 0 && index < data.length) {
            return data[index];
        } else {
            System.out.println("The length of the binary number is " + data.length + ".");
            System.out.println("index " + index + " is out of bounds.");
            return -1;
        }
    }

    //Shift all digits in a binary number any number of places to the right
    public void shiftR(int amountToShift) {
        int emptySlot = 0;
        //Count empty slots of data
        for (int i = data.length -1; i >= 0; i--) {
            if (data[i] == 0) {
                emptySlot++;
            } else {
                break;
            }
        }
        //Compare emptySlot with AmountToShit to decide whether we need to insert new slots
        int newDataLength;
        if (emptySlot >= amountToShift) {
            newDataLength = data.length;
        } else {
            newDataLength = data.length + amountToShift - emptySlot;
        }
        int[] newData = new int[newDataLength];
        for (int i = amountToShift; i < newDataLength; i++) {
            newData[i] = data[i - amountToShift];
        }
        data = newData;
    }

    //Transform a binary number to its decimal notation
    public int toDecimal() {
        int decimal = 0;
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++){
            decimal += (int) (data[i] * Math.pow(2, i));
        }
        return decimal;
    }

    //Add two same-length binary numbers
    public void add(BinaryNumber secondBinaryNumber) {
        int carriedDigit = 0;

        //Check whether the lengths of the binary numbers coincide
        if (secondBinaryNumber.getLength() == data.length) {
            for (int i = 0; i < data.length; i++) {
                int a = data[i];
                int b = secondBinaryNumber.getDigit(i);
                int sum = a + b + carriedDigit;

                if (sum == 0) {
                    carriedDigit = 0;
                    data[i] = 0;
                }
                if (sum == 1) {
                    carriedDigit = 0;
                    data[i] = 1;
                }
                if (sum == 2) {
                    carriedDigit = 1;
                    data[i] = 0;
                }
                if (sum == 3) {
                    carriedDigit = 1;
                    data[i] = 1;
                }
            }
            if (carriedDigit == 1) {
                overflow = true;
            }
        }
        else {
            System.out.println("Sorry!!! The lengths of two binary numbers are not equal.");
        }
    }

    //Transform a binary number to a String. If the number is the result of an overflow, return "Overflow" string
    public String toString() {
        if (overflow) {
            return "Overflow";
        }
        else {
            String s = "";
            for (int i = 0; i < data.length; i++) {
                s += String.valueOf(data[i]);
            }
            return s;
        }
    }

    //Clear the overflow flag
    public void clearOverflow() {
        overflow = false;
    }
}
