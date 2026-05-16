package lk.ac.sliit.bakery_site.controller;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.service.IAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }



    @GetMapping("/products")
    public List<Product> products() {
        return adminService.getAllProducts();
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody AdminProductCreateRequestDto request) {
        return adminService.createProduct(request);
    }

    @PostMapping("/products/import-catalog")
    public Map<String, Object> importCatalogProducts() {
        int inserted = adminService.importWebsiteCatalogProducts();
        return Map.of("success", true, "inserted", inserted);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(IllegalArgumentException exception) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("success", false);
        payload.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }
}
