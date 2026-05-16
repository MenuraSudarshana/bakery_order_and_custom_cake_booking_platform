package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class AdminService implements IAdminService {

    private final ProductRepository productRepository;




    public AdminService(
            ProductRepository productRepository

    ) {
        this.productRepository = productRepository;

    }

    @Override
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalProducts", productRepository.countByActiveTrue());

        return summary;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Product createProduct(AdminProductCreateRequestDto request) {
        Product product = new Product();
        applyProductPayload(product, request);
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now().toString());
        product.setUpdatedAt(LocalDateTime.now().toString());
        return productRepository.save(product);
    }

    @Override
    public int importWebsiteCatalogProducts() {
        return 0;
    }

    @Override
    public Product updateProduct(Integer productId, AdminProductCreateRequestDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));
        applyProductPayload(product, request);
        product.setUpdatedAt(LocalDateTime.now().toString());
        return productRepository.save(product);
    }

    @Override
    public Product updateProductActive(Integer productId, boolean active) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));
        product.setActive(active);
        product.setUpdatedAt(LocalDateTime.now().toString());
        return productRepository.save(product);
    }

 @Override
    public List<Product> getPublicProducts() {
        return productRepository.findByActiveTrueAndStockGreaterThanOrderByIdDesc(0);
    }

    private void applyProductPayload(Product product, AdminProductCreateRequestDto request) {
        String name = clean(request.getName());
        String category = clean(request.getCategory());
        String flavour = clean(request.getFlavour());
        String description = clean(request.getDescription());
        String imageUrl = clean(request.getImageUrl());
        double price = request.getPrice() == null ? 0 : request.getPrice();
        double rating = request.getRating() == null ? 4.5 : request.getRating();
        int stock = request.getStock() == null ? 0 : request.getStock();

        if (name.isEmpty() || category.isEmpty() || flavour.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("All product fields are required.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }

        product.setName(name);
        product.setCategory(category);
        product.setFlavour(flavour);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setPrice(price);
        product.setRating(rating);
        product.setStock(stock);
    }

    private String normalizeStatus(String value) {
        String cleaned = clean(value).toLowerCase(Locale.ROOT);
        if (cleaned.isEmpty()) {
            return "";
        }
        return cleaned.substring(0, 1).toUpperCase(Locale.ROOT) + cleaned.substring(1);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}

