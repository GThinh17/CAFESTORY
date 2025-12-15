package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.FcmTokenRequestDTO;
import vn.gt.__back_end_javaspring.entity.UserDevice;

import java.util.Optional;

public interface UserDeviceService {
    void saveOrUpdate(String userId, FcmTokenRequestDTO dto);
    void delete(String fcmToken);
}
