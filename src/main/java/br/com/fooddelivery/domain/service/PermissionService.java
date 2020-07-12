package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.PermissionNotFoundException;
import br.com.fooddelivery.domain.model.Permission;
import br.com.fooddelivery.domain.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission getPermissionById(Integer id) {
        return permissionRepository
                .findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
