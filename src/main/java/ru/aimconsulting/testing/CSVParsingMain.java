package ru.aimconsulting.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVParsingMain {

    private static ArrayList<String> titles = new ArrayList<>();
    private static HashMap<String, Integer> titlesHashMap = new HashMap<>();
    private static ArrayList<UniqueWords> uniqueWordsArrayList = new ArrayList<>();

    public static void main(String[] args) {
        Integer lock1 = 0;
        Integer lock2 = 0;
        ArrayList<Thread> treadsArray = new ArrayList<>();
        for (String filename: args)
        {
            Thread t = new Thread(new CSVParsing(filename, titles, titlesHashMap, uniqueWordsArrayList, lock1, lock2));
            treadsArray.add(t);
            t.start();
        }
        for (Thread t : treadsArray) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            new CSVCreating(titles, titlesHashMap, uniqueWordsArrayList).createCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
