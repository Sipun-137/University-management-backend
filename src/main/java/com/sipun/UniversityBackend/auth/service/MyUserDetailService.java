package com.sipun.UniversityBackend.auth.service;


import com.sipun.UniversityBackend.auth.repo.UserRepo;
import com.sipun.UniversityBackend.auth.model.User;
import com.sipun.UniversityBackend.auth.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= repo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        if (user==null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

}
