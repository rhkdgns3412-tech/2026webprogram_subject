package kr.hnu.ice.product.dao;

import kr.hnu.ice.product.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDao {
    private static final String DEFAULT_DB_NAME = "Product_db";
    private static final String FALLBACK_DB_NAME = "pruduct_db";
    private static final String DEFAULT_DB_HOST = "localhost";
    private static final String DEFAULT_DB_PORT = "3306";
    private static final String DEFAULT_DB_USER = "root";
    private static final String DEFAULT_DB_PASSWORD = "";
    private static final String CONFIG_FILE = "db.properties";
    private static final String URL_PATTERN = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&useSSL=false";

    private Connection conn;
    private PreparedStatement pstat;
    private final Properties dbProperties;
    private boolean schemaChecked;

    public ProductDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("MySQL JDBC 드라이버를 찾을 수 없습니다.", e);
        }

        dbProperties = loadDbProperties();
    }

    public void connect() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    ensureSchema();
                    return;
                }
            } catch (SQLException ignored) {
            }
        }

        String host = resolveSetting("product.db.host", "PRODUCT_DB_HOST", dbProperties.getProperty("db.host", DEFAULT_DB_HOST));
        String port = resolveSetting("product.db.port", "PRODUCT_DB_PORT", dbProperties.getProperty("db.port", DEFAULT_DB_PORT));
        String user = resolveSetting("product.db.user", "PRODUCT_DB_USER", dbProperties.getProperty("db.user", DEFAULT_DB_USER));
        String password = resolveSetting("product.db.password", "PRODUCT_DB_PASSWORD", dbProperties.getProperty("db.password", DEFAULT_DB_PASSWORD));
        String configuredDbName = resolveSetting("product.db.name", "PRODUCT_DB_NAME", dbProperties.getProperty("db.name", DEFAULT_DB_NAME));

        SQLException lastException = null;
        for (String dbName : new String[] {configuredDbName, DEFAULT_DB_NAME, FALLBACK_DB_NAME}) {
            try {
                conn = DriverManager.getConnection(String.format(URL_PATTERN, host, port, dbName), user, password);
                ensureSchema();
                return;
            } catch (SQLException e) {
                lastException = e;
            }
        }

        throw new IllegalStateException("Product_db에 연결할 수 없습니다. MySQL 서버가 실행 중인지, db.properties의 설정이 맞는지 확인하세요.", lastException);
    }

    private Properties loadDbProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("DB 설정 파일을 읽을 수 없습니다.", e);
        }

        return properties;
    }

    private String resolveSetting(String systemPropertyName, String envName, String defaultValue) {
        String value = System.getProperty(systemPropertyName);
        if (value != null && !value.trim().isEmpty()) {
            return value.trim();
        }

        value = System.getenv(envName);
        if (value != null && !value.trim().isEmpty()) {
            return value.trim();
        }

        return defaultValue;
    }

    private void ensureSchema() throws SQLException {
        if (schemaChecked || conn == null) {
            return;
        }

        final String sql = "SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'product' AND COLUMN_NAME = 'image_path'";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (java.sql.Statement alterStatement = conn.createStatement()) {
                    alterStatement.executeUpdate("ALTER TABLE product ADD COLUMN image_path VARCHAR(255) NULL AFTER category");
                }
            }
        }

        schemaChecked = true;
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
        final String sql = "INSERT INTO product (product_id, seller_id, title, description, price, category, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, product.getProductId());
            pstat.setString(2, product.getSellerId());
            pstat.setString(3, product.getTitle());
            pstat.setString(4, product.getDescription());
            pstat.setInt(5, product.getPrice());
            pstat.setString(6, product.getCategory());
            pstat.setString(7, product.getImagePath());
            pstat.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("상품 등록에 실패했습니다.", e);
        } finally {
            close();
        }
    }

    public List<Product> getAll() {
        final String sql = "SELECT product_id, seller_id, title, description, price, category, image_path, status, created_at FROM product ORDER BY created_at DESC, product_id DESC";
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

    public List<Product> getProducts(String category, String sortOrder) {
        StringBuilder sql = new StringBuilder("SELECT product_id, seller_id, title, description, price, category, image_path, status, created_at FROM product");
        boolean hasCategory = category != null && !category.trim().isEmpty() && !"전체".equals(category);

        if (hasCategory) {
            sql.append(" WHERE category = ?");
        }

        if ("price_asc".equals(sortOrder)) {
            sql.append(" ORDER BY price ASC");
        } else if ("price_desc".equals(sortOrder)) {
            sql.append(" ORDER BY price DESC");
        } else {
            sql.append(" ORDER BY created_at DESC, product_id DESC");
        }

        List<Product> productList = new ArrayList<>();
        try {
            connect();
            pstat = conn.prepareStatement(sql.toString());
            if (hasCategory) {
                pstat.setString(1, category);
            }
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

    public List<String> getCategories() {
        final String sql = "SELECT DISTINCT category FROM product WHERE category IS NOT NULL AND category <> '' ORDER BY category ASC";
        List<String> categories = new ArrayList<>();
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            try (ResultSet rs = pstat.executeQuery()) {
                while (rs.next()) {
                    categories.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("카테고리 목록 조회에 실패했습니다.", e);
        } finally {
            close();
        }
        return categories;
    }

    public Product getProduct(String productId) {
        final String sql = "SELECT product_id, seller_id, title, description, price, category, image_path, status, created_at FROM product WHERE product_id = ?";

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
        final String sql = "UPDATE product SET seller_id = ?, title = ?, description = ?, price = ?, category = ?, image_path = ?, status = ? WHERE product_id = ?";
        try {
            connect();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, product.getSellerId());
            pstat.setString(2, product.getTitle());
            pstat.setString(3, product.getDescription());
            pstat.setInt(4, product.getPrice());
            pstat.setString(5, product.getCategory());
            pstat.setString(6, product.getImagePath());
            pstat.setString(7, product.getStatus());
            pstat.setString(8, product.getProductId());
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
        final String sql = "SELECT product_id, seller_id, title, description, price, category, image_path, status, created_at FROM product WHERE title LIKE ? OR category LIKE ? OR description LIKE ? ORDER BY created_at DESC, product_id DESC";
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
                rs.getString("image_path"),
                rs.getString("status"),
                rs.getString("created_at")
        );
    }
}