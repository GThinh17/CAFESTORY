package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Production;

public interface ProductionRepository extends JpaRepository<Production, String> {
    public Production findByProductionId(String id);

}
