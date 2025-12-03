package vn.gt.__back_end_javaspring.exception;

public class WalletTransactionNotFound extends RuntimeException {
    public WalletTransactionNotFound(String message) {
        super(message);
    }
}
