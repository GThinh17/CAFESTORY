package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.entity.Role;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.UserRole;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.repository.RoleRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.UserRoleRepository;
import vn.gt.__back_end_javaspring.service.UserRoleService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    @Override
    public void registerCafeOwner(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
    }

    @Override
    public void removeCafeOwner(String userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);
        UserRole userRole = userRoleRepository.findByUser_IdAndRole_Id(userId,role.getId());

        userRoleRepository.delete(userRole);

    }

    @Override
    public void registerReviewer(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByroleName(RoleType.REVIEWER);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
    }

    @Override
    public void removeReviewer(String userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);
        UserRole userRole = userRoleRepository.findByUser_IdAndRole_Id(userId,role.getId());

        userRoleRepository.delete(userRole);
    }
}
