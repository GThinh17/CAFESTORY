package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.enums.FollowType;
import vn.gt.__back_end_javaspring.exception.CafeOwnerNotFound;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.PageMapper;
import vn.gt.__back_end_javaspring.repository.CafeOwnerRepository;
import vn.gt.__back_end_javaspring.repository.FollowRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.FollowService;
import vn.gt.__back_end_javaspring.service.PageService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private  final UserRepository userRepository;
    private final PageMapper pageMapper;
    private final CafeOwnerRepository cafeOwnerRepository;
    private final FollowService followService;
    private final FollowRepository followRepository;

    @Override
    public PageResponse createPage(PageCreateDTO request) {

        CafeOwner cafeOwner = cafeOwnerRepository.findById(request.getCafeOwnerId())
                .orElseThrow(()-> new CafeOwnerNotFound("CafeOwner not found"));

        Page page =pageMapper.toModel(request);
        page.setCafeOwner(cafeOwner);

        Page saved =  pageRepository.save(page);
        return pageMapper.toResponse(saved);
    }

    @Override
    public PageResponse getPageById(String pageId) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(()-> new PageNotFoundException("Page not found"));
        return pageMapper.toResponse(page);
    }

    @Override
    public PageResponse getPageByCafeOwnerId(String cafeOwnerId){
        CafeOwner cafeOwner = cafeOwnerRepository.findById(cafeOwnerId)
                .orElseThrow(()-> new CafeOwnerNotFound("CafeOwner not found"));

        Page page =  pageRepository.findPageByCafeOwner_Id(cafeOwner.getId());
        return pageMapper.toResponse(page);
    }

    @Override
    public PageResponse updatePage(PageUpdateDTO request, String pageId) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(()-> new PageNotFoundException("Page not found"));

        pageMapper.updateEntity(page, request);
        Page saved = pageRepository.save(page);
        return pageMapper.toResponse(saved);
    }

    @Override
    public void deletePage(String pageId) {
        Page page = pageRepository.findById(pageId)
                        .orElseThrow(()-> new PageNotFoundException("Page not found"));

        page.setIsDeleted(true);
        pageRepository.save(page);
    }

    @Override
    public List<PageResponse> getAllPagesOrderByFollowersDesc() {
        List<Page> pages = pageRepository.findAllOrderByFollowingCountDesc();
        if(pages.isEmpty()){
            throw new PageNotFoundException("Page not found");
        }

        return pageMapper.toResponse(pages);
    }

    @Override
    public List<PageResponse> getAllPagesFollowedByUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Follow> follows = followRepository.findAllByFollower_IdAndFollowType(userId, FollowType.PAGE);

        return follows.stream()
                .map(Follow::getFollowedPage)
                .filter(Objects::nonNull)
                .map(pageMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<PageResponse> getAllPagesFollowedByUserSortedAsc(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<PageResponse> pageResponses = getAllPagesFollowedByUser(userId);

        return pageResponses.stream()
                .sorted(Comparator.comparing(PageResponse::getFollowingCount).reversed())
                .collect(Collectors.toList());
    }



}
