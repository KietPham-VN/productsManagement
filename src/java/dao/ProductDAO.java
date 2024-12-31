package dao;

import constants.Queries;
import entities.Category;
import entities.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class ProductDAO extends DBUtils
{

    private String constructSearchQuery(String productName, Integer categoryId)
    {
        String searchProductQuery = Queries.SEARCH_PRODUCTS;
        if (categoryId != null)
        {
            searchProductQuery += " and p.category_id = ? ";
        }
        if (productName != null && !productName.trim().isEmpty())
        {
            searchProductQuery += " and p.name like ? ";
        }

        return searchProductQuery;
    }

    public List<Product> getProducts(String productName, Integer categoryId)
    {
        List<Product> list = null;
        String sql = constructSearchQuery(productName, categoryId);
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            int paramIndex = 1;
            if (categoryId != null)
            {
                ps.setInt(paramIndex++, categoryId);
            }
            if (productName != null && !productName.trim().isEmpty())
            {
                ps.setString(paramIndex++, "%" + productName + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs != null)
            {
                list = new ArrayList<>();
                while (rs.next())
                {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getFloat("price"));
                    product.setProductYear(rs.getInt("product_year"));
                    product.setImage(rs.getString("image"));

                    Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                    product.setCategory(category);
                    list.add(product);
                }
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting list of products. Details: ");
        }
        return list;
    }

    public Product getProductById(int id)
    {
        Product product = null;
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.GET_PRODUCT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null)
            {
                if (rs.next())
                {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getFloat("price"));
                    product.setProductYear(rs.getInt("product_year"));
                    product.setImage(rs.getString("image"));

                    Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                    product.setCategory(category);
                }
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting product by id. Details: ");
        }
        return product;
    }

    public boolean createProduct(Product newProduct)
    {
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.CREATE_PRODUCT);
            ps.setString(1, newProduct.getName());
            ps.setFloat(2, newProduct.getPrice());
            ps.setInt(3, newProduct.getProductYear());
            ps.setString(4, newProduct.getImage());
            ps.setInt(5, newProduct.getCategory().getId());
            int rows = ps.executeUpdate();
            if (rows > 0)
            {
                return true;
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found!");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in inserting new product. Details: ");
        }
        return false;
    }

    public boolean removeProduct(int id)
    {
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.REMOVE_PRODUCT);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
            {
                return true;
            }
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found!");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in inserting new product. Details: ");
        }
        return false;
    }
}
