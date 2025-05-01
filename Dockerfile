# Use the eclipse-temurin base image with JDK 17
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY arbitraryarithmetic/ /app/arbitraryarithmetic/
COPY MyInfArith.java /app/MyInfArith.java
COPY build.xml /app/
COPY coderunner.py /app/

# Install Ant (required for building the project)
RUN apt-get update && apt-get install -y ant

# Build the project using Ant
RUN ant jar

# Define the entry point to run the MyInfArith program
# Example: java -jar dist/ArbitraryArithmetic.jar float div 5.5 2
ENTRYPOINT ["java", "-jar", "/app/arbitraryarithmetic/aarithmetic.jar"] 
CMD ["int", "add", "1", "1"]