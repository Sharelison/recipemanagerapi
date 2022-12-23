package nl.abn.recipemanager.controller;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import nl.abn.recipemanager.domain.api.request.RecipeQuery;
import nl.abn.recipemanager.domain.api.request.RecipeRequest;
import nl.abn.recipemanager.domain.api.response.RecipeResponse;
import nl.abn.recipemanager.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1")
@RestController
public class RecipeController {

    private final RecipeService service;

    @RequestMapping(value = "recipes", method = RequestMethod.GET)
    public List<RecipeResponse> findAllRecipes() {
        return service.findAll();
    }

    @RequestMapping(value = "recipes/search", method = RequestMethod.GET)
    public List<RecipeResponse> searchRecipes(@Valid RecipeQuery query) {
        return service.searchRecipes(query);
    }

    @RequestMapping(value = "recipe/create", method = RequestMethod.POST)
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody RecipeRequest request) {
        RecipeResponse recipeResponse = service.createRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(recipeResponse);
    }

    @RequestMapping(value = "recipe/{id}/update",  method = RequestMethod.PATCH)
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable("id") UUID recipeId,
        @Valid @RequestBody RecipeRequest request) {
        RecipeResponse recipeResponse = service.updateRecipe(recipeId, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(recipeResponse);
    }

    @RequestMapping(value = "recipe/{id}/delete",  method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") UUID recipeId) {
        service.deleteRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
