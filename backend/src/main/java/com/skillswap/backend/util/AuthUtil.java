package com.skillswap.backend.util;

import com.skillswap.backend.security.userdetail.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Long getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getUser().getId();
    }
}
