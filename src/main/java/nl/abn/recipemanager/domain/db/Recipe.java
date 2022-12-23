package nl.abn.recipemanager.domain.db;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Data
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
@Entity(name = "recipe")
public class Recipe {

    public static final String NAME_COL = "name";
    public static final String SERVINGS_SIZE_COL = "servingsSize";
    public static final String VEGETARIAN_COL = "vegetarian";
    public static final String INGREDIENTS_COL = "ingredients";
    public static final String INSTRUCTIONS_COL = "instructions";

    @Column
    @Id
    private UUID id;
    @Column
    private String name;
    @Column(name = "servings_size")
    private Integer servingsSize;
    @Column
    private Boolean vegetarian;
    @Type(type = "list-array")
    @Column
    private List<String> ingredients;
    @Type(type = "list-array")
    @Column
    private List<String> instructions;
}
