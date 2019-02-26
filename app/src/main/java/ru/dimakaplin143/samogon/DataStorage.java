package ru.dimakaplin143.samogon;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static Map<String, String> calcMap = new HashMap<>();

    public static void addCalcState(String key, String state) {
        calcMap.put(key, state);
    }
    public static String getCalcState(String key) {
        return calcMap.get(key);
    }
}
