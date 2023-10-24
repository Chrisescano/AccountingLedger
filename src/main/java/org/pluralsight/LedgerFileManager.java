package org.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class LedgerFileManager {

    private static String fileName;
    private static ArrayList<Transaction> transactionsBuffer;

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

    public void load() {
        String fileContents = readFromFile(fileName);
        String[] fileTokens = fileContents.split("\n");

    }

    public void save() {
        String[] fileContents = getLedgerAsCVSArray(transactionsBuffer);
        writeToFile(fileName, fileContents);
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

    private static String readFromFile(String fileName) {
        String file = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                file = file.concat(fileLine);
            }
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not read from the file: " + fileName);
            e.printStackTrace();
        }
        return file;
    }

    private static void writeToFile(String fileName, String[] file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));

            for (String fileLine : file) {
                bufferedWriter.write(fileLine);
            }
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not write to this file: " + fileName);
            e.printStackTrace();
        }
    }

    public void setTransactionsBuffer(ArrayList<Transaction> transactionsBuffer) {
        this.transactionsBuffer = transactionsBuffer;
    }
}
