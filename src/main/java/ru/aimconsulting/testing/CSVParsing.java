package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class CSVParsing implements Runnable {

    private String filename;
    private ArrayList<String> titles;
    private HashMap<String, Integer> titlesHashMap;
    private ArrayList<UniqueWords> uniqueWordsArrayList;
    private final Integer lock1;
    private final Integer lock2;

    public CSVParsing(String filename, ArrayList<String> titles, HashMap<String, Integer> titlesHashMap,
                      ArrayList<UniqueWords> uniqueWordsArrayList, Integer lock1, Integer lock2) {
        this.filename = filename;
        this.titles = titles;
        this.titlesHashMap = titlesHashMap;
        this.uniqueWordsArrayList = uniqueWordsArrayList;
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "windows-1251"));
            String s = br.readLine();
            if (s != null) {
                String[] titlewords = s.trim().split(";");
                HashMap<Integer, Integer> titlewordsIndexHashMap = new HashMap<>();
                int titlewordsIndex = 0;
                for (String titleword: titlewords) {
                    String currentTitle = titleword.trim();
                    synchronized (lock1) {
                        if (!titles.contains(currentTitle)) {
                            titles.add(currentTitle);
                            titlesHashMap.put(currentTitle, titles.size() - 1);
                            titlewordsIndexHashMap.put(titlewordsIndex++, titles.size() - 1);
                        } else {
                            titlewordsIndexHashMap.put(titlewordsIndex++, titles.indexOf(currentTitle));
                        }
                    }
                }
                synchronized (lock2) {
                    for (int i = uniqueWordsArrayList.size(); i < titles.size(); i++) {
                        uniqueWordsArrayList.add(new UniqueWords());
                    }
                }
                while((s = br.readLine())!= null) {
                    String[] words = s.trim().split(";");
                    int wordsIndex = 0;
                    for (String word: words) {
                        String currentWord = word.trim();
                        UniqueWords currentUniqueWords = uniqueWordsArrayList.get(
                                titlewordsIndexHashMap.get(wordsIndex++));
                        currentUniqueWords.getWords().add(currentWord);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
