// package vn.gt.__back_end_javaspring.service.impl;
//
// import java.util.List;
//
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import vn.gt.__back_end_javaspring.repository.ExtraFeeRepository;
// import vn.gt.__back_end_javaspring.service.ExtraFeeService;
//
// @Service
// @RequiredArgsConstructor
// public class ExtraFeeServiceImpl implements ExtraFeeService {
// private final ExtraFeeRepository extraFeeRepository;
//
// @Override
// public List<?> GetAllExtraFee() {
// List<ExtraFee> list = this.extraFeeRepository.findAll();
// return list;
// }
//
// @Override
// public ExtraFee GetExtraFee(String id) {
// return this.extraFeeRepository.findByExtraFeeId(id);
// }
//
// @Override
// public ExtraFee CreateExtraFee(ExtraFee extraFee) {
// return this.extraFeeRepository.save(extraFee);
// }
//
// }
