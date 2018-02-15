package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

class CSVCreating {
    private ArrayList<String> titles;
    private HashMap<String, Integer> titlesHashMap;
    private ArrayList<UniqueWords> uniqueWordsArrayList;

    public CSVCreating(ArrayList<String> titles, HashMap<String, Integer> titlesHashMap,
                       ArrayList<UniqueWords> uniqueWordsArrayList) {
        this.titles = titles;
        this.titlesHashMap = titlesHashMap;
        this.uniqueWordsArrayList = uniqueWordsArrayList;
    }

    public void createCSV() throws IOException {
        for (String title : titles) {
            HashSet<String> words = uniqueWordsArrayList.get(titlesHashMap.get(title)).getWords();
            Iterator<String> wordsIterator = words.iterator();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(title),
                    "windows-1251"));
            while (wordsIterator.hasNext()) {
                bw.write(wordsIterator.next() + ";");
            }
            bw.flush();
            bw.close();
        }
    }

}
