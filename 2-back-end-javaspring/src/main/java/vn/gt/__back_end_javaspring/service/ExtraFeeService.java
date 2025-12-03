package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.ExtraFee;
import vn.gt.__back_end_javaspring.repository.ExtraFeeRepository;

public interface ExtraFeeService {
    public List<?> GetAllExtraFee();

    public ExtraFee GetExtraFee(String id);

    public ExtraFee CreateExtraFee(ExtraFee extraFee);
}
