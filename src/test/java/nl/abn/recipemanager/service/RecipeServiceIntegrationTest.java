package nl.abn.recipemanager.service;

import static nl.abn.recipemanager.TestConstants.*;

import java.util.List;
import nl.abn.recipemanager.domain.api.request.RecipeQuery;
import nl.abn.recipemanager.domain.api.request.RecipeRequest;
import nl.abn.recipemanager.domain.api.response.RecipeResponse;
import nl.abn.recipemanager.domain.db.Recipe;
import nl.abn.recipemanager.repository.RecipeRepository;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/schema.sql")
@SpringBootTest
public class RecipeServiceIntegrationTest extends DatabaseIT{

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @DynamicPropertySource
    protected static void registerSQLProperties(DynamicPropertyRegistry registry) {
        DatabaseIT.registerSQLProperties(registry);
    }

    @Test
    public void testFetchAll() {
        //given when
        List<RecipeResponse> response = recipeService.findAll();

        //then
        BDDAssertions.assertThat(response).hasSize(2);
        BDDAssertions.assertThat(response).containsExactlyInAnyOrderElementsOf(recipeResponseMap().values());
    }

    @Test
    public void testSearchForOneQuery() {
        RecipeQuery query = new RecipeQuery();
        query.setName(RECIPE_NAME_2);
        List<RecipeResponse> response = recipeService.searchRecipes(query);

        BDDAssertions.assertThat(response).hasSize(1);
        BDDAssertions.assertThat(response).containsOnly(recipeResponseMap().get(ID_2));
    }

    @Test
    public void testSearchForMultiple() {
        RecipeQuery query = new RecipeQuery();
        query.setName(RECIPE_NAME_1);
        query.setMinServingsSize(SERVINGS_SIZE_1);
        query.setIngredientsInclude(List.of(INGREDIENTS_1.get(0)));
        query.setIngredientsExclude(EXCLUDE_INGREDIENTS);

        List<RecipeResponse> response = recipeService.searchRecipes(query);

        BDDAssertions.assertThat(response).hasSize(1);
        BDDAssertions.assertThat(response).containsOnly(recipeResponseMap().get(ID_1));
    }

    @Test
    public void testSearchForAllQueryParams() {
        RecipeQuery query = new RecipeQuery();
        query.setName(RECIPE_NAME_1);
        query.setMinServingsSize(SERVINGS_SIZE_1);
        query.setIngredientsInclude(List.of(INGREDIENTS_1.get(0)));
        query.setIngredientsExclude(EXCLUDE_INGREDIENTS);
        query.setVegetarian(VEGETARIAN_1);
        query.setInstructionsContains(INSTRUCTIONS_1.get(0));

        List<RecipeResponse> response = recipeService.searchRecipes(query);

        BDDAssertions.assertThat(response).hasSize(1);
        BDDAssertions.assertThat(response).containsOnly(recipeResponseMap().get(ID_1));
    }

    @Test
    public void testSearchForAllRowsMatch() {
        //given when
        RecipeQuery query = new RecipeQuery();
        query.setMinServingsSize(1);
        query.setInstructionsContains("of");

        //when
        List<RecipeResponse> response = recipeService.searchRecipes(query);

        //then
        BDDAssertions.assertThat(response).hasSize(2);
        BDDAssertions.assertThat(response).containsExactlyInAnyOrderElementsOf(recipeResponseMap().values());
    }

    @Test
    public void testSearchForNullQuery() {
        //given when
        List<RecipeResponse> response = recipeService.searchRecipes(null);

        //then
        BDDAssertions.assertThat(response).hasSize(2);
        BDDAssertions.assertThat(response).containsExactlyInAnyOrderElementsOf(recipeResponseMap().values());
    }

    @Test
    public void testCreateRecipe() {
        //given
        RecipeRequest rq = recipeRequest();


        //when
        RecipeResponse recipeResponse = recipeService.createRecipe(rq);
        Recipe recipe = recipeRepository.findById(recipeResponse.getId()).orElseThrow();

        //then
        BDDAssertions.assertThat(recipe.getIngredients()).isEqualTo(rq.getIngredients());
        BDDAssertions.assertThat(recipe.getInstructions()).isEqualTo(rq.getInstructions());
        BDDAssertions.assertThat(recipe.getVegetarian()).isEqualTo(rq.getVegetarian());
        BDDAssertions.assertThat(recipe.getServingsSize()).isEqualTo(rq.getServingsSize());
        BDDAssertions.assertThat(recipe.getName()).isEqualTo(rq.getName());
    }

    @Test
    public void testDeleteRecipe() {
        //given when
        recipeService.deleteRecipe(ID_2);

        //then
        BDDAssertions.assertThat(recipeRepository.findAll()).containsOnly(recipeMap().get(ID_1));
    }

    @Test
    public void testUpdateRecipe() {
        //given
        RecipeRequest rq = recipeRequest();

        //when
        RecipeResponse response = recipeService.updateRecipe(ID_1, recipeRequest());
        //then
        BDDAssertions.assertThat(response.getIngredients()).isEqualTo(rq.getIngredients());
        BDDAssertions.assertThat(response.getInstructions()).isEqualTo(rq.getInstructions());
        BDDAssertions.assertThat(response.getVegetarian()).isEqualTo(rq.getVegetarian());
        BDDAssertions.assertThat(response.getServingsSize()).isEqualTo(rq.getServingsSize());
        BDDAssertions.assertThat(response.getName()).isEqualTo(rq.getName());

        BDDAssertions.assertThat(recipeService.findAll()).containsExactlyInAnyOrderElementsOf(List.of(response, recipeResponseMap().get(ID_2)));
    }
}
