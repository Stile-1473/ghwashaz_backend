package Ascenso.sytem.security.service;

import Ascenso.sytem.user.entity.Permission;
import Ascenso.sytem.user.entity.Roles;
import Ascenso.sytem.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        Set<GrantedAuthority> authorities = new HashSet<>();

        for(Roles role : user.getRoles()){
            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + role.getName()
                    )
            );

            for(Permission permission : role.getPermissions()){

                authorities.add(
                        new SimpleGrantedAuthority(
                                permission.getName()
                        )
                );

            }
        }
        return authorities;
    }

    @Override
    public String getPassword(){
        return user.getPasswordHash();
    }

    @Override
    public String getUsername(){
        return user.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;

    }

    @Override
    public boolean isAccountNonLocked() {

        return !user.getLocked();

    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;

    }

    @Override
    public boolean isEnabled() {

        return user.getEnabled();

    }

}
