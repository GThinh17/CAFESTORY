package vn.gt.__back_end_javaspring.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.entity.Share;

@RestControllerAdvice
@ControllerAdvice
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
		List<String> errors = fieldErorrs.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList()); // duyệt// qua	        // sách// errors
		res.setMessage(errors.size() > 1 ? errors : errors.get(0));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleBlogNotFound(BlogNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Blog not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleCommentNotFound(CommentNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Comment not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(LikeExist.class)
    public ResponseEntity<RestResponse<Object>> handleLikeExist(LikeExist ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Like already exist");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleCommentNotFound(UserNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleCommentNotFound(LikeNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Like not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleCommentNotFound(ShareNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Share not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }





}
