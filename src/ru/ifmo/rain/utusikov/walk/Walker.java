package ru.ifmo.rain.utusikov.walk;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Walker {

    private final String INPUT;
    private final String OUTPUT;


    Walker(String input, String output) {
        INPUT = input;
        OUTPUT = output;
    }
    void execute() {
        BufferedWriter out;
        Path outputFile;
        try (BufferedReader in = new BufferedReader(new FileReader(INPUT), 2048)) {
            try {
                outputFile = Paths.get(OUTPUT);
                if (outputFile.getParent() != null) {
                    Files.createDirectories(outputFile.getParent());
                }
                try {
                    out = new BufferedWriter(new FileWriter(outputFile.toFile()));
                    try {
                        String newFile = in.readLine();
                        while(newFile != null) {
                            countHash(newFile, out);
                            newFile = in.readLine();
                        }
                    } catch (IOException e) {
                        System.err.println("Couldn't read a new name of file!");
                    } finally {
                        out.close();
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Couldn't find a output file!");
                } catch (IOException e) {
                    System.err.println("Couldn't open a output file!");
                }
            } catch (InvalidPathException e) {
                System.err.println("Invalid path!");
            } catch (IOException e) {
                System.err.println("Error while creating directories!");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find a input file!");
        }
        catch (IOException e) {
            System.err.println("Couldn't open input file!");
        }
    }

    private void countHash(String newFile, BufferedWriter out) {
        HashFNV  hash = new HashFNV();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(newFile), 2048)) {
            byte[] bytes = new byte[2048];
            try {
                int len = in.read(bytes);
                while(len > 0) {
                    hash.compute(bytes, len);
                    len = in.read(bytes);
                }
                write(hash.getHash(), newFile, out);
            } catch (IOException e) {
                System.err.println("Couldn't read a new byte!");
                write(0, newFile, out);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find a input file!");
            write(0, newFile, out);
        } catch (IllegalArgumentException e) {
            System.err.println("Small size of buffer!");
            write(0, newFile, out);
        } catch (IOException e) {
            System.err.println("Error in reading process");
            write(0, newFile, out);
        } catch (SecurityException e) {
            System.err.println("Haven't access to read a file");
            write(0, newFile, out);
        }
    }

    private void write(int hash, String newFile, BufferedWriter out) {
        try {
            out.write(String.format("%08x", hash) + " " + newFile + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Failed write a hash for file: " + newFile);
            try {
                out.close();
            } catch (IOException e1) {
                System.err.println("Failed closing of output file!");
            }
        }
    }
}
