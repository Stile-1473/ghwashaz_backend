package Ascenso.sytem.security.util;

import Ascenso.sytem.security.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {


    private SecurityUtils(){}

    public static CustomUserDetails getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (CustomUserDetails) authentication.getPrincipal();

    }

    public static UUID getCurrentUserId(){
        return getCurrentUser()
                .getUser()
                .getId();
    }

    public static String getPhoneNumber(){
        return getCurrentUser()
                .getUsername();
    }


}
