package de.hska.iwi.EShop.user.api;

import de.hska.iwi.EShop.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleApi {
    private final RoleService roleService;

    @Override
    public ResponseEntity<RoleDTO> getRoleById(Integer id) {
        return roleService.getRoleById(id)
                .map(role -> ResponseEntity.ok(RoleDTO.builder()
                        .id(role.getId())
                        .typ(role.getRoleType())
                        .level(role.getRoleLevel())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<RoleDTO> getRoleByLevel(GetRoleByLevelRequestDTO getRoleByLevelRequestDTO) {
        return roleService.getRoleByLevel(getRoleByLevelRequestDTO.getRoleLevel())
                .map(role -> ResponseEntity.ok(RoleDTO.builder()
                        .id(role.getId())
                        .typ(role.getRoleType())
                        .level(role.getRoleLevel())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
