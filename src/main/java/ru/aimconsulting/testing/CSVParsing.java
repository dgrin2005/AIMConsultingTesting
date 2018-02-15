package ru.aimconsulting.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CSVParsing implements Runnable {

    String filename;

    public CSVParsing(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {

        HashSet<String> titles = new HashSet<>();
        HashMap<String, Integer> titlesHashMap = new HashMap<>();
        ArrayList<UniqueWords> uniqueWordsArrayList = new ArrayList<>();
        int index = 0;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String s = br.readLine();
            s.trim();
            String[] titlewords = s.split(";");
            if (s != null) {
                HashMap<Integer, Integer> titlewordsIndexHashMap = new HashMap<>();
                int titlewordsIndex = 0;
                for (String titleword: titlewords) {
                    String currentTitle = titleword.trim();
                    int currentIndex;
                    titles.add(currentTitle);
                    if (titlesHashMap.containsKey(currentTitle)){
                        currentIndex = titlesHashMap.get(currentTitle);
                    } else {
                        currentIndex = index;
                        titlesHashMap.put(currentTitle, index++);
                    }
                    titlewordsIndexHashMap.put(titlewordsIndex++, currentIndex);
                }
                for (int i = uniqueWordsArrayList.size(); i < titles.size(); i++) {
                    uniqueWordsArrayList.add(new UniqueWords());
                }
                while((s = br.readLine())!= null) {
                    s.trim();
                    String[] words = s.split(";");
                    int wordsIndex = 0;
                    for (String word: words) {
                        String currentWord = word.trim();
                        UniqueWords currentUniqueWords = uniqueWordsArrayList.get(titlewordsIndexHashMap.get(wordsIndex++));
                        currentUniqueWords.getWords().add(currentWord);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
