package vn.gt.__back_end_javaspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.ProductionDTO;
import vn.gt.__back_end_javaspring.entity.Production;
import vn.gt.__back_end_javaspring.service.ProductionService;

@RestController
@RequestMapping("/api/production")
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    @GetMapping("")
    public ResponseEntity<?> GetAllProduction() {
        return ResponseEntity.ok().body(this.productionService.GetAllProduction());
    }

    @PostMapping("")
    public ResponseEntity CreateProduction(@RequestBody ProductionDTO entity) {
        System.out.println(entity);
        return ResponseEntity.ok().body(this.productionService.CreateProduction(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity GetProduction(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.productionService.GetProduction(id));
    }
}
