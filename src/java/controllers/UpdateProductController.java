package controllers;

import constants.Pages;
import dao.CategoryDAO;
import dao.ProductDAO;
import entities.Category;
import entities.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateProductController", urlPatterns =
{
    "/UpdateProductController"
})
public class UpdateProductController extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");

        try {
            int productId = Integer.parseInt(productIdStr);

            // Lấy thông tin sản phẩm
            Product product = productDAO.getProductById(productId);

            if (product != null) {
                // Đặt sản phẩm và danh sách category vào request
                request.setAttribute("product", product);
                List<Category> categories = categoryDAO.getCategories();
                request.setAttribute("categories", categories);

                // Chuyển tiếp đến trang cập nhật
                request.getRequestDispatcher("views/product/update.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "Product not found.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Invalid product ID. Please check and try again.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String productYearStr = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryIdStr = request.getParameter("category");

        try {
            int productId = Integer.parseInt(productIdStr);
            float price = Float.parseFloat(priceStr);
            int productYear = Integer.parseInt(productYearStr);
            int categoryId = Integer.parseInt(categoryIdStr);

            // Lấy danh mục sản phẩm
            Category category = categoryDAO.getCategoryById(categoryId);

            if (category != null) {
                // Cập nhật sản phẩm
                Product product = new Product(productId, name, price, productYear, image, category);
                boolean isUpdated = productDAO.updateProduct(product);

                if (isUpdated) {
                    request.getRequestDispatcher("MainController?action=list&category=").forward(request, response);
                } else {
                    request.setAttribute("msg", "Failed to update the product.");
                    request.getRequestDispatcher(Pages.UPDATE).forward(request, response);
                }
            } else {
                request.setAttribute("msg", "Category not found. Please select a valid category.");
                request.getRequestDispatcher(Pages.UPDATE).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Invalid input data. Please check and try again.");
            request.getRequestDispatcher(Pages.UPDATE).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating products";
    }
}
