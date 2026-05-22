package lk.ac.sliit.bakery_site.controller;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.service.IAdminService;
import lk.ac.sliit.bakery_site.model.Reservation;
import lk.ac.sliit.bakery_site.model.CustomerOrder;
import lk.ac.sliit.bakery_site.model.CustomizeCakeOrder;
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

    //OrderPart

    @GetMapping("/orders")
    public List<CustomerOrder> orders(@RequestParam(defaultValue = "all") String status) {
        return adminService.getOrders(status);
    }

    @PatchMapping("/orders/{orderId}/status")
    public CustomerOrder updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        return adminService.updateOrderStatus(orderId, status);
    }


    //ReservationPart

    @GetMapping("/reservations")
    public List<Reservation> reservations() {
        return adminService.getReservations();
    }

    @PatchMapping("/reservations/{reservationId}/status")
    public Reservation updateReservationStatus(@PathVariable Long reservationId, @RequestParam String status) {
        return adminService.updateReservationStatus(reservationId, status);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public Map<String, Object> deleteReservation(@PathVariable Long reservationId) {
        adminService.deleteReservation(reservationId);
        return Map.of("success", true);
    }


    //Customize Cake

    @GetMapping("/customize-cake-orders")
    public List<CustomizeCakeOrder> customizeCakeOrders(@RequestParam(defaultValue = "all") String status) {
        return adminService.getCustomizeCakeOrders(status);
    }

    @PatchMapping("/customize-cake-orders/{orderId}/status")
    public CustomizeCakeOrder updateCustomizeCakeOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return adminService.updateCustomizeCakeOrderStatus(orderId, status);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(IllegalArgumentException exception) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("success", false);
        payload.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }
}
