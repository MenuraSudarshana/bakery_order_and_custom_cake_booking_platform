package lk.ac.sliit.bakery_site.dto;

import lk.ac.sliit.bakery_site.dto.CustomerAuthResponseDto;
import lk.ac.sliit.bakery_site.dto.CustomerLoginRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerPasswordResetRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerProfileUpdateRequestDto;
import lk.ac.sliit.bakery_site.dto.CustomerRegisterRequestDto;

public class CustomerLoginRequestDto {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

