package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.CustomerOrder;
import lk.ac.sliit.bakery_site.repository.CustomerOrderRepository;
import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.repository.ProductRepository;
import lk.ac.sliit.bakery_site.model.Reservation;
import lk.ac.sliit.bakery_site.repository.ReservationRepository;
import lk.ac.sliit.bakery_site.model.CustomizeCakeOrder;
import lk.ac.sliit.bakery_site.repository.CustomizeCakeOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class AdminService implements IAdminService {

    private final ProductRepository productRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final ReservationRepository reservationRepository;
    private final CustomizeCakeOrderRepository customizeCakeOrderRepository;

    public AdminService(
            ProductRepository productRepository,
            CustomerOrderRepository customerOrderRepository,
            ReservationRepository reservationRepository,
            CustomizeCakeOrderRepository customizeCakeOrderRepository

    ) {
        this.productRepository = productRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.reservationRepository = reservationRepository;
        this.customizeCakeOrderRepository = customizeCakeOrderRepository;

    }

    @Override
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalProducts", productRepository.countByActiveTrue());
        summary.put("pendingOrders", customerOrderRepository.countByOrderStatusIgnoreCase("Pending"));
        summary.put("successfulOrders", customerOrderRepository.countByOrderStatusIgnoreCase("Successful"));

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

    //Orders
    @Override
    public List<CustomerOrder> getOrders(String status) {
        String cleanStatus = clean(status);
        if (cleanStatus.isEmpty() || cleanStatus.equalsIgnoreCase("all")) {
            return customerOrderRepository.findAllByOrderByIdDesc();
        }
        return customerOrderRepository.findByOrderStatusIgnoreCaseOrderByIdDesc(cleanStatus);
    }

    @Override
    public CustomerOrder updateOrderStatus(Integer orderId, String status) {
        String cleanStatus = normalizeStatus(status);
        if (!List.of("Pending", "Successful", "Cancelled").contains(cleanStatus)) {
            throw new IllegalArgumentException("Invalid order status.");
        }
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setOrderStatus(cleanStatus);
        return customerOrderRepository.save(order);
    }

    //Reservation
    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Reservation updateReservationStatus(Long reservationId, String status) {
        String cleanStatus = normalizeStatus(status);
        if (!List.of("Pending", "Successful", "Cancelled").contains(cleanStatus)) {
            throw new IllegalArgumentException("Invalid reservation status.");
        }
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
        reservation.setReservationStatus(cleanStatus);
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
        reservationRepository.delete(reservation);
    }

    //Customize Cake

    @Override
    public List<CustomizeCakeOrder> getCustomizeCakeOrders(String status) {
        List<CustomizeCakeOrder> all = customizeCakeOrderRepository.findAll();
        all.sort((a, b) -> Long.compare(b.getId(), a.getId()));
        String cleanStatus = clean(status);
        if (cleanStatus.isEmpty() || cleanStatus.equalsIgnoreCase("all")) {
            return all;
        }
        return all.stream()
                .filter(order -> cleanStatus.equalsIgnoreCase(order.getOrderStatus()))
                .toList();
    }

    @Override
    public CustomizeCakeOrder updateCustomizeCakeOrderStatus(Long orderId, String status) {
        String cleanStatus = normalizeStatus(status);
        if (!List.of("Pending", "Successful", "Cancelled").contains(cleanStatus)) {
            throw new IllegalArgumentException("Invalid custom cake order status.");
        }
        CustomizeCakeOrder order = customizeCakeOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Custom cake order not found."));
        order.setOrderStatus(cleanStatus);
        if (cleanStatus.equalsIgnoreCase("Cancelled")) {
            order.setCancelRequested(false);
        }
        if (cleanStatus.equalsIgnoreCase("Pending")) {
            order.setCancelRequested(false);
        }
        order.setUpdateRequested(false);
        order.setUpdateRequestNote(null);
        return customizeCakeOrderRepository.save(order);
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

