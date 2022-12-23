FROM openjdk:17
ADD target/recipemanagerapi.jar /app/recipemanagerapi.jar
ENTRYPOINT [ "java", "-jar", "/app/recipemanagerapi.jar" ]