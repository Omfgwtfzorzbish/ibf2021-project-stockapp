package ibf2021.stockapp.server.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ibf2021.stockapp.server.models.Login;
import ibf2021.stockapp.server.repositories.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class LoginServ implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    private final Logger logger = LoggerFactory.getLogger(LoginServ.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login user = userRepo.findByUsername(username); 
            if(user==null){
                throw new UsernameNotFoundException("User: " + username + "not found.");
            }
            System.out.println(user.getUsername()); 
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }

    // public Integer valUsernameAndPassword(Login loginInfo){
    //     return userRepo.valUsernameAndPassword(loginInfo);
    // }

    public void registerUser(Login reg){
         userRepo.registerUser(reg);
    }
}
