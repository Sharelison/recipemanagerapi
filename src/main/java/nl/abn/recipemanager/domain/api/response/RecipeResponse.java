package nl.abn.recipemanager.domain.api.response;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class RecipeResponse {

    private UUID id;
    private String name;
    private Integer servingsSize;
    private Boolean vegetarian;
    private List<String> ingredients;
    private List<String> instructions;
}
