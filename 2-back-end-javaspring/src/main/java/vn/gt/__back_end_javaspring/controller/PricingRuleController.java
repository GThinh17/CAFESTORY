package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.PricingRuleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
@RequiredArgsConstructor
public class PricingRuleController {

    private final PricingRuleService pricingRuleService;

    @PostMapping("")
    public ResponseEntity<PricingRuleResponse> createPricingRule(
            @Valid @RequestBody PricingRuleCreateDTO dto
    ) {
        PricingRuleResponse data = pricingRuleService.create(dto);


        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingRuleResponse> getById(@PathVariable String id) {
        PricingRuleResponse data = pricingRuleService.getById(id);

        return ResponseEntity.ok().body(data);
    }
    @GetMapping("")
    public ResponseEntity<List<PricingRuleResponse>> getAll() {
        List<PricingRuleResponse> data = pricingRuleService.getAll();

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PricingRuleResponse> activate(@PathVariable String id) {
        PricingRuleResponse data = pricingRuleService.activate(id);


        return ResponseEntity.ok().body(data);
    }
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<PricingRuleResponse> deactivate(@PathVariable String id) {
        PricingRuleResponse data = pricingRuleService.deactivate(id);


        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        pricingRuleService.delete(id);


        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-at")
    public ResponseEntity<PricingRuleResponse> getActiveRuleAt(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime dateTime
    ) {
        PricingRuleResponse data = pricingRuleService.getActiveRuleAt(dateTime);


        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/current-active")
    public ResponseEntity<PricingRuleResponse> getCurrentActiveRule() {
        PricingRuleResponse data = pricingRuleService.getCurrentActiveRule();

        return ResponseEntity.ok().body(data);
    }
}
