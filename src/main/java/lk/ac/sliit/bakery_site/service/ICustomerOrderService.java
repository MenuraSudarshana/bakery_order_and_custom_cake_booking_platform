package lk.ac.sliit.bakery_site.service;


import lk.ac.sliit.bakery_site.dto.CustomerOrderRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerOrderResponseDto;

public interface ICustomerOrderService {

    CustomerOrderResponseDto createOrder(CustomerOrderRequestDto request);
}
