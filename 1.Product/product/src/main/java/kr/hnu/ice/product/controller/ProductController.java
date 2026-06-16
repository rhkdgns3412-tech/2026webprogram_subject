package kr.hnu.ice.product.controller;

import kr.hnu.ice.product.dao.ProductDao;
import kr.hnu.ice.product.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@WebServlet("/product")
@MultipartConfig
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
        String category = req.getParameter("category");
        String sort = req.getParameter("sort");

        List<Product> products = dao.getProducts(category, sort);
        List<String> categories = dao.getCategories();

        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
        req.setAttribute("category", category);
        req.setAttribute("sort", sort);
        req.getRequestDispatcher("/product/product-list.jsp").forward(req, resp);
    }

    public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            req.getRequestDispatcher("/product/product-create.jsp").forward(req, resp);
            return;
        }

        Product product = readProduct(req);
        product.setStatus(null);
        String storedImagePath = storeUploadedImage(req, product.getProductId());
        product.setImagePath(storedImagePath);
        try {
            dao.addProduct(product);
        } catch (RuntimeException e) {
            deleteStoredImage(req, storedImagePath);
            throw e;
        }
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        Product product = dao.getProduct(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/product/product-detail.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            String productId = req.getParameter("productId");
            Product product = dao.getProduct(productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product/product-edit.jsp").forward(req, resp);
            return;
        }

        Product existingProduct = dao.getProduct(req.getParameter("productId"));
        Product product = readProduct(req);
        product.setProductId(req.getParameter("productId"));
        String storedImagePath = storeUploadedImage(req, product.getProductId());
        String existingImagePath = existingProduct == null ? null : existingProduct.getImagePath();
        String resolvedImagePath = storedImagePath != null ? storedImagePath : existingImagePath;
        product.setImagePath(resolvedImagePath);
        try {
            if ("거래완료".equals(product.getStatus())) {
                dao.delProduct(product.getProductId());
                deleteStoredImage(req, resolvedImagePath);
                if (storedImagePath != null && existingImagePath != null && !existingImagePath.equals(storedImagePath)) {
                    deleteStoredImage(req, existingImagePath);
                }
            } else {
                dao.updateProduct(product);
                if (storedImagePath != null && existingImagePath != null && !existingImagePath.equals(storedImagePath)) {
                    deleteStoredImage(req, existingImagePath);
                }
            }
        } catch (RuntimeException e) {
            deleteStoredImage(req, storedImagePath);
            throw e;
        }
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        Product existingProduct = dao.getProduct(productId);
        dao.delProduct(productId);
        if (existingProduct != null) {
            deleteStoredImage(req, existingProduct.getImagePath());
        }
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Product> products;
        if (keyword == null || keyword.trim().isEmpty()) {
            products = dao.getAll();
        } else {
            products = dao.searchProduct(keyword);
        }
        req.setAttribute("products", products);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/product/product-search.jsp").forward(req, resp);
    }

    private Product readProduct(HttpServletRequest req) throws IOException, ServletException {
        Product product = new Product();
        product.setProductId(trimToNull(req.getParameter("productId")));
        product.setSellerId(trimToNull(req.getParameter("sellerId")));
        product.setTitle(trimToNull(req.getParameter("title")));
        product.setDescription(trimToNull(req.getParameter("description")));
        String priceValue = trimToNull(req.getParameter("price"));
        product.setPrice(priceValue == null || priceValue.trim().isEmpty() ? 0 : Integer.parseInt(priceValue));
        product.setCategory(trimToNull(req.getParameter("category")));
        String status = trimToNull(req.getParameter("status"));
        product.setStatus(status == null ? "판매중" : status);
        return product;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String storeUploadedImage(HttpServletRequest req, String productId) throws IOException, ServletException {
        Part imagePart = req.getPart("productImage");
        if (imagePart == null || imagePart.getSize() <= 0) {
            return null;
        }

        String submittedFileName = imagePart.getSubmittedFileName();
        if (submittedFileName == null || submittedFileName.trim().isEmpty()) {
            return null;
        }

        Path imageDirectory = resolveImageDirectory(req);
        Files.createDirectories(imageDirectory);

        String storedName = buildStoredFileName(productId, submittedFileName);
        Path targetPath = imageDirectory.resolve(storedName);
        try (InputStream inputStream = imagePart.getInputStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "uploads/product/" + storedName;
    }

    private String buildStoredFileName(String productId, String submittedFileName) {
        String safeProductId = productId == null ? "product" : productId.replaceAll("[^a-zA-Z0-9._-]", "_");
        String extension = "";
        int dotIndex = submittedFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = submittedFileName.substring(dotIndex);
        }

        return safeProductId + "_" + UUID.randomUUID().toString().replace("-", "") + extension;
    }

    private Path resolveImageDirectory(HttpServletRequest req) {
        String realPath = req.getServletContext().getRealPath("/uploads/product");
        if (realPath != null) {
            return Paths.get(realPath);
        }

        return Paths.get(System.getProperty("user.dir"), "uploads", "product");
    }

    private void deleteStoredImage(HttpServletRequest req, String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return;
        }

        try {
            Path targetPath = resolveImageDirectory(req).resolve(new File(imagePath).getName());
            Files.deleteIfExists(targetPath);
        } catch (IOException ignored) {
        }
    }
}