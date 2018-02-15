package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class CSVCreating {
    private ArrayList<String> titles;
    private ArrayList<UniqueWords> uniqueWordsArrayList;

    public CSVCreating(ArrayList<String> titles, ArrayList<UniqueWords> uniqueWordsArrayList) {
        this.titles = titles;
        this.uniqueWordsArrayList = uniqueWordsArrayList;
    }

    public void createCSV() throws IOException {
        for (int i = 0; i < titles.size(); i++) {
            HashSet<String> words = uniqueWordsArrayList.get(i).getWords();
            Iterator<String> wordsIterator = words.iterator();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(titles.get(i)),
                    "windows-1251"));
            while (wordsIterator.hasNext()) {
                bw.write(wordsIterator.next() + ";");
            }
            bw.flush();
            bw.close();
        }
    }

}
