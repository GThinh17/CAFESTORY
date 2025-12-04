package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.PricingRule;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PricingRuleRepository extends JpaRepository<PricingRule, String> {
    Optional<PricingRule> findFirstByIsActiveTrueAndEffectiveFromLessThanEqualAndEffectiveToGreaterThanEqual(
            LocalDateTime from,
            LocalDateTime to
    );
    PricingRule findFirstByIsActiveTrue();
}
