package vn.gt.__back_end_javaspring.mapper;

import org.springframework.stereotype.Component;

import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.DTO.UserDTO;

@Component
public class UserMapper {

    public void partialUpdate(UserDTO dto, User user) {
        if (dto.getFullName() != null) 
            user.setFullName(dto.getFullName());

        if (dto.getPhone() != null) 
            user.setPhone(dto.getPhone());

        if (dto.getEmail() != null) 
            user.setEmail(dto.getEmail());

        if (dto.getDateOfBirth() != null) 
            user.setDateOfBirth(dto.getDateOfBirth());

        if (dto.getAddress() != null) 
            user.setAddress(dto.getAddress());

        if (dto.getAvatar() != null) 
            user.setAvatar(dto.getAvatar());

        if (dto.getFollowerCount() != null) 
            user.setFollowerCount(dto.getFollowerCount());

        if (dto.getFollowingCount() != null) 
            user.setFollowingCount(dto.getFollowingCount());

        if (dto.getCreatedAt() != null) 
            user.setCreatedAt(dto.getCreatedAt());

        if (dto.getUpdatedAt() != null) 
            user.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getVertifiedBank() != null) 
            user.setVertifiedBank(dto.getVertifiedBank());
    }
}
