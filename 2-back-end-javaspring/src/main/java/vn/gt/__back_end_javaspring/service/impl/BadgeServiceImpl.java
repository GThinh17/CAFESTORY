package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.BadgeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.DTO.BadgeUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Badge;
import vn.gt.__back_end_javaspring.exception.BadgeNotFound;
import vn.gt.__back_end_javaspring.mapper.BadgeMapper;
import vn.gt.__back_end_javaspring.repository.BadgeRepository;
import vn.gt.__back_end_javaspring.service.BadgeService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    private final BadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;

    @Override
    public BadgeResponse createBadge(BadgeCreateDTO badgeCreateDTO) {
        Badge badge = badgeMapper.toEntity(badgeCreateDTO);
        Badge saved = badgeRepository.save(badge);
        return badgeMapper.toResponse(saved);
    }

    @Override
    public BadgeResponse updateBadge(String badgeId, BadgeUpdateDTO badgeUpdateDTO) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(()-> new BadgeNotFound("badge not found"));

        badgeMapper.updateEntity(badgeUpdateDTO, badge);
        badgeRepository.save(badge);
        return badgeMapper.toResponse(badge);
    }

    @Override
    public void softDelete(String badgeId) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(()-> new BadgeNotFound("badge not found"));
        badge.setIsDeleted(true);
        badgeRepository.save(badge);

    }

    @Override
    public BadgeResponse getBadgeById(String badgeId) {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(()-> new BadgeNotFound("badge not found"));

        return badgeMapper.toResponse(badge);
    }

    @Override
    public List<BadgeResponse> getAllBadges(boolean includeDeleted) {
        List<Badge> badges;

        if (includeDeleted) {
            badges = badgeRepository.findAll();
        } else {
            badges = badgeRepository.findAllByIsDeletedFalse();
        }
        return badgeMapper.toResponses(badges);
    }

}
