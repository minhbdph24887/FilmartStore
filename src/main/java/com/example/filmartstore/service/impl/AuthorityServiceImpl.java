package com.example.filmartstore.service.impl;

import com.example.filmartstore.entity.Account;
import com.example.filmartstore.entity.Role;
import com.example.filmartstore.repository.AccountRepository;
import com.example.filmartstore.repository.RoleRepository;
import com.example.filmartstore.service.AuthorityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AuthorityServiceImpl(AccountRepository accountRepository,
                                RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveAuthority(String email, String roleName) {
        Account detailAccount = accountRepository.findAccountByEmail(email).orElse(null);
        Role detailRole = roleRepository.findRoleByName(roleName);
        assert detailAccount != null;
        detailAccount.getRoles().add(detailRole);
    }
}
