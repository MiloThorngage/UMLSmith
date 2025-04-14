package com.example;
import java.util.Scanner;
import java.io.IOException;

public class CheckOS {
    static void checkOS() {
        // Get the OS name and version
        String osName = System.getProperty("os.name").toLowerCase();
        String osVersion = System.getProperty("os.version");

        // Print the OS details
        System.out.println("Operating System: " + osName);
        System.out.println("Version: " + osVersion);
        Scanner scanner = new Scanner(System.in);

        // Optionally, check specific OS types
        if (osName.contains("win")) {
            System.out.println("Have you installed Tesseract? y/n ");
            String tesseract = scanner.nextLine();
            if (tesseract.toLowerCase().equals("n")) {
                System.out.println("Please install Tesseract before running this program.");
                System.out.println("https://tesseract-ocr.github.io/tessdoc/Installation.html");
                System.exit(0);
            }
            
        } else if (osName.contains("mac")) { //if it is a mac
            System.out.println("You are running macOS.");
            System.out.println("Do you want to install Tesseract (requires HomeBrew)? y/n");
            String tesseract = scanner.nextLine();
            if(tesseract.toLowerCase().equals("y")){ //if user says yes, it will install Tesseract
            try {
                System.out.println("Installing Tesseract using Homebrew...");
                ProcessBuilder processBuilder = new ProcessBuilder("brew", "install", "tesseract");
                processBuilder.inheritIO(); // This will redirect the output to the console
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Tesseract installation completed.");
                } else {
                    System.out.println("Tesseract installation failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            
        }


        } else if (osName.contains("nix") || osName.contains("nux")) { //if linux
            System.out.println("You are running Linux.");
            System.out.println("Do you want to install Tesseract? y/n");
            String tesseract = scanner.nextLine();
            if(tesseract.toLowerCase().equals("y")){ //if user says yes, it will install Tesseract
            try {
                System.out.println("Installing Tesseract using apt-get...");
                ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "install", "tesseract-ocr"); //installs Tesseract
                processBuilder.inheritIO(); // This will redirect the output to the console
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Tesseract installation completed.");
                } else {
                    System.out.println("Tesseract installation failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Installing Tesseract using apt-get...");
                ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "install", "libtesseract-dev");//installs libtesseract-dev (required for Tesseract on Linux)
                processBuilder.inheritIO(); // This will redirect the output to the console
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Tesseract installation completed.");
                } else {
                    System.out.println("Tesseract installation failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unknown OS."); //if it doesn't recognise the OS, it will ask if Tesseract is installed. If not, it exits the program
            System.out.println("Have you installed Tesseract? y/n ");
            tesseract = scanner.nextLine();
            if (tesseract.equals("n")) {
                System.out.println("Please install Tesseract before running this program.");
                System.out.println("https://tesseract-ocr.github.io/tessdoc/Installation.html");
                System.exit(0);
            }
        }
        scanner.close();
    }
}
}
