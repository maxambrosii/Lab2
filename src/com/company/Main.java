package com.company;

import java.util.Scanner;

public class Main {


    public static int minPath(int numberPoints, int[] currentFlag, long[] longs) {
        int res = 0;
        for (int i = 0; i < numberPoints; i++) {
            if (currentFlag[i] == 0) res = i;
        }

        for (int i = 0; i < numberPoints; i++)
            if ((longs[res] > longs[i]) && (currentFlag[i] == 0)) res = i;
        return res;
    }

    public static String convert(int x, int base) {
        boolean negative = false;
        StringBuilder s = new StringBuilder();
        if (x == 0)
            return "0";
        negative = (x < 0);
        if (negative)
            x = -1 * x;
        while (x != 0) {
            s.insert(0, (x % base)); // add char to front of s
            x = x / base; // integer division gives quotient
        }
        if (negative)
            s.insert(0, "-");
        return s.toString();
    } // convert

    public static void main(String[] args) {

        int countOfPoints, pathCurrent, xN, xK;
        int[] currentFlag = new int[11];
        long[][] pointRemember = new long[11][11];
        long[] longs = new long[11];
        String str = "";
        StringBuilder path = new StringBuilder();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Input quantity of points: ");
        countOfPoints = scanner.nextInt();

        for (int i = 0; i < countOfPoints; i++)
            for (int j = 0; j < countOfPoints; j++)
                pointRemember[i][j] = 0;

        for (int i = 0; i < countOfPoints; i++) {
            for (int j = i + 1; j < countOfPoints; j++) {
                System.out.print("Input the distance from point  X" + (i + 1) + " to point X" + (j + 1) + ": ");
                pointRemember[i][j] = scanner.nextLong();
            }
        }

        System.out.print("   ");

        for (int i = 0; i < countOfPoints; i++) System.out.print("    X" + (i + 1));
        System.out.println("\n");

        for (int i = 0; i < countOfPoints; i++) {
            System.out.print("X" + (i + 1) + "     ");
            for (int j = 0; j < countOfPoints; j++) {
                System.out.print(pointRemember[i][j] + "     ");
                pointRemember[j][i] = pointRemember[i][j];
            }
            System.out.print("\n");
        }

        for (int i = 0; i < countOfPoints; i++)
            for (int j = 0; j < countOfPoints; j++)
                if (pointRemember[i][j] == 0) pointRemember[i][j] = 65535; //INFINIFY IN long

        System.out.print("Enter the starting point: "); xN = scanner.nextInt();
        System.out.print("Enter the end point: "); xK = scanner.nextInt();

        xK--;
        xN--;

        if (xN == xK) {
            System.out.println("The starting and end points coinciding.");
            System.exit(0);
        }

        for (int i = 0; i < countOfPoints; i++) {
            currentFlag[i] = 0;
            longs[i] = 65535;
        }
        longs[xN] = 0;
        currentFlag[xN] = 1;
        pathCurrent = xN;

        str = convert(xN + 1, 10);
        path.append('X');
        path.append(str);
        int k = 0;
        do {
            for (int i = 0; i < countOfPoints; i++)
                if ((pointRemember[pathCurrent][i] != (long) 65535) && (currentFlag[i] == 0) && (i != pathCurrent)) {
                    if (longs[i] > longs[pathCurrent] + pointRemember[pathCurrent][i]) {
                        str = convert(i + 1, 10);
                        path.append("-X");
                        path.append(str);
                        k++;
                    }
                    longs[i] = Math.min(longs[i], (longs[pathCurrent] + pointRemember[pathCurrent][i]));
                }

            pathCurrent = minPath(countOfPoints, currentFlag, longs);
            currentFlag[pathCurrent] = 1;
        }
        while (pathCurrent != xK);

        if (longs[pathCurrent] != 65535) {
            System.out.println("Current path:" + path);
            System.out.println("Length path: " + longs[pathCurrent]);
        } else
            System.out.println("There is no such path!");
    }
}