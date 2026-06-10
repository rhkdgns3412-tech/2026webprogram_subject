package kr.hnu.ice.product.controller;

import kr.hnu.ice.product.util.ProductImageStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/product-image")
public class ProductImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path imagePath = ProductImageStorage.findProductImage(productId);
        if (imagePath == null || !Files.exists(imagePath)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = Files.probeContentType(imagePath);
        if (contentType == null || contentType.trim().isEmpty()) {
            contentType = "application/octet-stream";
        }

        resp.setContentType(contentType);
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0L);
        resp.setContentLengthLong(Files.size(imagePath));

        try (OutputStream outputStream = resp.getOutputStream()) {
            Files.copy(imagePath, outputStream);
            outputStream.flush();
        }
    }
}