package com.devlab.prodcatalog.backend.dto;

import com.devlab.prodcatalog.backend.service.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDto extends UserDto{

    private String password;

    public UserInsertDto(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
