package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    private final DataSource dataSource;

    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
        this.dataSource = dataSource;
    }


    //TODO: IntelliJ states that this method is recursive and runs indefinitely. Make sure the getAllCategories method only gets what you want not more.
    @Override
    public List<Category> getAllCategories() {
        // get all categories
        //TODO: CONFIRMED -> CURRENT ERROR DUE TO RECURSIVE CALLING OF METHOD
//        System.out.println("THIS IS BEING CALLED RECURSIVELY!!!");
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Category category = mapRow(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
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
