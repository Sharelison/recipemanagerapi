package nl.abn.recipemanager.domain.api.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class RecipeRequest {

    @NotBlank
    @NotNull
    private String name;
    @Positive
    private Integer servingsSize;
    @NotNull
    private Boolean vegetarian;
    @Size(min=1)
    @NotNull
    private List<String> ingredients;
    @Size(min=1)
    @NotNull
    private List<String> instructions;
}
