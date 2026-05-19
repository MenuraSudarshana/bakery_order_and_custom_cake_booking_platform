package lk.ac.sliit.bakery_site.service;

import lk.ac.sliit.bakery_site.dto.CustomerAuthResponseDto;
import lk.ac.sliit.bakery_site.dto.CustomerLoginRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerPasswordResetRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerProfileUpdateRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerRegisterRequestDto;

public interface ICustomerLoginService {

    CustomerAuthResponseDto register(CustomerRegisterRequestDto request);

    CustomerAuthResponseDto login(CustomerLoginRequestDto request);

    CustomerAuthResponseDto updateProfile(CustomerProfileUpdateRequestDto request);

    CustomerAuthResponseDto resetPassword(CustomerPasswordResetRequestDto request);
}

