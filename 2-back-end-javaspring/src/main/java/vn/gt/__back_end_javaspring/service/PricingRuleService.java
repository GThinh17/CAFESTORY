package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PricingRuleService {
    PricingRuleResponse create(PricingRuleCreateDTO dto);

    PricingRuleResponse getById(String id);

    List<PricingRuleResponse> getAll();

    PricingRuleResponse activate(String id);

    PricingRuleResponse deactivate(String id);

    void delete(String id);

    PricingRuleResponse getActiveRuleAt(LocalDateTime dateTime);

    PricingRuleResponse getCurrentActiveRule();
}
