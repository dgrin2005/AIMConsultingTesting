package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class CSVCreating {
    private HashSet<String> titles;
    private HashMap<String, Integer> titlesHashMap;
    private ArrayList<UniqueWords> uniqueWordsArrayList;

    public CSVCreating(HashSet<String> titles, HashMap<String, Integer> titlesHashMap,
                       ArrayList<UniqueWords> uniqueWordsArrayList) {
        this.titles = titles;
        this.titlesHashMap = titlesHashMap;
        this.uniqueWordsArrayList = uniqueWordsArrayList;
    }

    public void createCSV() throws IOException {
        Iterator<String> titlesIterator = titles.iterator();
        BufferedWriter bw = null;
        while (titlesIterator.hasNext()) {
            String title = titlesIterator.next();
            HashSet<String> words = uniqueWordsArrayList.get(titlesHashMap.get(title)).getWords();
            Iterator<String> wordsIterator = words.iterator();
            try {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(title), "utf-8"));
                while (wordsIterator.hasNext()) {
                    bw.write(wordsIterator.next()+";");
                }
                bw.flush();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

}
