package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }


    //TODO: IntelliJ states that this method is recursive and runs indefinitely. Make sure the getAllCategories method only gets what you want not more.
    @Override
    public List<Category> getAllCategories()
    {
        // get all categories
        return getAllCategories();
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        return getById(categoryId);
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return create(category);
    }

    @Override
    public Category insert(Category category) {
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        //TODO: Double check that this is the correct way to update.
        update(categoryId, category);
    }

    //TODO: IntelliJ states that this method is recursive and runs indefinitely. Make sure the delete method only deletes what you want not more.
    @Override
    public void delete(int categoryId)
    {
        // delete category
        delete(categoryId);
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
