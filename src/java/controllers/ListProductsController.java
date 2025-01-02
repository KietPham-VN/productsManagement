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

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy danh sách danh mục
            List<Category> categories = categoryDAO.getCategories();
            request.setAttribute("categories", categories);

            // Lấy toàn bộ sản phẩm
            List<Product> products = productDAO.getProducts(null, null);
            request.setAttribute("products", products);
        } catch (Exception e) {
            request.setAttribute("msg", "Error while loading products: " + e.getMessage());
            Logger.getLogger(ListProductsController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Chuyển tiếp đến trang hiển thị danh sách sản phẩm
            request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy tiêu chí tìm kiếm từ form
            String productName = request.getParameter("productName");
            String categoryIdRaw = request.getParameter("category");

            // Chuyển đổi dữ liệu
            Integer categoryId = null;
            if (categoryIdRaw != null && !categoryIdRaw.isEmpty()) {
                categoryId = Integer.valueOf(categoryIdRaw);
            }

            // Thực hiện tìm kiếm sản phẩm
            List<Product> products = productDAO.getProducts(productName, categoryId);

            // Kiểm tra kết quả
            if (products == null || products.isEmpty()) {
                request.setAttribute("msg", "No products found matching your criteria.");
            } else {
                request.setAttribute("products", products);
            }

            // Lấy danh sách danh mục để hiển thị trong dropdown
            List<Category> categories = categoryDAO.getCategories();
            request.setAttribute("categories", categories);

            // Giữ lại tiêu chí tìm kiếm
            request.setAttribute("productName", productName);
            request.setAttribute("category", categoryIdRaw);

        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Invalid category ID. Please try again.");
            Logger.getLogger(ListProductsController.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            request.setAttribute("msg", "An error occurred while searching for products.");
            Logger.getLogger(ListProductsController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Chuyển tiếp đến trang hiển thị sản phẩm
            request.getRequestDispatcher(Pages.PRODUCTS).forward(request, response);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }

}
