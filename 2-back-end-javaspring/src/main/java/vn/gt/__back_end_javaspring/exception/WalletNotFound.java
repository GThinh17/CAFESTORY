package vn.gt.__back_end_javaspring.exception;

public class WalletNotFound extends RuntimeException {
    public WalletNotFound(String message) {
        super(message);
    }
}
