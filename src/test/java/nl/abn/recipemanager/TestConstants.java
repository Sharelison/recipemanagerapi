package nl.abn.recipemanager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import nl.abn.recipemanager.domain.api.request.RecipeRequest;
import nl.abn.recipemanager.domain.api.response.RecipeResponse;
import nl.abn.recipemanager.domain.db.Recipe;

public final class TestConstants {

    private TestConstants(){}

    //inserted in schema.sql

    public static final UUID ID_1 = UUID.fromString("09aad778-18cd-44d1-9050-58154525d013");
    public static final String RECIPE_NAME_1 = "Chesscake";
    public static final Integer SERVINGS_SIZE_1 = 2;
    public static final Boolean VEGETARIAN_1 = false;
    public static final List<String> INGREDIENTS_1 = List.of("cheese", "apple");
    public static final List<String> INSTRUCTIONS_1 = List.of("1 cup of milk", "1 hour in oven");

    public static final UUID ID_2 = UUID.fromString("09aad778-18cd-44d1-9050-58154525d015");
    public static final String RECIPE_NAME_2 = "Apple cake";
    public static final Integer SERVINGS_SIZE_2 = 1;
    public static final Boolean VEGETARIAN_2 = true;
    public static final List<String> INGREDIENTS_2 = List.of("apple");
    public static final List<String> INSTRUCTIONS_2 = List.of("1 liter of water");

    public static final List<String> EXCLUDE_INGREDIENTS = List.of("egg", "otherexclingredient");

    public static Map<UUID, Recipe> recipeMap() {
        return Map.of(ID_1, recipe(ID_1, RECIPE_NAME_1, VEGETARIAN_1, SERVINGS_SIZE_1, INGREDIENTS_1, INSTRUCTIONS_1),
            ID_2, recipe(ID_2, RECIPE_NAME_2, VEGETARIAN_2, SERVINGS_SIZE_2, INGREDIENTS_2, INSTRUCTIONS_2));
    }
    public static Map<UUID, RecipeResponse> recipeResponseMap() {
        return Map.of(ID_1, recipeResponse(ID_1, RECIPE_NAME_1, VEGETARIAN_1, SERVINGS_SIZE_1, INGREDIENTS_1, INSTRUCTIONS_1),
            ID_2, recipeResponse(ID_2, RECIPE_NAME_2, VEGETARIAN_2, SERVINGS_SIZE_2, INGREDIENTS_2, INSTRUCTIONS_2));
    }

    public static List<RecipeResponse> recipeResponses() {
        return List.of(recipeResponse(ID_1, RECIPE_NAME_1, VEGETARIAN_1, SERVINGS_SIZE_1, INGREDIENTS_1, INSTRUCTIONS_1),
            recipeResponse(ID_2, RECIPE_NAME_2, VEGETARIAN_2, SERVINGS_SIZE_2, INGREDIENTS_2, INSTRUCTIONS_2));
    }

    public static Recipe recipe(UUID id, String recipeName, boolean isVegetarian,
        int servings, List<String> ingredients, List<String> instructions) {
        Recipe recipe = new Recipe();

        recipe.setId(id);
        recipe.setName(recipeName);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setVegetarian(isVegetarian);
        recipe.setServingsSize(servings);

        return recipe;
    }

    public static RecipeResponse recipeResponse(UUID id, String recipeName, boolean isVegetarian,
        int servings, List<String> ingredients, List<String> instructions) {
        RecipeResponse recipe = new RecipeResponse();

        recipe.setId(id);
        recipe.setName(recipeName);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setVegetarian(isVegetarian);
        recipe.setServingsSize(servings);

        return recipe;
    }

    public static RecipeRequest recipeRequest() {
        RecipeRequest rq = new RecipeRequest();

        rq.setIngredients(List.of("egg", "milk", "avocado"));
        rq.setInstructions(List.of("one cup of cheese", "one cup of water"));
        rq.setName("recipe for pie");
        rq.setVegetarian(false);
        rq.setServingsSize(4);

        return rq;
    }
}
