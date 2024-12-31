package controllers;

import constants.Pages;
import dao.ProductDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteProductController", urlPatterns =
{
    "/DeleteProductController"
})
public class DeleteProductController extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String productIdStr = request.getParameter("productId");
        try
        {
            int productId = Integer.parseInt(productIdStr);

            ProductDAO productDAO = new ProductDAO();
            boolean isDeleted = productDAO.removeProduct(productId);

            if (isDeleted)
            {
                request.getRequestDispatcher("MainController?action=list&category=").forward(request, response);
            } else
            {
                request.setAttribute("msg", "Failed to delete product.");
                request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
            }
        } catch (NumberFormatException e)
        {
            request.setAttribute("msg", "Invalid product ID.");
            request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
        }

    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
}
