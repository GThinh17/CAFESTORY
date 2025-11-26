package vn.gt.__back_end_javaspring.service;

import java.util.List;

import vn.gt.__back_end_javaspring.entity.Production;

public interface ProductionService {
    public List<?> GetAllProduction();

    public Production GetProduction(String id);

    public Production CreateProduction(Production production);

    public Production UpdateProduction(Production production, String id);

}
