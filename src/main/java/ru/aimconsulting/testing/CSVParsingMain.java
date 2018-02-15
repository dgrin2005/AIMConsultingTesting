package ru.aimconsulting.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CSVParsingMain {

    private static HashSet<String> titles = new HashSet<>();
    private static HashMap<String, Integer> titlesHashMap = new HashMap<>();
    private static ArrayList<UniqueWords> uniqueWordsArrayList = new ArrayList<>();

    public static void main(String[] args) {
        for (String filename: args)
        {
            new CSVParsing(filename, titles, titlesHashMap, uniqueWordsArrayList).run();
        }


        try {
            new CSVCreating(titles, titlesHashMap, uniqueWordsArrayList).createCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
