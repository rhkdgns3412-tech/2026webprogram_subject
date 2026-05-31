package kr.hnu.ice.product.controller;

import kr.hnu.ice.product.dao.ProductDao;
import kr.hnu.ice.product.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private ProductDao dao;

    @Override
    public void init() {
        dao = new ProductDao();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action == null || action.trim().isEmpty() || "list".equals(action)) {
            list(req, resp);
            return;
        }

        switch (action) {
            case "detail":
                detail(req, resp);
                break;
            case "create":
                create(req, resp);
                break;
            case "edit":
                edit(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "search":
                search(req, resp);
                break;
            default:
                list(req, resp);
                break;
        }
    }

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", dao.getAll());
        req.getRequestDispatcher("/product/product-list.jsp").forward(req, resp);
    }

    public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            req.getRequestDispatcher("/product/product-create.jsp").forward(req, resp);
            return;
        }

        Product product = readProduct(req);
        dao.addProduct(product);
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        req.setAttribute("product", dao.getProduct(productId));
        req.getRequestDispatcher("/product/product-detail.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            String productId = req.getParameter("productId");
            req.setAttribute("product", dao.getProduct(productId));
            req.getRequestDispatcher("/product/product-edit.jsp").forward(req, resp);
            return;
        }

        Product product = readProduct(req);
        product.setProductId(req.getParameter("productId"));
        dao.updateProduct(product);
        resp.sendRedirect(req.getContextPath() + "/product?action=detail&productId=" + product.getProductId());
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        dao.delProduct(productId);
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            req.setAttribute("products", dao.getAll());
        } else {
            req.setAttribute("products", dao.searchProduct(keyword));
        }
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/product/product-search.jsp").forward(req, resp);
    }

    private Product readProduct(HttpServletRequest req) {
        Product product = new Product();
        product.setProductId(req.getParameter("productId"));
        product.setSellerId(req.getParameter("sellerId"));
        product.setTitle(req.getParameter("title"));
        product.setDescription(req.getParameter("description"));
        String priceValue = req.getParameter("price");
        product.setPrice(priceValue == null || priceValue.trim().isEmpty() ? 0 : Integer.parseInt(priceValue));
        product.setCategory(req.getParameter("category"));
        product.setStatus(req.getParameter("status"));
        return product;
    }
}