package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.entity.PricingRule;
import vn.gt.__back_end_javaspring.exception.PricingRuleConflictException;
import vn.gt.__back_end_javaspring.exception.PricingRuleNotFound;
import vn.gt.__back_end_javaspring.exception.PricingRuleStatusException;
import vn.gt.__back_end_javaspring.mapper.PricingRuleMapper;
import vn.gt.__back_end_javaspring.repository.PricingRuleRepository;
import vn.gt.__back_end_javaspring.service.PricingRuleService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PricingRuleServiceImpl implements PricingRuleService {
    private final PricingRuleRepository pricingRuleRepository;
    private final PricingRuleMapper pricingRuleMapper;


    @Override
    public PricingRuleResponse create(PricingRuleCreateDTO dto) {
        PricingRule pricingRule = pricingRuleMapper.toEntity(dto);
        if(dto.getEffectiveFrom().isAfter(dto.getEffectiveTo())) {
            throw new PricingRuleConflictException("Effective from should be before effective to date");
        }
        PricingRule saved =  pricingRuleRepository.save(pricingRule);
        return pricingRuleMapper.toResponse(saved);
    }

    @Override
    public PricingRuleResponse getById(String id) {
        PricingRule pricingRule = pricingRuleRepository.findById(id)
                .orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));

        return pricingRuleMapper.toResponse(pricingRule);
    }

    @Override
    public List<PricingRuleResponse> getAll() {
        List<PricingRule> pricingRules = pricingRuleRepository.findAll();

        return pricingRuleMapper.toResponseList(pricingRules);
    }

    @Override
    public PricingRuleResponse activate(String id) {
        PricingRule pricingRule = pricingRuleRepository
                .findById(id).orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));

        if(!pricingRule.getIsActive()){
            pricingRule.setIsActive(true);
        } else{
            throw new PricingRuleStatusException("PricingRule already active");
        }

        pricingRuleRepository.save(pricingRule);
        return pricingRuleMapper.toResponse(pricingRule);
    }

    @Override
    public PricingRuleResponse deactivate(String id) {
        PricingRule pricingRule = pricingRuleRepository
                .findById(id).orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));
        if(pricingRule.getIsActive()) {
            pricingRule.setIsActive(false);
        } else{
            throw new PricingRuleStatusException("PricingRule already inactive");
        }

        pricingRuleRepository.save(pricingRule);
        return pricingRuleMapper.toResponse(pricingRule);
    }

    @Override
    public void delete(String id) {
        PricingRule pricingRule = pricingRuleRepository
                .findById(id).orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));

        pricingRule.setIsDeleted(true);
        pricingRule.setIsActive(false);
        pricingRuleRepository.save(pricingRule);
    }

    @Override
    public PricingRuleResponse getActiveRuleAt(LocalDateTime dateTime) {
        PricingRule pricingRule = pricingRuleRepository
                .findFirstByIsActiveTrueAndEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(dateTime, dateTime)
                .orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));

        return pricingRuleMapper.toResponse(pricingRule);
    }

    @Override
    public PricingRuleResponse getCurrentActiveRule() {
        LocalDateTime now = LocalDateTime.now();
        PricingRule pricingRule = pricingRuleRepository
                .findFirstByIsActiveTrueAndEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(now, now)
                .orElseThrow(()-> new PricingRuleNotFound("pricingRule not found"));
        return pricingRuleMapper.toResponse(pricingRule);
    }
}
