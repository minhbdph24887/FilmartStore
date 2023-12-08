package com.example.filmartstore.service;

import com.example.filmartstore.auth.AccountRequest;
import com.example.filmartstore.auth.AccountResponse;

public interface AuthorityService {
    void saveAuthority(String email, String roleName);

    AccountResponse loginSuccess(AccountRequest request);
}
