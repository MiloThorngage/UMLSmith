package com.example;

import java.util.Scanner;
import java.io.IOException;

// Interface for OS-specific logic
interface OSHandler {
    void handleOS(Scanner scanner);
}

// Windows-specific logic
class WindowsHandler implements OSHandler {
    @Override
    public void handleOS(Scanner scanner) {
        System.out.println("Have you installed Tesseract? y/n ");
        String tesseract = scanner.nextLine();
        if (tesseract.equalsIgnoreCase("n")) {
            System.out.println("Please install Tesseract before running this program.");
            System.out.println("https://github.com/UB-Mannheim/tesseract/wiki");
            System.exit(0);
        }
    }
}

// macOS-specific logic
class MacHandler implements OSHandler {
    @Override
    public void handleOS(Scanner scanner) {
        System.out.println("You are running macOS.");
        System.out.println("Do you want to install Tesseract OCR (requires HomeBrew)? y/n");
        String tesseract = scanner.nextLine();
        if (tesseract.equalsIgnoreCase("y")) {
            try {
                System.out.println("Installing Tesseract using Homebrew...");
                ProcessBuilder processBuilder = new ProcessBuilder("brew", "install", "tesseract");
                processBuilder.inheritIO();
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
    }
}

// Linux-specific logic
class LinuxHandler implements OSHandler {
    @Override
    public void handleOS(Scanner scanner) {
        System.out.println("You are running Linux.");
        System.out.println("Do you want to install Tesseract OCR? y/n");
        String tesseract = scanner.nextLine();
        if (tesseract.equalsIgnoreCase("y")) {
            try {
                System.out.println("Installing Tesseract using apt-get...");
                ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "install", "tesseract-ocr");
                processBuilder.inheritIO();
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
    }
}

// Unknown OS logic
class UnknownOSHandler implements OSHandler {
    @Override
    public void handleOS(Scanner scanner) {
        System.out.println("Unknown OS.");
        System.out.println("Have you installed Tesseract? y/n ");
        String tesseract = scanner.nextLine();
        if (tesseract.equalsIgnoreCase("n")) {
            System.out.println("Please install Tesseract before running this program.");
            System.out.println("https://tesseract-ocr.github.io/tessdoc/Installation.html");
            System.exit(0);
        }
    }
}

// Main CheckOS class
public class CheckOS {
    public static void checkOS(Scanner scanner) {
        // Get the OS name
        String osName = System.getProperty("os.name").toLowerCase();

        // Determine the appropriate OSHandler
        OSHandler osHandler;
        if (osName.contains("win")) {
            osHandler = new WindowsHandler();
        } else if (osName.contains("mac")) {
            osHandler = new MacHandler();
        } else if (osName.contains("nix") || osName.contains("nux")) {
            osHandler = new LinuxHandler();
        } else {
            osHandler = new UnknownOSHandler();
        }

        // Use polymorphism to handle the OS
        osHandler.handleOS(scanner);
    }
}
