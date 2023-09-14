package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = userRepository.findByEmail(username);
            System.out.println(user);

            if(user == null)
//             throw new UsernameNotFoundException("User Not Found ! Register Yourself first :" + username);
                throw new RuntimeException("User not found");

            List<GrantedAuthority> authorities = new ArrayList<>();
            return  new org.springframework.security.core.userdetails.User(user.getEmail() , user.getPassword() , authorities );
        }

        catch (Error e){
            e.printStackTrace();
        }

        return  null;




    }
}
