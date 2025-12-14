package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.BadgeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.DTO.BadgeUpdateDTO;

import java.util.List;

public interface BadgeService {
    BadgeResponse createBadge(BadgeCreateDTO badgeCreateDTO);
    BadgeResponse updateBadge(String badgeId, BadgeUpdateDTO badgeUpdateDTO);
    void softDelete(String badgeId);
    BadgeResponse getBadgeById(String badgeId);
    List<BadgeResponse> getAllBadges(boolean includeDeleted);
}
