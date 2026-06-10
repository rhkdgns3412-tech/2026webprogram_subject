package kr.hnu.ice.product.util;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.stream.Stream;

public final class ProductImageStorage {
    private static final String IMAGE_DIR_PROPERTY = "product.image.dir";
    private static final String IMAGE_DIR_ENV = "PRODUCT_IMAGE_DIR";
    private static final String DEFAULT_IMAGE_DIR = ".2026WebProgramClass_teamproject/product-images";

    private ProductImageStorage() {
    }

    public static Path getRootDirectory() {
        String configuredPath = System.getProperty(IMAGE_DIR_PROPERTY);
        if (configuredPath == null || configuredPath.trim().isEmpty()) {
            configuredPath = System.getenv(IMAGE_DIR_ENV);
        }

        Path rootDirectory = (configuredPath == null || configuredPath.trim().isEmpty())
                ? Paths.get(System.getProperty("user.home"), DEFAULT_IMAGE_DIR)
                : Paths.get(configuredPath.trim());

        try {
            Files.createDirectories(rootDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("상품 이미지 저장 폴더를 만들 수 없습니다.", e);
        }

        return rootDirectory;
    }

    public static void saveProductImage(String productId, Part imagePart) {
        if (imagePart == null || imagePart.getSize() == 0) {
            return;
        }

        String safeBaseName = buildSafeBaseName(productId);
        String extension = resolveExtension(imagePart.getSubmittedFileName(), imagePart.getContentType());
        Path rootDirectory = getRootDirectory();
        Path tempFile = null;

        try {
            tempFile = Files.createTempFile(rootDirectory, safeBaseName + "_", "." + extension);
            try (InputStream inputStream = imagePart.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            Path finalFile = rootDirectory.resolve(safeBaseName + "." + extension);
            Files.move(tempFile, finalFile, StandardCopyOption.REPLACE_EXISTING);
            deleteOtherImages(rootDirectory, safeBaseName, finalFile);
        } catch (IOException e) {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException ignored) {
                }
            }
            throw new IllegalStateException("상품 이미지를 저장할 수 없습니다.", e);
        }
    }

    public static Path findProductImage(String productId) {
        String safeBaseName = buildSafeBaseName(productId);
        Path rootDirectory = getRootDirectory();

        try (Stream<Path> stream = Files.list(rootDirectory)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(safeBaseName + "."))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new IllegalStateException("상품 이미지를 찾을 수 없습니다.", e);
        }
    }

    public static boolean hasProductImage(String productId) {
        return findProductImage(productId) != null;
    }

    public static void deleteProductImage(String productId) {
        String safeBaseName = buildSafeBaseName(productId);
        Path rootDirectory = getRootDirectory();

        try (Stream<Path> stream = Files.list(rootDirectory)) {
            stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(safeBaseName + "."))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                        }
                    });
        } catch (IOException e) {
            throw new IllegalStateException("상품 이미지를 삭제할 수 없습니다.", e);
        }
    }

    public static String buildImageUrl(String contextPath, String productId) {
        try {
            return contextPath + "/product-image?productId=" + URLEncoder.encode(productId, "UTF-8") + "&v=" + System.currentTimeMillis();
        } catch (Exception e) {
            return contextPath + "/product-image?productId=" + productId + "&v=" + System.currentTimeMillis();
        }
    }

    private static void deleteOtherImages(Path rootDirectory, String safeBaseName, Path finalFile) throws IOException {
        try (Stream<Path> stream = Files.list(rootDirectory)) {
            stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(safeBaseName + "."))
                    .filter(path -> !path.equals(finalFile))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                        }
                    });
        }
    }

    private static String buildSafeBaseName(String productId) {
        String source = productId == null ? "product" : productId.trim();
        if (source.isEmpty()) {
            source = "product";
        }

        String sanitized = source.replaceAll("[\\\\/:*?\"<>|]", "_").replaceAll("\\s+", "_");
        sanitized = sanitized.replaceAll("_+", "_");
        sanitized = sanitized.replaceAll("^\\.+", "");
        sanitized = sanitized.replaceAll("\\.+$", "");
        return sanitized.isEmpty() ? "product" : sanitized;
    }

    private static String resolveExtension(String submittedFileName, String contentType) {
        String extension = null;
        if (submittedFileName != null) {
            int index = submittedFileName.lastIndexOf('.');
            if (index > -1 && index < submittedFileName.length() - 1) {
                extension = submittedFileName.substring(index + 1).toLowerCase(Locale.ROOT);
            }
        }

        if (extension == null || extension.trim().isEmpty()) {
            if (contentType == null) {
                return "img";
            }

            String lowerContentType = contentType.toLowerCase(Locale.ROOT);
            if (lowerContentType.contains("png")) {
                return "png";
            }
            if (lowerContentType.contains("jpeg") || lowerContentType.contains("jpg")) {
                return "jpg";
            }
            if (lowerContentType.contains("gif")) {
                return "gif";
            }
            if (lowerContentType.contains("webp")) {
                return "webp";
            }

            return "img";
        }

        return extension;
    }
}