package nl.abn.recipemanager.service;

import static nl.abn.recipemanager.TestConstants.*;

import nl.abn.recipemanager.controller.exception.RecipeNotFoundException;
import nl.abn.recipemanager.repository.RecipeRepository;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    QueryToSpecConverter converter;
    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService service;

    @Test
    public void testUpdateRecipeForNotExistShouldThrow() {
        BDDAssertions.assertThatThrownBy(
            () -> service.updateRecipe(ID_1, recipeRequest())
        ).isInstanceOf(RecipeNotFoundException.class)
            .hasMessage("Recipe does not exist");
    }
}
