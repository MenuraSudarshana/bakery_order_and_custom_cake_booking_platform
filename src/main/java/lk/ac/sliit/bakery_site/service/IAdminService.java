package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.Product;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

public interface IAdminService {

    Map<String, Object> getSummary();

    List<Product> getAllProducts();

    Product createProduct(AdminProductCreateRequestDto request);

    int importWebsiteCatalogProducts();

    Product updateProduct(Integer productId, AdminProductCreateRequestDto request);

    Product updateProductActive(Integer productId, boolean active);

    List<Product> getPublicProducts();
}
