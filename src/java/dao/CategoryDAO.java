package dao;

import constants.Queries;
import entities.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class CategoryDAO extends DBUtils
{

    public Category getCategoryById(int categoryId)
    {
        Category category = null;
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.GET_CATEGORY_BY_ID);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs != null)
            {
                if (rs.next())
                {
                    category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                }
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting category by id. Details: ");
            ex.printStackTrace();
        }
        return category;
    }

    public List<Category> getCategories()
    {
        List<Category> categories = new ArrayList<>();
        try
        {
            ResultSet rs = getConnection()
                    .prepareStatement(Queries.GET_CATEGORIES)
                    .executeQuery();
            if (rs != null)
            {
                categories = new ArrayList<>();
                while (rs.next())
                {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    categories.add(category);
                }
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting list of categories. Details: ");
            ex.printStackTrace();
        }
        return categories;
    }
}
