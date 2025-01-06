package controllers;

import constants.Pages;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns =
{
    "/MainController"
})
public class MainController extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = Pages.LOGIN;
        switch (action)
        {
            case "login":
            {
                url = "LoginController";
                break;
            }
            case "list":
            {
                url = "ListProductsController";
                break;
            }
            case "create":
            {
                url = "CreateProductController";
                break;
            }
            case "update":
            {
                url = "UpdateProductController";
                break;
            }
            case "delete":
            {
                url = "DeleteProductController";
                break;
            }
            case "logout":
            {
                url = "LogoutController";
                break;
            }
            default:
            {
                break;
            }
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
}
