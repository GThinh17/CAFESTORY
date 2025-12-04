package vn.gt.__back_end_javaspring.exception;

public class UserAlreadyhaveWallet extends RuntimeException {
    public UserAlreadyhaveWallet(String message) {
        super(message);
    }
}
