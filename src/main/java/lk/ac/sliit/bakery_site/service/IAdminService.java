package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.AdminProductCreateRequestDto;
import lk.ac.sliit.bakery_site.model.CustomerOrder;
import lk.ac.sliit.bakery_site.model.Product;
import lk.ac.sliit.bakery_site.model.Reservation;
import lk.ac.sliit.bakery_site.model.CustomizeCakeOrder;

import java.util.Map;
import java.util.List;

public interface IAdminService {

    Map<String, Object> getSummary();

    List<Product> getAllProducts();

    Product createProduct(AdminProductCreateRequestDto request);

    int importWebsiteCatalogProducts();

    Product updateProduct(Integer productId, AdminProductCreateRequestDto request);

    Product updateProductActive(Integer productId, boolean active);

    //OrderPart
    List<CustomerOrder> getOrders(String status);

    CustomerOrder updateOrderStatus(Integer orderId, String status);


    //RsevationPart
    List<Reservation> getReservations();

    Reservation updateReservationStatus(Long reservationId, String status);

    void deleteReservation(Long reservationId);

    //Customize Cake

    List<CustomizeCakeOrder> getCustomizeCakeOrders(String status);

    CustomizeCakeOrder updateCustomizeCakeOrderStatus(Long orderId, String status);

    List<Product> getPublicProducts();
}
