package controllers;

import com.sun.media.sound.InvalidDataException;
import constants.Pages;
import dao.CategoryDAO;
import dao.ProductDAO;
import dto.SearchProductDTO;
import entities.Category;
import entities.Product;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListProductsController", urlPatterns =
{
    "/ListProductsController"
})
public class ListProductsController extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // get category list for drop down
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getCategories();
            request.setAttribute("categories", categories);

            // get search criterias
            String categoryIdRaw = request.getParameter("category");
            String productName = request.getParameter("productName");

            // validate search fields only when search criterias a string
            Integer categoryId = null;
            if (categoryIdRaw != null && !categoryIdRaw.isEmpty())
            {
                SearchProductDTO searchDTO = new SearchProductDTO(categoryIdRaw, productName);
                searchDTO.validate();
                categoryId = Integer.parseInt(categoryIdRaw);
            }

            // get search data
            ProductDAO productDAO = new ProductDAO();
            List<Product> list = productDAO.getProducts(productName, categoryId);
            if (list != null && !list.isEmpty())
            {
                request.setAttribute("products", list);
            } else
            {
                throw new InvalidDataException("No Products Found!");
            }

            // hold search criteria on search bar for next request
            request.setAttribute("productName", productName);
            request.setAttribute("category", categoryIdRaw);
        } catch (InvalidDataException ex)
        {
            request.setAttribute("msg", ex.getMessage());
        } catch (exceptions.ValidationException ex)
        {
            Logger.getLogger(ListProductsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }

}
