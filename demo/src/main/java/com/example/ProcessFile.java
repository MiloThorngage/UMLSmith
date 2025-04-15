package com.example;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ProcessFile {
    String filename;

    static void readFile(String filename) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(); // Create a new process builder to do a command in terminal
            processBuilder.command("tesseract", filename, Main.outputname); // makes command "tesseract " + filename + " " + outputname
            
            // Redirect error stream to capture potential issues
            processBuilder.redirectErrorStream(true); // Redirects error stream to the output stream of the code
            Process process = processBuilder.start(); // runs the built command in Terminal

            // Read command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); //sets up reading the output of the terminal command
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); //prints out the output of the terminal command
            }

            int exitCode = process.waitFor(); //exits terminal with the exit code of the command
            System.out.println("Exited with code: " + exitCode); 
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();//prints out the error message if there is an error
        }
    }

    static void processFile() {
        Path filePath = Paths.get(Main.outputname + ".txt"); // grabs the file output.txt

        String[] linesArray = null; // Declare linesArray outside the try block
        try {
            // Read all lines into a String[] array directly
            linesArray = Files.readAllLines(filePath).toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (linesArray == null || linesArray.length == 0) {
            System.err.println("The file is empty or could not be read.");
            return;
        }

        // Get the name of the new file from the first line of linesArray
        String className = linesArray[0].trim();
        String newFileName = className + ".java";

        // Regex to match between + or - and before :, and also capture after :
        String regex = "(?:[-+]\\s*)?(\\w+)(?:\\(([^)]*)\\))?\\s*(?::\\s*(\\w+))?";
        Pattern pattern = Pattern.compile(regex); // Compile the regex pattern
        Matcher matcher = pattern.matcher(String.join("\n", linesArray)); // Join all lines into a single string for regex matching

        String parameterRegex = "(\\w+)\\s*:\\s*(\\w+)";
        // This regex captures the parameter name and type

        File newFile = new File(newFileName); // Create a new file with the class name
        try {
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }

        try (FileWriter classWriter = new FileWriter(newFileName)) {
            // Helper method to write to the file
            FileWriterHelper writerHelper = new FileWriterHelper(classWriter);

            // Write the class declaration
            writerHelper.write("public class " + className + " {\n");

            // Process the rest of the lines using the matcher
            for (int i = 1; i < linesArray.length; i++) { // Start from the second line
                matcher.reset(linesArray[i]); // Match each line individually
                if (matcher.find()) {
                    String accessMod = null;

                    if (linesArray[i].charAt(0) == '-') {
                        accessMod = "private"; // Access modifier
                    } else if (linesArray[i].charAt(0) == '+') {
                        accessMod = "public"; // Access modifier
                    } else if (linesArray[i].charAt(0) == '#') {
                        accessMod = "protected"; // Access modifier
                    }

                    if (accessMod != null) {
                        writerHelper.write(accessMod + " "); // Write access modifier to the file
                    }

                    String methodName = matcher.group(1);  // Constructor/Method name
                    String params = matcher.group(2);      // Parameters inside parentheses
                    
                    
                    if (matcher.group(3) != null && matcher.group(1).toLowerCase() != className.toLowerCase()) {  // Return type for variables
                        String returnType = matcher.group(3); // Return type
                        if(returnType.toLowerCase().equals("str") || returnType.toLowerCase().equals("string")){
                            returnType = "String";
                        }
                        else if(returnType.toLowerCase().equals("bool") || returnType.toLowerCase().equals("boolean")){
                            returnType = "boolean";
                        }
                        else if(returnType.toLowerCase().equals("integer") || returnType.toLowerCase().equals("int")){
                            returnType = "int";
                        }
                        
 
                        writerHelper.write(returnType + " ");
                    }
                    else if (!(matcher.group(1).toLowerCase().equals( className.toLowerCase()))) {
                        writerHelper.write("void ");
                    }
                    //writerHelper.write(methodName); // Write the variable name or method name
                    //writerHelper.write(";\n"); // End the lin

                    if (matcher.group(0).contains("(")) { // Check if it is a method
                        writerHelper.write(methodName + "("); // Write method name and opening parenthesis
                        Matcher paramMatcher = Pattern.compile(parameterRegex).matcher(params);
                        boolean firstParam = true; // Track if it's the first parameter
                        while (paramMatcher.find()) {
                            String paramName = paramMatcher.group(1);   // Parameter name
                            String paramType = paramMatcher.group(2);   // Parameter type

                            if (!firstParam) {
                                writerHelper.write(", "); // Add a comma before subsequent parameters
                            }
                            writerHelper.write(paramType + " " + paramName); // Write parameter type and name
                            firstParam = false; // Set to false after the first parameter
                        }
                        writerHelper.write("){\n"); // Write closing parenthesis
                        writerHelper.write("}\n"); // Close the method
                    } else {
                        
                        writerHelper.write(methodName + ";"); // Write the variable name or method name
                        
                    }
                    writerHelper.write("\n");
                    
                }
            }

            writerHelper.write("}\n"); // Close the class
        } catch (IOException e) {
            System.out.println("An error occurred while initializing writing the file.");
            e.printStackTrace();
        }
    }

    //Helper class to handle FileWriter operations with a single try-catch block.
     
    static class FileWriterHelper {
        private final FileWriter writer;

        public FileWriterHelper(FileWriter writer) {
            this.writer = writer;
        }

        public void write(String content) {
            try {
                writer.write(content);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        }
    }
}