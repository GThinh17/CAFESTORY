package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.User;

public interface CafeOwnerRepository extends JpaRepository<CafeOwner, String> {
    Boolean existsByUser(User user);

    // hoặc dùng luôn theo id của user:
    Boolean existsByUser_Id(String userId);

    CafeOwner findByUser_Id(String userId); // truy cập qua quan hệ user.id
}
