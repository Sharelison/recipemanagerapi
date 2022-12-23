package nl.abn.recipemanager.domain.api.request;

import java.util.List;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecipeQuery {

    private String name;
    private List<String> ingredientsInclude;
    private List<String> ingredientsExclude;
    private String instructionsContains;
    @Positive
    private Integer minServingsSize;
    private Boolean vegetarian;
}
