package vn.gt.__back_end_javaspring.exception;

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

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    // ---Handle wwith invalid
    @ExceptionHandler(IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInvalid(IdInvalidException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Id invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    public ResponseEntity<RestResponse<Object>> handleAuthException(RuntimeException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Authentication failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        List<String> errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage("Validation failed");
        res.setErrors(String.join("; ", errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // --handle with not found
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleLikeNotFound(LikeNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Like not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleShareNotFound(ShareNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Share not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handlePageNotFound(PageNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Page not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(WalletNotFound.class)
    public ResponseEntity<RestResponse<Object>> handleWalletNotFound(WalletNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Wallet not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(WalletTransactionNotFound.class)
    public ResponseEntity<RestResponse<Object>> handleWalletTransactionNotFound(WalletTransactionNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Wallet transaction not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(PricingRuleNotFound.class)
    public ResponseEntity<RestResponse<Object>> handlePricingRuleNotFound(PricingRuleNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Pricing rule not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(BadgeNotFound.class)
    public ResponseEntity<RestResponse<Object>> handleBadgeNotFound(BadgeNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Badge not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(PageAlbumNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handlePageAlbumNotFound(PageAlbumNotFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Page album not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(ReviewerNotFound.class)
    public ResponseEntity<RestResponse<Object>> handlePageAlbumNotFound(ReviewerNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Reviewer not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    // --Handle with conflict
    @ExceptionHandler(LikeExist.class)
    public ResponseEntity<RestResponse<Object>> handleLikeExist(LikeExist ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Like already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(OwnerCanNotLikePost.class)
    public ResponseEntity<RestResponse<Object>> handleLikeExist(OwnerCanNotLikePost ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Owner can not like your post exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(OnwerCanNotActionOnTheirs.class)
    public ResponseEntity<RestResponse<Object>> handleLikeExist(OnwerCanNotActionOnTheirs ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Owner can not do anything on their post");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }


    @ExceptionHandler(NotSignReviewer.class)
    public ResponseEntity<RestResponse<Object>> hanldeNotSignReviewer(NotSignReviewer ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("User not signed reviewer");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(ExistFollow.class)
    public ResponseEntity<RestResponse<Object>> handleExistFollow(ExistFollow ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Follow already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(UserAlreadyhaveWallet.class)
    public ResponseEntity<RestResponse<Object>> handleUserAlreadyHaveWallet(UserAlreadyhaveWallet ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Wallet already exists for user");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(PricingRuleConflictException.class)
    public ResponseEntity<RestResponse<Object>> handlePricingRuleConflict(PricingRuleConflictException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setMessage(ex.getMessage());
        res.setErrors("Pricing rule conflict");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(NotificationNotFound.class)
    public ResponseEntity<RestResponse<Object>> handleGenericException(NotificationNotFound ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage("Notification not found error");
        res.setErrors(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGenericException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage("Internal server error");
        res.setErrors(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }


}
