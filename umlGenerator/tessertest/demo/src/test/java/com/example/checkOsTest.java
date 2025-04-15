package com.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class checkOsTest {
    @Test
    public void testCheckOsMac() {
        // Simulate terminal input for "n"
        String simulatedInput = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Set the OS property to simulate macOS
        System.setProperty("os.name", "macOS");

        // Call the method
        CheckOS.checkOS();

        // Assert that the output contains expected text
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("You are running macOS."));

        // Reset System.in and System.out to their original states
        System.setIn(System.in);
        System.setOut(originalOut);
    }

    @Test
    public void testCheckOsWindows() {
        // Simulate terminal input for "y"
        String simulatedInput = "y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Set the OS property to simulate Windows
        System.setProperty("os.name", "windows");

        // Call the method
        CheckOS.checkOS();

        // Assert that the output contains expected text
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Have you installed Tesseract? y/n"));

        // Reset System.in and System.out to their original states
        System.setIn(System.in);
        System.setOut(originalOut);
    }

    @Test
    public void testCheckOsLinux() {
        // Simulate terminal input for "n" (user does not want to install Tesseract)
        String simulatedInput = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Set the OS property to simulate Linux
        System.setProperty("os.name", "Linux");

        // Call the method
        CheckOS.checkOS();

        // Assert that the output contains expected text
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("You are running Linux."));
        assertTrue(consoleOutput.contains("Do you want to install Tesseract? y/n"));

        // Reset System.in and System.out to their original states
        System.setIn(System.in);
        System.setOut(originalOut);
    }
}
