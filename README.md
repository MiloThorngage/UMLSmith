# Project Name: UMLSmith, Uml to Code converter
## Description

This project provides a simple tool that converts UML class diagrams into Java code. 
It automates the process of generating class files from UML class diagrams, saving time and frustration.

## Requirements

This project requires: 
  1. recent version of Java.
  2. Maven
  4. Tesseract OCR (Program will ask if you have it installed and will automatically install it for you if you're on Linux or MacOS (requires Homebrew for MacOS). If you are on Windows or another OS, it will give you a link to the install page).
  5. an IDE (VSCode is recommended)
  


## How to install
  1. Clone the repository
  2. open the project folder in your preferred IDE
  3. Take a screenshot of a UML class (nothing else should be in the picture. See "input.png" as an example. The text should be easily read
  4. Put the target image (most formats should work) of your UML in the `~/UMLSmith` folder
  5. In your IDE's terminal, go to the folder `~/UMLSmith/demo`
  6. run `mvn install` in your terminal
  7. run the program using your IDE
And now your Class should be outputted as a .Java File!


If the generated .java file has incorrect readings, you can look at the generated .txt file to see if your image has been read correctly. If not, your screenshot could be unclear. Replace the screenshot with one that's better readable (clearer screenshot or different font).
