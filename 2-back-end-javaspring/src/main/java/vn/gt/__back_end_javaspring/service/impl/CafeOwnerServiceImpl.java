package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.entity.Embedded.UserRoleId;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.exception.CafeOwnerNotFound;
import vn.gt.__back_end_javaspring.exception.ConflictRole;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.CafeOwnerMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;
import lombok.Data;
import vn.gt.__back_end_javaspring.service.PageService;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CafeOwnerServiceImpl implements CafeOwnerService {

    private final CafeOwnerRepository cafeOwnerRepository;
    private final UserRepository userRepository;
    private final CafeOwnerMapper cafeOwnerMapper;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PageRepository pageRepository;
    private final PageService pageService;
    private final ReviewerRepository reviewerRepository;
    @Override
    public CafeOwnerResponse createCafeOwner(CafeOwnerDTO dto) {
        // 1. Lấy user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);

        CafeOwner cafeOwner1 = cafeOwnerRepository.findByUser_Id(dto.getUserId());
        if(isCafeOwner(dto.getUserId())){
            extendCafeOwner(getByUserId(dto.getUserId()).getId(), dto);
            return cafeOwnerMapper.toResponse(cafeOwner1);
        }

        UserRoleId userRoleId = new UserRoleId(
                user.getId(),
                role.getId()
        );

        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setCreatedAt(LocalDateTime.now());

        userRoleRepository.save(userRole);

        CafeOwner cafeOwner = cafeOwnerMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        cafeOwner.setExpiredAt(now.plusMonths(dto.getDuration()));
        cafeOwner.setUser(user);

        cafeOwnerRepository.save(cafeOwner);
        return cafeOwnerMapper.toResponse(cafeOwner);
    }

    @Override
    public Boolean isCafeOwner(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return cafeOwnerRepository.existsByUser(user);
    }

    @Override
    public CafeOwnerResponse getById(String id) {
        CafeOwner cafeOwner = cafeOwnerRepository.findById(id)
                .orElseThrow(() -> new CafeOwnerNotFound("User not found"));
        return cafeOwnerMapper.toResponse(cafeOwner);
    }

    @Override
    public CafeOwnerResponse getByUserId(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        CafeOwner cafeOwner = cafeOwnerRepository.findByUser_Id(userId);

        if (cafeOwner == null) {
            throw new CafeOwnerNotFound("Cafe owner not found");
        }
        return cafeOwnerMapper.toResponse(cafeOwner);
    }

    @Override
    public CafeOwnerResponse updateCafeOwner(String id, CafeOwnerUpdateDTO dto) {
        CafeOwner cafeOwner = cafeOwnerRepository.findById(id)
                .orElseThrow(() -> new CafeOwnerNotFound("User not found"));

        cafeOwnerMapper.updateEntity(dto, cafeOwner);
        cafeOwnerRepository.save(cafeOwner);
        return cafeOwnerMapper.toResponse(cafeOwner);

    }

    @Override
    public void deleteCafeOwner(String cafeOwnerId) {

        CafeOwner cafeOwner =  cafeOwnerRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new CafeOwnerNotFound("User not found"));
        Page page = pageRepository.findPageByCafeOwner_Id(cafeOwnerId);

        System.out.println("page = " + page);

        pageRepository.delete(page);
        cafeOwnerRepository.delete(cafeOwner);

        Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);
        UserRole userRole = userRoleRepository.findByUser_IdAndRole_Id(cafeOwner.getUser().getId(), role.getId());
        userRoleRepository.delete(userRole);

    }

    @Override
    public List<CafeOwnerResponse> getAllCafeOwners() {
        List<CafeOwner> cafeOwners = cafeOwnerRepository.findAll();
        List<CafeOwnerResponse> cafeOwnerResponse = cafeOwnerMapper.toResponseList(cafeOwners);
        return cafeOwnerResponse;
    }

    @Override
    public CafeOwnerResponse extendCafeOwner(String cafeOwnerId, CafeOwnerDTO dto) {
        CafeOwner cafeOwner = cafeOwnerRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new CafeOwnerNotFound("CafeOwner not found"));

        Integer duration = dto.getDuration();
        int extendMonths = duration;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = cafeOwner.getExpiredAt();

        if(base == null || base.isBefore(now)) {
            base = now;
        }

        cafeOwner.setExpiredAt(base.plusMonths(extendMonths));
        cafeOwnerRepository.save(cafeOwner);
        return cafeOwnerMapper.toResponse(cafeOwner);
    }

    @Override
    public String getUserId(String cafeOwnerId) {
        CafeOwner cafeOwner = cafeOwnerRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new CafeOwnerNotFound("CafeOwner not found"));

        String userId = cafeOwner.getUser().getId();
        return userId;
    }



}
