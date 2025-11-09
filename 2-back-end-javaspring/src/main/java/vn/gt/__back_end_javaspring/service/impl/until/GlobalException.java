package vn.gt.__back_end_javaspring.service.impl.until;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.gt.__back_end_javaspring.entity.RestResponse;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(value = { IdInvalidException.class, UsernameNotFoundException.class,
			BadCredentialsException.class })
	public ResponseEntity<RestResponse<Object>> handleException(IdInvalidException idInvalidException) {
		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage(idInvalidException.getMessage());
		res.setErrors("Id Invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	// (MethodArgumentNotValidException.class hfm xử lí ngoại lệ trong DTO ví dụ như
	// là NotBlank, Size, Valid
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse<Object>> validationEntity(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult(); // lấy thông tin chi tiết về kết quả validate exeception
		final List<FieldError> fieldErorrs = result.getFieldErrors(); // lấy tất cả các lỗi liên quan đến field bị vi
																		// phạm ràng buộc

		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setErrors(ex.getBody().getDetail()); // lấy lỗi chi tiết từ ExceptionBody

		List<String> errors = fieldErorrs.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList()); // duyệt
																													// qua
																													// các
																													// fielErorr
																													// gôm
																													// lại
																													// thành
																													// danh
																													// sách
																													// errors
		res.setMessage(errors.size() > 1 ? errors : errors.get(0));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
}
