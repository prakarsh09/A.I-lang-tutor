package com.creator.console.Creator.Console.JwtConfig;


import com.creator.console.Creator.Console.User;
import com.creator.console.Creator.Console.UserRepo;
import com.creator.console.Creator.Console.JwtConfig.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
       doAuthenticate(email, password);
        // Load user details
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

        // Generate JWT token
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Return the token
        return ResponseEntity.ok("Bearer " + jwt);
    }

    
private void doAuthenticate(String email, String password){
    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email, password);
    try{
       authenticationManager.authenticate(authentication);
    }catch(BadCredentialsException e){
        throw new RuntimeException("Invalid Username or password");
    }
}


@PostMapping("/regis")
public String register(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
    User user=new User();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setName(name);
    userRepo.save(user);
    return "success";
}

}
