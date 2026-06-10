package kr.hnu.ice.product.controller;

import kr.hnu.ice.product.dao.ProductDao;
import kr.hnu.ice.product.model.Product;
import kr.hnu.ice.product.util.ProductImageStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        List<Product> products = dao.getAll();
        decorateProducts(req, products);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/product/product-list.jsp").forward(req, resp);
    }

    public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            req.getRequestDispatcher("/product/product-create.jsp").forward(req, resp);
            return;
        }

        Product product = readProduct(req);
        product.setStatus(null);
        Part imagePart = getImagePart(req, "imageFile");
        dao.addProduct(product);
        saveProductImage(product.getProductId(), imagePart);
        resp.sendRedirect(req.getContextPath() + "/product?action=list");
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        Product product = dao.getProduct(productId);
        decorateProduct(req, product);
        req.setAttribute("product", product);
        req.getRequestDispatcher("/product/product-detail.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            String productId = req.getParameter("productId");
            Product product = dao.getProduct(productId);
            decorateProduct(req, product);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product/product-edit.jsp").forward(req, resp);
            return;
        }

        Product product = readProduct(req);
        product.setProductId(req.getParameter("productId"));
        Part imagePart = getImagePart(req, "imageFile");
        dao.updateProduct(product);
        saveProductImage(product.getProductId(), imagePart);
        resp.sendRedirect(req.getContextPath() + "/product?action=detail&productId=" + product.getProductId());
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        dao.delProduct(productId);
        ProductImageStorage.deleteProductImage(productId);
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
        decorateProducts(req, products);
        req.setAttribute("products", products);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/product/product-search.jsp").forward(req, resp);
    }

    private Product readProduct(HttpServletRequest req) throws IOException, ServletException {
        Product product = new Product();
        product.setProductId(trimToNull(readFormValue(req, "productId")));
        product.setSellerId(trimToNull(readFormValue(req, "sellerId")));
        product.setTitle(trimToNull(readFormValue(req, "title")));
        product.setDescription(trimToNull(readFormValue(req, "description")));
        String priceValue = trimToNull(readFormValue(req, "price"));
        product.setPrice(priceValue == null || priceValue.trim().isEmpty() ? 0 : Integer.parseInt(priceValue));
        product.setCategory(trimToNull(readFormValue(req, "category")));
        String status = trimToNull(readFormValue(req, "status"));
        product.setStatus(status == null ? "판매중" : status);
        return product;
    }

    private void decorateProducts(HttpServletRequest req, List<Product> products) {
        if (products == null) {
            return;
        }

        for (Product product : products) {
            decorateProduct(req, product);
        }
    }

    private void decorateProduct(HttpServletRequest req, Product product) {
        if (product == null || product.getProductId() == null) {
            return;
        }

        if (ProductImageStorage.hasProductImage(product.getProductId())) {
            product.setImageUrl(ProductImageStorage.buildImageUrl(req.getContextPath(), product.getProductId()));
        } else {
            product.setImageUrl(null);
        }
    }

    private void saveProductImage(String productId, Part imagePart) {
        if (productId == null || productId.trim().isEmpty() || imagePart == null || imagePart.getSize() == 0) {
            return;
        }

        String contentType = imagePart.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith("image/")) {
            throw new IllegalStateException("이미지 파일만 업로드할 수 있습니다.");
        }

        ProductImageStorage.saveProductImage(productId, imagePart);
    }

    private Part getImagePart(HttpServletRequest req, String partName) throws IOException, ServletException {
        if (!isMultipartRequest(req)) {
            return null;
        }

        Part part = req.getPart(partName);
        return part == null || part.getSize() == 0 ? null : part;
    }

    private String readFormValue(HttpServletRequest req, String name) throws IOException, ServletException {
        String value = req.getParameter(name);
        if (value != null) {
            return value;
        }

        if (!isMultipartRequest(req)) {
            return null;
        }

        Part part = req.getPart(name);
        if (part == null || part.getSize() == 0) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (builder.length() > 0) {
                    builder.append('\n');
                }
                builder.append(line);
            }
            return builder.toString();
        }
    }

    private boolean isMultipartRequest(HttpServletRequest req) {
        String contentType = req.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/form-data");
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}