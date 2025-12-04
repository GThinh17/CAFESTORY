package vn.gt.__back_end_javaspring.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.ProductionDTO;
import vn.gt.__back_end_javaspring.entity.Production;
import vn.gt.__back_end_javaspring.repository.ProductionRepository;
import vn.gt.__back_end_javaspring.service.ProductionService;

@Service
@RequiredArgsConstructor
public class ProductionServiceImpl implements ProductionService {
    private final ProductionRepository productionRepository;

    @Override
    public List<?> GetAllProduction() {
        return this.productionRepository.findAll();
    }

    @Override
    public Production GetProduction(String id) {
        return this.productionRepository.findByProductionId(id);
    }

    @Override
    public Production CreateProduction(ProductionDTO productionDTO) {
        Production production = productionDTO.getProduction();

        // tao timeexpire
        int timeExpired = productionDTO.getTimeExpired();
        production.setTimeExpired(timeExpired);

        return this.productionRepository.save(production);
    }

    @Override
    public Production UpdateProduction(Production production, String id) {
        Production updateProduction = this.productionRepository.findByProductionId(id);
        updateProduction = this.productionRepository.save(production);
        return updateProduction;
    }

}
