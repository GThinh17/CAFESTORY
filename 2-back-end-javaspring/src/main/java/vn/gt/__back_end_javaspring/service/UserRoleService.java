package vn.gt.__back_end_javaspring.service;

import java.util.List;

import vn.gt.__back_end_javaspring.DTO.UserRoleResponse;
import vn.gt.__back_end_javaspring.entity.UserRole;

public interface UserRoleService {

    void registerCafeOwner(String userId);

    void removeCafeOwner(String userId);

    void registerReviewer(String userId);

    void removeReviewer(String userId);
}
