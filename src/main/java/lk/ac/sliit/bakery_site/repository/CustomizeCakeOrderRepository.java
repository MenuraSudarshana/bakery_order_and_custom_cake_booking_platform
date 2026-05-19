package lk.ac.sliit.bakery_site.repository;

import lk.ac.sliit.bakery_site.model.CustomizeCakeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizeCakeOrderRepository extends JpaRepository<CustomizeCakeOrder, Long> {
    List<CustomizeCakeOrder> findByCustomerIdOrderByIdDesc(Integer customerId);
    List<CustomizeCakeOrder> findByCustomerEmailIgnoreCaseOrderByIdDesc(String customerEmail);
}
