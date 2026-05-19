package lk.ac.sliit.bakery_site.controller;

import lk.ac.sliit.bakery_site.dto.CustomizeCakeOrderRequestDto;
import lk.ac.sliit.bakery_site.service.ICustomizeCakeOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/customize-cake-orders")
public class CustomizeCakeOrderController {

    private final ICustomizeCakeOrderService customizeCakeOrderService;

    public CustomizeCakeOrderController(ICustomizeCakeOrderService customizeCakeOrderService) {
        this.customizeCakeOrderService = customizeCakeOrderService;
    }

    @PostMapping
    public String createOrder(@RequestBody CustomizeCakeOrderRequestDto request) {
        return customizeCakeOrderService.createOrder(request);
    }
}
