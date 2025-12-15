package vn.gt.__back_end_javaspring.service;

public interface UserRoleService {
    void registerCafeOwner(String userId);

    void removeCafeOwner(String userId);

    void registerReviewer(String userId);

    void removeReviewer(String userId);
}
