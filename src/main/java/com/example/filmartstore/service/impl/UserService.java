package com.example.filmartstore.service.impl;

import com.example.filmartstore.auth.AccountRequest;
import com.example.filmartstore.auth.AccountResponse;
import com.example.filmartstore.entity.Account;
import com.example.filmartstore.entity.Role;
import com.example.filmartstore.repository.AccountRepository;
import com.example.filmartstore.repository.RoleRepository;
import com.example.filmartstore.service.AuthorityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final AuthorityService authorityService;

    public UserService(AccountRepository accountRepository,
                       RoleRepository roleRepository,
                       AuthorityService authorityService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authorityService = authorityService;
    }

    public void checkLoginGoogleAccount(HttpServletResponse response, Authentication authentication) throws IOException {
        DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
        System.out.println("aaaaaaaaaaaaaaa " + oauthUser.getEmail());
        System.out.println("ccccccccccccccc " + oauthUser.getFullName());
        String email = oauthUser.getEmail();
        String fullName = oauthUser.getFullName();

        Account existUser = accountRepository.getUserByEmail(email);
        if (existUser == null) {
            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setFullName(fullName);

            Role roleUser = roleRepository.getAllRoleByUser();
            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(roleUser);
            newAccount.setRoles(rolesUser);

            accountRepository.save(newAccount);

            AccountRequest request = new AccountRequest(email, null);
            AccountResponse accountResponse = authorityService.loginSuccess(request);
            Cookie jwtCookie = new Cookie("jwtLoginGoogle", accountResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            response.sendRedirect("/filmart/home");
        } else {
            AccountRequest request = new AccountRequest(email, null);
            AccountResponse accountResponse = authorityService.loginSuccess(request);
            Cookie jwtCookie = new Cookie("jwtLoginGoogle", accountResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            response.sendRedirect("/filmart/home");
        }
    }
}
