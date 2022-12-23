package nl.abn.recipemanager.controller;

import static nl.abn.recipemanager.TestConstants.*;

import java.util.List;
import nl.abn.recipemanager.domain.api.request.RecipeQuery;
import nl.abn.recipemanager.domain.api.response.RecipeResponse;
import nl.abn.recipemanager.service.RecipeService;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController controller;

    @Test
    public void testFetchAll() {
        //given
        List<RecipeResponse> expected = recipeResponses();
        Mockito.when(recipeService.findAll()).thenReturn(expected);

        //when
        List<RecipeResponse> actual = controller.findAllRecipes();

        //then
        BDDAssertions.assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    public void testSearchApi() {
        //given
        List<RecipeResponse> expected = recipeResponses();
        Mockito.when(recipeService.searchRecipes(Mockito.any())).thenReturn(expected);

        //when
        List<RecipeResponse> actual = controller.searchRecipes(new RecipeQuery());

        //then
        BDDAssertions.assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    public void testCreateRecipe() {
        //given when
        ResponseEntity<RecipeResponse> response = controller.createRecipe(recipeRequest());

        //then
        BDDMockito.then(recipeService).should().createRecipe(recipeRequest());
        BDDAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testUpdateRecipe() {
        //given when
        ResponseEntity<RecipeResponse> response = controller.updateRecipe(ID_2, recipeRequest());

        //then
        BDDMockito.then(recipeService).should().updateRecipe(ID_2, recipeRequest());
        BDDAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteRecipe() {
        //given when
        ResponseEntity<Void> response = controller.deleteRecipe(ID_2);

        //then
        BDDMockito.then(recipeService).should().deleteRecipe(ID_2);
        BDDAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
