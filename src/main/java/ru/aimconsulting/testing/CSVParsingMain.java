package ru.aimconsulting.testing;

public class CSVParsingMain {

    public static void main(String[] args) {
        for (String filename: args)
        {
            new CSVParsing(filename).run();
        }
    }
}
