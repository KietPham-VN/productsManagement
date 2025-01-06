package controllers.product;

import constants.Pages;
import dao.CategoryDAO;
import dao.ProductDAO;
import dto.CreateProductDTO;
import entities.Category;
import entities.Product;
import exceptions.InvalidDataException;
import exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateProductController", urlPatterns =
{
    "/CreateProductController"
})
public class CreateProductController extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("views/product/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String productYear = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category");

        CreateProductDTO productDTO = new CreateProductDTO(name, price, productYear, image, categoryId);
        try
        {
            productDTO.validate();
            CategoryDAO categoryDAO = new CategoryDAO();
            ProductDAO productDAO = new ProductDAO();
            Category category = categoryDAO.getCategoryById(Integer.parseInt(categoryId));
            if (category == null)
            {
                throw new InvalidDataException("Category not found!");
            }

            Product product = new Product(name, Float.parseFloat(price), Integer.parseInt(productYear), image, category);
            boolean isOk = productDAO.createProduct(product);
            if (!isOk)
            {
                throw new InvalidDataException("Cannot save product to database!");
            } else
            {
                request.getRequestDispatcher("MainController?action=list&category=").forward(request, response);
            }
        } //      catch (ValidationException ex) {
        //          request.setAttribute("validationErrors", ex.getErrors());
        //      } 
        catch (ValidationException | InvalidDataException ex)
        {
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }

}
