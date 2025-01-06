package controllers;

import constants.Pages;
import dao.AccountDAO;
import entities.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns =
{
    "/LoginController"
})
public class LoginController extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null)
        {
            response.sendRedirect("MainController?action=list");
        } else
        {
            request.getRequestDispatcher(Pages.LOGIN).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.login(username, password);
        if (account != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            session.setMaxInactiveInterval(1800);
            request.getRequestDispatcher("MainController?action=list").forward(request, response);
        } else
        {
            request.setAttribute("error", "Wrong username or password");
            request.getRequestDispatcher(Pages.LOGIN).forward(request, response);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
}
