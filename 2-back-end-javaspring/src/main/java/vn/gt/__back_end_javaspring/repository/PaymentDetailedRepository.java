package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.PaymentDetailed;

public interface PaymentDetailedRepository extends JpaRepository<PaymentDetailed, String> {
    public PaymentDetailed findByUser_id(String userid);
}
