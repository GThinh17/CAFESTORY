package vn.gt.__back_end_javaspring.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component("auth")
public class AuthHelper {

    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return false;

        Collection<?> authorities = auth.getAuthorities();
        return authorities.stream()
                .anyMatch(a -> a.toString().equals("ROLE_" + role));
    }

    public boolean hasAnyRole(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return false;

        Collection<?> authorities = auth.getAuthorities();

        for (String r : roles) {
            if (authorities.stream().anyMatch(a -> a.toString().equals("ROLE_" + r))) {
                return true;
            }
        }
        return false;
    }
}
