package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Payout;
import vn.gt.__back_end_javaspring.entity.PricingRule;

public interface PayOutRepository extends JpaRepository<Payout, String> {

}
