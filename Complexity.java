/**
 * 2021F CS 570-B. Homework Assignment 2.
 * Implement a number of methods for a class Complexity. These methods should  be  implemented  using  for  loops,
 * as  seen  in  class  (except  for  the  extra-credit  one). In addition,
 * each  of  these  methods  should  print  out  the  value  of  an  accumulator  that  counts  the number of “operations” performed.
 * The notion of “operation” should be taken loosely;
 * the idea is that if you are requested to implement a method of time complexity O(n),
 * then it should print out values from 1 to n (or close enough).
 *
 * CWID: 20007427
 * @Truong
 * @Date 09/28/2021
 */


public class Complexity {

    //A  method1 that has time complexity O(n^2)
    public void method1(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Operation method1 is " + counter);
                counter++;
            }
        }
        System.out.println("Time complexity of method1 (O(n^2)) is " + counter);
    }


    //A method2 that has time complexity O(n^3)
    public void method2(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = 0; t < n; t++) {
                    System.out.println("Operation method2 (O(n^3)) are " + counter);
                    counter++;
                }
            }
        }
        System.out.println("Time complexity of method2 is " + counter);
    }


    //A method3 that has time complexity O(logn)
    public void method3(int n) {
        int counter = 0;
        for (int i = n; i > 1; i = i / 2) {
            System.out.println("Operation method 3 are " + counter);
            counter++;
        }
        System.out.println("Time complexity of method3 (O(logn)) is " + counter);
    }


    //A method4 that has time complexity O(nlogn)
    public void method4(int n) {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n; j > 1; j = j / 2) {
                System.out.println("Operation method 4 is " + counter);
                counter++;
            }
        }
        System.out.println("Time complexity of method4 (O(nlogn)) is " + counter);
    }


    //A method5 that has time complexity O(loglogn)
    public void method5(int n) {
        int counter = 0;
        for (double i = 2; i < n; i = i * i) {
            System.out.println("Operation method5 is " + counter);
            counter++;
        }
        System.out.println("Time complexity of method5 (O(loglogn)) is " + counter);
    }


    //A method6  that  has  time  complexity O(2^n)
    static int sum = 0;
    public int method6(int n) {
        if (n == 0) { //Base case 1
            sum++;
            System.out.println("Operation method6 is " + sum);
            return sum;
        } else if (n == 1) { //Base case 2
            System.out.println("Operation method6 is " + sum);
            sum++;
            System.out.println("Operation method6 is " + sum);
            sum++;
            return sum;
        }
        //Recursive case
        method6(n - 1);
        method6(n - 1);
        return sum;
    }
}
