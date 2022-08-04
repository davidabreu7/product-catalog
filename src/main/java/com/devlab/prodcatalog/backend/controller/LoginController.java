package com.devlab.prodcatalog.backend.controller;

import com.devlab.prodcatalog.backend.dto.UserAuthenticationDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody UserAuthenticationDto userAuthenticationDto) {
    }
}
