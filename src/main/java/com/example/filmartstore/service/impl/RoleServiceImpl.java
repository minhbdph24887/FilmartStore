package com.example.filmartstore.service.impl;

import com.example.filmartstore.entity.Role;
import com.example.filmartstore.repository.RoleRepository;
import com.example.filmartstore.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Role role) {
        role.setStatus(1);
        roleRepository.save(role);
    }
}
