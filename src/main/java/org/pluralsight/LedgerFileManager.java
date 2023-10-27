package org.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class LedgerFileManager {

    private static String fileName;

    public LedgerFileManager(String fileName) {
        LedgerFileManager.fileName = fileName;
    }

    private static String[] getLedgerAsCVSArray(ArrayList<Transaction> ledger) {
        String[] fileContents = new String[ledger.size()];
        for(int i = 0; i < fileContents.length; i++) {
            fileContents[i] = ledger.get(i).toCSVFormat();
        }
        return fileContents;
    }

    /*-----Saving / Loading-----*/

    public String[] loadFromFile() {
        String fileContents = readFromFile();
        return fileContents.split("\n");
    }

// not implemented but is a useful function to overwrite the file in a way
//    public void saveToFile(ArrayList<Transaction> ledger) {
//        String[] fileContents = getLedgerAsCVSArray(ledger);
//        writeToFile(fileContents);
//    }

    public void saveToFile(Transaction transaction) {
        String[] fileLine = {transaction.toCSVFormat()};
        writeToFile(fileLine);
    }

    public void makeFile() {
        File transactionsFile = new File(fileName);
        try {
            //creates file if it does not exist already
            transactionsFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*-----FileIO methods-----*/

    private static String readFromFile() {
        String file = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                file = file.concat(fileLine + "\n");
            }
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not read from the file: " + fileName);
            e.printStackTrace();
        }
        return file;
    }

    private static void writeToFile(String[] fileContents) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            for (String fileLine : fileContents) {
                bufferedWriter.write(fileLine);
            }
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not write to this file: " + fileName);
            e.printStackTrace();
        }
    }
}
