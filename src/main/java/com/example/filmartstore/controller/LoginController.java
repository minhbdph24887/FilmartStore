package com.example.filmartstore.controller;

import com.example.filmartstore.auth.AccountRequest;
import com.example.filmartstore.auth.AccountResponse;
import com.example.filmartstore.service.AuthorityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/filmart/login/")
public class LoginController {
    private final AuthorityService authorityService;

    public LoginController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping(value = "from")
    public String loginFrom() {
        return "login/Form";
    }

    @PostMapping(value = "success")
    public String loginSuccess(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpServletResponse response) {
        AccountRequest request = new AccountRequest(email, password);
        AccountResponse accountResponse = authorityService.loginSuccess(request);
        Cookie jwtCookie = new Cookie("jwtLogin", accountResponse.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        return "redirect:/filmart/home";
    }
}
