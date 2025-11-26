package vn.gt.__back_end_javaspring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
    public Production CreateProduction(Production production) {
        return this.productionRepository.save(production);
    }

    @Override
    public Production UpdateProduction(Production production, String id) {
        Production updateProduction = this.productionRepository.findByProductionId(id);
        updateProduction = this.productionRepository.save(production);
        return updateProduction;
    }

}
