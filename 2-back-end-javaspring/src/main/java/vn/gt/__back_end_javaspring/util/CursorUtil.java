package vn.gt.__back_end_javaspring.util;

import com.nimbusds.jose.util.Pair;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

public class CursorUtil {
    public static String encode(LocalDateTime createdAt, String id){
        var raw = createdAt.toString() + "|" + id;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
        //Result: "MjAyNS0xMC0xMVQxNTo1OTowMHxiY2QxMjM"
    }

    public static Pair<LocalDateTime, String> decode(String cursor){
        var raw = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
        var parts = raw.split("\\|", 2);
        return Pair.of(LocalDateTime.parse(parts[0]), parts[1]);

    }
}
