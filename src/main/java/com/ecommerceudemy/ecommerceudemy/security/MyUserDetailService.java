package com.ecommerceudemy.ecommerceudemy.security;

import com.ecommerceudemy.ecommerceudemy.Repository.UserRepository;
import com.ecommerceudemy.ecommerceudemy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByEmail(username);

        if(user != null) throw new UsernameNotFoundException("User 404");

        return new UserPrincipal(user);
    }
}
