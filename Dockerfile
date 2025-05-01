FROM eclipse-temurin

# Copy the project files into the container
COPY arbitraryarithmetic/aarithmetic.jar app/my.jar

ENTRYPOINT ["java", "-jar", "app/my.jar"] 
CMD ["int", "add", "1", "1"]