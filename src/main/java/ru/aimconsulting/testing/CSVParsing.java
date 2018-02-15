package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class CSVParsing implements Runnable {

    private String filename;
    private ArrayList<String> titles;
    private ArrayList<UniqueWords> uniqueWordsArrayList;
    private final Integer lock;

    public CSVParsing(String filename, ArrayList<String> titles, ArrayList<UniqueWords> uniqueWordsArrayList,
                      Integer lock) {
        this.filename = filename;
        this.titles = titles;
        this.uniqueWordsArrayList = uniqueWordsArrayList;
        this.lock = lock;
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
                    synchronized (lock) {
                        if (!titles.contains(currentTitle)) {
                            titles.add(currentTitle);
                            uniqueWordsArrayList.add(new UniqueWords());
                        }
                        titlewordsIndexHashMap.put(titlewordsIndex++, titles.indexOf(currentTitle));
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
