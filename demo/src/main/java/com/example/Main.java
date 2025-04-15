package com.example;
import java.util.Scanner;

public class Main {
    public static String outputname = "output";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CheckOS.checkOS(scanner); //checks the OS and installs Tesseract if needed
        
        System.out.println("Enter the name of the file you want to read (including extension): ");
        String filename = scanner.nextLine();// reads the next line
        scanner.close();
        ProcessFile.readFile(filename); //reads file you want and outputs it to "output.txt"
        ProcessFile.processFile(); //processes the file
        
    }
}
