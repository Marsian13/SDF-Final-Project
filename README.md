# Arbitrary Precision Arithmetic Library

This project is part of the Software Development Fundamentals (SDF) course (CS1023, Jan-May 2025). It implements an arbitrary-precision arithmetic library in Java, supporting operations on integers (`AInteger`) and floating-point numbers (`AFloat`) with unlimited size and up to 30 digits of precision for floating-point division. The library includes a command-line interface via `MyInfArith`.

## Project Structure

SDF PROJECT/
│
├── arbitraryarithmetic/
│   ├── AInteger.java           # Implementation for integers
│   ├── AFloat.java             # Implementation for floats
│   └── aarithmetic.jar         # Compiled JAR file containing arithmetic classes
|
├── Latex report/
|   ├── report.tex              # report of project
|   └── uml.png                 # uml image made by plant uml
|
├── MyInfArith.java             # Main driver program
├── build.xml                   # Apache Ant build script
├── Dockerfile                  # Docker instructions for containerizing the project
├── coderunner.py               # Python helper script for testing/running code
└── README.md                   # Project documentation (this file)

## Prerequisites

- **Java**: JDK 17 or later (e.g., Eclipse Temurin JDK 17).
- **Ant**: For building the project.
- **Docker**: For running the containerized version (optional).
- **LaTeX**: For compiling the report (e.g., MiKTeX, TeX Live).
- **Python**: For running `coderunner.py` (optional).

## Build and Run Instructions

### Local Setup
1. **Compile the Project Using Ant**:
- Run the following command to compile the Java source files:
ant compile

text

Copy

2. **Create a JAR File**:
- Build the JAR file using Ant:
ant jar

text

Copy
- This creates `dist/ArbitraryArithmetic.jar`.

3. **Run the Program**:
- **Using Ant**:
- Compiles and runs predefined test cases:
ant run

text

Copy
- Alternatively, just run:
ant

text

Copy

- **Using the JAR File**:
- Run the program with custom arguments (e.g., type: float, operation: div, num1: 5.5, num2: 2):
java -jar dist/ArbitraryArithmetic.jar float div 5.5 2

text

Copy
Output: `2.75`

- **Using the Python Script**:
- Execute the program via `coderunner.py` (ensure the JAR file exists):
python3 coderunner.py float div 5.5 2

text

Copy
Output: `2.75`
- Note: If the JAR file doesn’t exist, create it using `ant jar`.

- **Manual Compilation and Execution**:
- Compile `MyInfArith.java` (and its dependencies) manually:
javac -d . arbitraryarithmetic/*.java

text

Copy
This compiles all Java files in the `arbitraryarithmetic/` directory and places the `.class` files in the same directory structure.
- Run the program directly:
java arbitraryarithmetic.MyInfArith float div 5.5 2

text

Copy
Output: `2.75`

### Using Docker
1. **Pull the Docker Image**:
- The project is containerized and available on Docker Hub:
docker pull vansh132005/sdfproject:latest

text

Copy

2. **Run the Container**:
- Run the container with default arguments (`float div 5.5 2`):
docker run --rm vansh132005/sdfproject:latest

text

Copy
Output: `2.75`

- Run with custom arguments (e.g., type: int, operation: add, num1: 12345, num2: 67890):
docker run --rm vansh132005/sdfproject int add 12345 67890

text

Copy
Output: `80235`

- **Build the Docker Image Locally (Optional)**:
- Build the image:
docker build -t vansh132005/sdfproject .

text

Copy
- Run the container:
docker run --rm vansh132005/sdfproject float div 5.5 2

text

Copy

## Compiling the LaTeX Report

1. **Install LaTeX**:
- On Linux:
sudo apt-get install -y texlive-full

text

Copy
- Or use MiKTeX/TeX Live for your OS.

2. **Compile the Report**:
- Navigate to the `Latex_report/` directory:
cd Latex_report
pdflatex report.tex


- Open `report.pdf` to view the project documentation, including the UML class diagram.

## Project Features

- Supports arbitrary-precision arithmetic for integers (`AInteger`) and floating-point numbers (`AFloat`).
- Operations: addition, subtraction, multiplication, division.
- Floating-point division precision up to 1000 digits.
- Command-line interface via `MyInfArith`.
- Containerized using Docker for portability.

