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
|   ├── report.pdf              # pdf of report
|   ├── report.tex              # report of project
|   └── uml.png                 # uml image made by plant uml
|
├── TEST CASES/
|   ├── input.txt               # test cases given here
|   ├── expected_ouput.tex      # expected ans of test cases are here
|   └── test.py                 # script to run test cases
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

- Open `report.pdf` to view the project documentation and how to use this library, including the UML class diagram.

## Project Features

- Supports arbitrary-precision arithmetic for integers (`AInteger`) and floating-point numbers (`AFloat`).
- Operations: addition, subtraction, multiplication, division.
- Floating-point division precision up to 1000 digits.
- Command-line interface via `MyInfArith`.
- Containerized using Docker for portability.