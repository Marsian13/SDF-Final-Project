FROM eclipse-temurin

# Copy my jar file to docker image
COPY arbitraryarithmetic/aarithmetic.jar app/my.jar

# Running throug jar file
ENTRYPOINT ["java", "-jar", "app/my.jar"] 

# Default input 
CMD ["int", "add", "1", "1"]