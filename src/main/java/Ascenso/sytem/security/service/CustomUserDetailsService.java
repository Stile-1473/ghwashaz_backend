package Ascenso.sytem.security.service;


import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = repository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()-> new UsernameNotFoundException(
                        "Invalid phoneNumber and password"
                )
        );

        return new CustomUserDetails(user);
    }
}
