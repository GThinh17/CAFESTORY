package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerUpdateDTO;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/cafe-owners")
@RequiredArgsConstructor
public class CafeOwnerController {

    private final CafeOwnerService cafeOwnerService;


    @PostMapping
    public ResponseEntity<CafeOwnerResponse> createCafeOwner(
            @Valid @RequestBody CafeOwnerDTO dto
    ) {
        CafeOwnerResponse response = cafeOwnerService.createCafeOwner(dto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/exists")
    public ResponseEntity<Boolean> isCafeOwner(@PathVariable String userId) {
        Boolean exists = cafeOwnerService.isCafeOwner(userId);
        return ResponseEntity.ok(exists);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CafeOwnerResponse> getById(@PathVariable String id) {
        CafeOwnerResponse response = cafeOwnerService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CafeOwnerResponse> getByUserId(@PathVariable String userId) {
        CafeOwnerResponse response = cafeOwnerService.getByUserId(userId);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CafeOwnerResponse> updateCafeOwner(
            @PathVariable String id,
            @Valid @RequestBody CafeOwnerUpdateDTO dto
    ) {
        CafeOwnerResponse response = cafeOwnerService.updateCafeOwner(id, dto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{cafeOwnerId}")
    public ResponseEntity<Void> deleteCafeOwner(@PathVariable String cafeOwnerId) {
        cafeOwnerService.deleteCafeOwner(cafeOwnerId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<CafeOwnerResponse>> getAllCafeOwners() {
        List<CafeOwnerResponse> responses = cafeOwnerService.getAllCafeOwners();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/extend")
    public ResponseEntity<CafeOwnerResponse> extendCafeOwner(
            @PathVariable String id,
            @Valid @RequestBody CafeOwnerDTO dto
    ) {
        CafeOwnerResponse response = cafeOwnerService.extendCafeOwner(id, dto);
        return ResponseEntity.ok(response);
    }
}
