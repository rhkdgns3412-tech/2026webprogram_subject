package kr.hnu.ice.product.dao;

import kr.hnu.ice.product.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final String[] DB_NAMES = {"Product_db", "pruduct_db"};
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String URL_PATTERN = "jdbc:mysql://localhost:3306/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&useSSL=false";

    private Connection conn;
    private PreparedStatement pstat;

    public ProductDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("MySQL JDBC 드라이버를 찾을 수 없습니다.", e);
        }
    }

    public void connect() {
        if (conn != null) {
            return;
        }

        SQLException lastException = null;
        for (String dbName : DB_NAMES) {
            try {
                conn = DriverManager.getConnection(String.format(URL_PATTERN, dbName), DB_USER, DB_PASSWORD);
                return;
            } catch (SQLException e) {
                lastException = e;
            }
        }

        throw new IllegalStateException("Product_db 또는 pruduct_db에 연결할 수 없습니다.", lastException);
    }

    public void close() {
        if (pstat != null) {
            try {
                pstat.close();
            } catch (SQLException ignored) {
            } finally {
                pstat = null;
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            } finally {
                conn = null;
            }
        }
    }

    public void addProduct(Product product) {
        final String sql = "INSERT INTO product (product_id, seller_id, title, description, price, category) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, product.getProductId());
            pstat.setString(2, product.getSellerId());
            pstat.setString(3, product.getTitle());
            pstat.setString(4, product.getDescription());
            pstat.setInt(5, product.getPrice());
            pstat.setString(6, product.getCategory());
            pstat.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("상품 등록에 실패했습니다.", e);
        } finally {
            close();
        }
    }

    public List<Product> getAll() {
        final String sql = "SELECT product_id, seller_id, title, description, price, category, status, created_at FROM product ORDER BY created_at DESC, product_id DESC";
        List<Product> productList = new ArrayList<>();

        try {
            connect();
            pstat = conn.prepareStatement(sql);
            try (ResultSet rs = pstat.executeQuery()) {
                while (rs.next()) {
                    productList.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("상품 목록 조회에 실패했습니다.", e);
        } finally {
            close();
        }

        return productList;
    }

    public Product getProduct(String productId) {
        final String sql = "SELECT product_id, seller_id, title, description, price, category, status, created_at FROM product WHERE product_id = ?";

        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, productId);
            try (ResultSet rs = pstat.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("상품 상세 조회에 실패했습니다.", e);
        } finally {
            close();
        }

        return null;
    }

    public void updateProduct(Product product) {
        final String sql = "UPDATE product SET seller_id = ?, title = ?, description = ?, price = ?, category = ?, status = ? WHERE product_id = ?";
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, product.getSellerId());
            pstat.setString(2, product.getTitle());
            pstat.setString(3, product.getDescription());
            pstat.setInt(4, product.getPrice());
            pstat.setString(5, product.getCategory());
            pstat.setString(6, product.getStatus());
            pstat.setString(7, product.getProductId());
            pstat.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("상품 수정에 실패했습니다.", e);
        } finally {
            close();
        }
    }

    public void delProduct(String productId) {
        final String sql = "DELETE FROM product WHERE product_id = ?";
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, productId);
            pstat.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("상품 삭제에 실패했습니다.", e);
        } finally {
            close();
        }
    }

    public List<Product> searchProduct(String keyword) {
        final String sql = "SELECT product_id, seller_id, title, description, price, category, status, created_at FROM product WHERE title LIKE ? OR category LIKE ? OR description LIKE ? ORDER BY created_at DESC, product_id DESC";
        List<Product> productList = new ArrayList<>();

        try {
            connect();
            pstat = conn.prepareStatement(sql);
            String likeKeyword = "%" + keyword + "%";
            pstat.setString(1, likeKeyword);
            pstat.setString(2, likeKeyword);
            pstat.setString(3, likeKeyword);
            try (ResultSet rs = pstat.executeQuery()) {
                while (rs.next()) {
                    productList.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("상품 검색에 실패했습니다.", e);
        } finally {
            close();
        }

        return productList;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("product_id"),
                rs.getString("seller_id"),
                rs.getString("title"),
                rs.getInt("price"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getString("created_at")
        );
    }
}