package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
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
//        System.out.println("THIS IS BEING CALLED RECURSIVELY!!!");
//      This now works!
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
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

    //TODO: Look into changing try catch clauses
    @Override
    public Category getById(int categoryId) {
        // get category by id
//        System.out.println("THIS IS BEING CALLED RECURSIVELY!!!");
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public Category create(Category category)
    {
        // create a new category
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Category category1 = mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category insert(Category category) {
        return null;
    }


    @Override
    public void update(int categoryId, Category category){
        // update category
        //TODO: Double check that this is the correct way to update.
        String sql = "UPDATE categories SET name = ?, description = ?, WHERE category_id =?";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCategoryId());

        statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //TODO: Fix connection
    @Override
    public void delete(int categoryId) {
        // delete category
        String sql = "DELETE FROM Categories WHERE category_id = ?";

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
