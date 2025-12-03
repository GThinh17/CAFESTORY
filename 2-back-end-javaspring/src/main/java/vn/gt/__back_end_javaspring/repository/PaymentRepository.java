package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
   public Payment findByPaymentId(String id);
}
