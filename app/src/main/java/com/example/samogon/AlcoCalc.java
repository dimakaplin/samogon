package com.example.samogon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlcoCalc {
    private static String waterVol = "0";
    private static String nextVol = "0";
    private static String amountBottle = "0";


    public static String getWaterVol() {
        return waterVol;
    }

    public static String getNextVol() {
        return nextVol;
    }

    public static String getAmountBottle() {
        return amountBottle;
    }

    public static void addWater(double volume, double nowAlco, double nextAlco) {


        double water = ((volume * 1000) * (nowAlco / nextAlco - 1)) / 1000;

        String checkString = String.format("%.2f",water);

        waterVol = regCheck(checkString, water);

        double next = water + volume;

        String nextCheckString = String.format("%.2f", next);

        nextVol = regCheck(nextCheckString, next);

        double bottles = next / 0.5;

        // String bottleCheckString = String.format("%.2f", bottles);

        amountBottle = String.format("%.0f", bottles);

    }

    public static String regCheck(String checkString, double checkDouble) {
        Pattern p = Pattern.compile("^([0-9]*,[0]+)$");
        Matcher m = p.matcher(checkString);
        String result = m.matches() ? String.format("%.0f", checkDouble) : checkString;
        return result;
    }
}
