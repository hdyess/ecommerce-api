package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        // get all categories
		List<Category> categories = new ArrayList<>();
		String sql = "select * from categories;";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rows = statement.executeQuery();
			while(rows.next()) {
				Category category = mapRow(rows);
				categories.add(category);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
		String sql = """
				select *
				from categories
				where category_id = ?""";

		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, categoryId);
			ResultSet row = statement.executeQuery();
			if (row.next()) {
				return mapRow(row);
			}
		} catch(SQLException ex) {
			throw new RuntimeException(ex);
		}
		return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
		String sql = """
				insert into categories(category_id, name, description) values (?, ?, ?);""";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, category.getCategoryId());
			statement.setString(2, category.getName());
			statement.setString(3, category.getDescription());
			int rowsAffected = statement.executeUpdate();

			if(rowsAffected > 0) {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if(generatedKeys.next()) {
					int categoryId = generatedKeys.getInt(1);
					return getById(categoryId);
				}
			}
		} catch(SQLException ex) {
			throw new RuntimeException(ex);
		}
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
		String sql = """
				update categories
				set category_id = ?,
				set name = ?,
				set description = ?
				where category_id = ?""";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, category.getCategoryId());
			statement.setString(2, category.getName());
			statement.setString(3, category.getDescription());
			statement.setInt(4, categoryId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
		String sql = "delete from categories where category_id = ?";

		try(Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, categoryId);
			statement.executeUpdate();
		} catch(SQLException ex) {
			throw new RuntimeException(ex);
		}
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
