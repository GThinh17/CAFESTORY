package vn.gt.__back_end_javaspring.repository;

import com.google.firebase.database.core.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.UserDevice;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, String> {
    Optional<UserDevice> findUserDeviceByFcmToken(String fcmToken);

    Optional<UserDevice> findByUser_IdAndPlatform(String userId, String platform);

    void deleteByFcmToken(String fcmToken);

    UserDevice findUserDeviceByUser_Id(String userId);
}
