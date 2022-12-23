package nl.abn.recipemanager.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RecipeManagerControllerAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<String> handRecipeNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Recipe not found");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnexpectedExceptions(Exception ex) {
      log.error("Unexpected exception occurred with message {}", ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .body("Service error occurred, please try again at a later moment");
    }
}
