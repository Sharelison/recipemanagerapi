package nl.abn.recipemanager.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abn.recipemanager.controller.exception.RecipeNotFoundException;
import nl.abn.recipemanager.domain.api.request.RecipeQuery;
import nl.abn.recipemanager.domain.api.request.RecipeRequest;
import nl.abn.recipemanager.domain.api.response.RecipeResponse;
import nl.abn.recipemanager.domain.db.Recipe;
import nl.abn.recipemanager.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final QueryToSpecConverter queryToSpecConverter;
    private final ModelMapper modelMapper;

    public List<RecipeResponse> findAll() {
        log.info("Fetching all recipes");
        return recipeRepository.findAll(null).stream()
            .map(r -> modelMapper.map(r, RecipeResponse.class))
            .collect(Collectors.toList());
    }

    public List<RecipeResponse> searchRecipes(RecipeQuery query) {
        return recipeRepository.findAll(queryToSpecConverter.toSpecification(query)).stream()
            .map(r -> modelMapper.map(r, RecipeResponse.class))
            .collect(Collectors.toList());
    }

    @Transactional
    public RecipeResponse createRecipe(RecipeRequest request) {
        log.info("Creating new recipe");
        log.debug("New recipe request {}", request);
        Recipe recipe = modelMapper.map(request, Recipe.class);
        recipe.setId(UUID.randomUUID());
        recipeRepository.save(recipe);

        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @Transactional
    public RecipeResponse updateRecipe(UUID recipeId, RecipeRequest request) {
        if(!recipeRepository.existsById(recipeId)) {
            log.warn("Recipe with ID {} does not exist, failed to update", recipeId);
            throw new RecipeNotFoundException("Recipe does not exist");
        }

        log.info("Updating recipe with ID {}", recipeId);
        Recipe recipe = modelMapper.map(request, Recipe.class);
        recipe.setId(recipeId);
        recipeRepository.save(recipe);

        return modelMapper.map(recipe, RecipeResponse.class);
    }

    @Transactional
    public void deleteRecipe(UUID recipeId) {
        log.info("Deleting recipe with ID {}", recipeId);
        recipeRepository.deleteById(recipeId);
    }
}
