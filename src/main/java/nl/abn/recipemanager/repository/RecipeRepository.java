package nl.abn.recipemanager.repository;

import java.util.UUID;
import nl.abn.recipemanager.domain.db.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, UUID>,
    JpaSpecificationExecutor<Recipe> {
}
