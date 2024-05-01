package de.hska.iwi.EShop.user.service;

import de.hska.iwi.EShop.user.api.exception.RoleNotDefinedException;
import de.hska.iwi.EShop.user.persistence.Role;
import de.hska.iwi.EShop.user.persistence.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> getRoleById(final int id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByLevel(final int level) {
        return roleRepository.findByRoleLevel(level);
    }

    public Role getRegularUserRole() {
        return roleRepository.findByRoleLevel(1).orElseThrow(RoleNotDefinedException::new);
    }
}
