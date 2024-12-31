package constants;

public class Queries
{

    // Account
    public static final String GET_ACCOUNT_BY_ID
            = "SELECT username, role "
            + "FROM Account a "
            + "WHERE id = ?";
    public static final String LOGIN
            = "SELECT id, role "
            + "FROM Account "
            + "WHERE username = ? AND password = ?";

    // category
    public static final String GET_CATEGORY_BY_ID
            = "SELECT id, name "
            + "FROM Category "
            + "WHERE id = ? ";

    public static final String GET_CATEGORIES
            = "SELECT id, name "
            + "FROM Category ";

    // product
    public static final String SEARCH_PRODUCTS
            = "SELECT p.id, p.name, p.price, p.product_year, p.image, p.category_id, c.name as category_name "
            + "FROM Product p join Category c "
            + "ON p.category_id = c.id "
            + "WHERE 1 = 1 ";

    public static final String GET_PRODUCT_BY_ID
            = "SELECT id, name, price, product_year, image, p.category_id, c.name as category_name "
            + "FROM Product p join Category c "
            + "ON p.category_id = c.id "
            + "WHERE id = ? ";

    public static final String CREATE_PRODUCT
            = "INSERT INTO Product(name, price, product_year, image, category_id) "
            + "VALUES(?, ?, ?, ?, ?)";

    public static final String REMOVE_PRODUCT
            = "DELETE Product "
            + "WHERE id = ?";
}
