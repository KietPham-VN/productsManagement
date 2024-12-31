package dao;

import constants.Queries;
import entities.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;
import utils.enums.AccountRole;

public class AccountDAO extends DBUtils
{

    public Account getAccountById(int id)
    {
        Account account = null;
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.GET_ACCOUNT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next())
                {
                    account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUsername(rs.getString("username"));
                    account.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return account;
    }

    public Account login(String username, String password)
    {
        Account account = null;
        try
        {
            PreparedStatement ps = getConnection().prepareStatement(Queries.LOGIN);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next())
                {
                    account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUsername(username);
                    account.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex)
        {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return account;
    }
}
