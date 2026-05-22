package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.CustomizeCakeOrderRequestDto;

public interface ICustomizeCakeOrderService {
    String createOrder(CustomizeCakeOrderRequestDto request);
}
