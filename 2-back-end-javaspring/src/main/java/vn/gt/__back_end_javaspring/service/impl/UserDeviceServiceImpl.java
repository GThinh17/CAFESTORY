package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.FcmTokenRequestDTO;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.UserDevice;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.repository.UserDeviceRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.UserDeviceService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeviceServiceImpl implements UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;
    private final UserRepository userRepository;



    @Override
    public void saveOrUpdate(String userId, FcmTokenRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserDevice userDevice = userDeviceRepository
                .findUserDeviceByFcmToken(dto.getFcmToken())
                .orElse(new UserDevice());

        userDevice.setUser(user);
        userDevice.setFcmToken(dto.getFcmToken());
        userDevice.setPlatform(dto.getPlatform());

        System.out.println("Get FCM token: "+ dto.getFcmToken());
        userDeviceRepository.save(userDevice);
    }

    @Override
    public void delete(String fcmToken) {
        userDeviceRepository.findUserDeviceByFcmToken(fcmToken)
                .orElseThrow(() -> new IllegalStateException("UserDevice not found"));

        System.out.println("HA xoa FCM token: " + fcmToken);
        userDeviceRepository.deleteByFcmToken(fcmToken);
    }

}

