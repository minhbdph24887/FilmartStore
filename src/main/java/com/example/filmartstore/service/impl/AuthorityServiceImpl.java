package com.example.filmartstore.service.impl;

import com.example.filmartstore.auth.AccountRequest;
import com.example.filmartstore.auth.AccountResponse;
import com.example.filmartstore.entity.Account;
import com.example.filmartstore.entity.Role;
import com.example.filmartstore.repository.AccountRepository;
import com.example.filmartstore.repository.RoleRepository;
import com.example.filmartstore.service.AuthorityService;
import com.example.filmartstore.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthorityServiceImpl(AccountRepository accountRepository,
                                RoleRepository roleRepository,
                                AuthenticationManager authenticationManager,
                                JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public void saveAuthority(String email, String roleName) {
        Account detailAccount = accountRepository.findAccountByEmail(email).orElse(null);
        Role detailRole = roleRepository.findRoleByName(roleName);
        assert detailAccount != null;
        detailAccount.getRoles().add(detailRole);
    }

    @Override
    public AccountResponse loginSuccess(AccountRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Account detailAccountByEmail = accountRepository.findAccountByEmail(request.getEmail()).orElseThrow();
        List<Role> itemsRole = null;
        itemsRole = roleRepository.getAllRoleByEmail(detailAccountByEmail.getEmail());

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        itemsRole.forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));

        var jwtToken = jwtService.generateToken(detailAccountByEmail, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(detailAccountByEmail, authorities);
        AccountResponse response = new AccountResponse();
        response.setToken(jwtToken);
        response.setRefreshToken(jwtRefreshToken);
        System.out.println("ooooooooooo " + jwtToken);
        return response;
    }
}
