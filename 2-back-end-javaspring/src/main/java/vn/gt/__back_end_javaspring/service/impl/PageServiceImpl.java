package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.CafeOwner;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.exception.CafeOwnerNotFound;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.PageMapper;
import vn.gt.__back_end_javaspring.repository.CafeOwnerRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.PageService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private  final UserRepository userRepository;
    private final PageMapper pageMapper;
    private final CafeOwnerRepository cafeOwnerRepository;
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
        return List.of();
    }

    @Override
    public List<PageResponse> getAllPagesByFollwing() {
        return List.of();
    }

}
