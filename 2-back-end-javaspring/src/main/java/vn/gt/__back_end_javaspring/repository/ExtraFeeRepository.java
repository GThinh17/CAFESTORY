package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.ExtraFee;

public interface ExtraFeeRepository extends JpaRepository<ExtraFee, String> {
    public ExtraFee findByExtraFeeId(String id);
}
