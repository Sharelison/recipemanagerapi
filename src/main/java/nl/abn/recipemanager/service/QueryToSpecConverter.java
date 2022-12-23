package nl.abn.recipemanager.service;

import nl.abn.recipemanager.domain.api.request.RecipeQuery;
import nl.abn.recipemanager.domain.db.Recipe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class QueryToSpecConverter {

    private static final String PSQL_ARRAY_DELIMITER = ",";
    private static final String LIKE_POST_PREFIX = "%";
    private static final String PSQL_ARRAY_POSITION_FUNCTION = "array_position";
    private static final String PSQL_ARRAY_TO_STRING_FUNCTION = "array_to_string";



    public Specification<Recipe> toSpecification(RecipeQuery recipeQuery) {
        Specification<Recipe> specification = (recipe, cq, cb) -> recipe.isNotNull();

        if(recipeQuery != null) {
            if(recipeQuery.getName() != null) {
                specification = specification
                    .and((recipe, cq, cb) -> cb.equal(recipe.get(Recipe.NAME_COL), (recipeQuery.getName())));
            }

            if(recipeQuery.getVegetarian() != null) {
                specification = specification
                    .and((recipe, cq, cb) -> cb.equal(recipe.get(Recipe.VEGETARIAN_COL), (recipeQuery.getVegetarian())));
            }

            if(recipeQuery.getMinServingsSize() != null) {
                specification = specification
                    .and((recipe, cq, cb) ->
                        cb.greaterThanOrEqualTo(recipe.get(Recipe.SERVINGS_SIZE_COL), recipeQuery.getMinServingsSize()));
            }


            if(recipeQuery.getInstructionsContains() != null) {
                specification = specification
                    .and((recipe, cq, cb) ->
                        cb.like(cb.function(PSQL_ARRAY_TO_STRING_FUNCTION, String.class,
                                recipe.get(Recipe.INSTRUCTIONS_COL), cb.literal(PSQL_ARRAY_DELIMITER)),
                            LIKE_POST_PREFIX + recipeQuery.getInstructionsContains() + LIKE_POST_PREFIX));
            }

            if(recipeQuery.getIngredientsInclude() != null) {
                for(String ingredient : recipeQuery.getIngredientsInclude()) {
                    specification = specification.and((recipe, cq, cb) ->
                        cb.isNotNull(cb.function(PSQL_ARRAY_POSITION_FUNCTION, Integer.class, recipe.get(Recipe.INGREDIENTS_COL),
                            cb.literal(ingredient))));
                }
            }

            if(recipeQuery.getIngredientsExclude() != null) {
                for(String ingredient : recipeQuery.getIngredientsExclude()) {
                    specification = specification.and((recipe, cq, cb) ->
                        cb.isNull(cb.function(PSQL_ARRAY_POSITION_FUNCTION, Integer.class, recipe.get(Recipe.INGREDIENTS_COL),
                            cb.literal(ingredient))));
                }
            }
        }

        return specification;
    }
}
