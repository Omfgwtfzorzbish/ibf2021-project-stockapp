package ibf2021.stockapp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.stockapp.server.models.AuthRequest;
import ibf2021.stockapp.server.models.Login;
import ibf2021.stockapp.server.services.LoginServ;
import ibf2021.stockapp.server.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping()
public class loginRestController {
    
    private final Logger logger = LoggerFactory.getLogger(loginRestController.class);

    @Autowired
    private LoginServ loginServ;

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping(path="/api/login" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserLoginInput(@RequestBody AuthRequest authRequest) throws Exception{

        // Login login= new Login(); login.setPassword("root"); login.setEmail("email");login.setUsername("brian");
        // int i = loginServ.valUsernameAndPassword(login); System.out.println("VALIDATE:>> " + Integer.toString(i));
        try{
            System.out.println(authRequest.getPassword()+ authRequest.getUsername());
        //only generates a token if password and username are correct
        auth.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); }catch(Exception ex){
          ex.printStackTrace();
            throw new UsernameNotFoundException("Invalid Username or Password"); 
        }
        String token = jwtUtil.generateToken(authRequest.getUsername());
       logger.info(token);

        JsonObject o = Json.createObjectBuilder().add("token", token).build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(o.toString());
            
       
    }
    @PostMapping(path="/api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody String payload){
        Login reg = null;   
        try {
            reg = Login.create(payload);   //create registration item
            logger.info(payload);
        }catch(Exception e){
            JsonObject error = Json.createObjectBuilder()
                .add("error", e.getMessage())
                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
        }
        loginServ.registerUser(reg);
        JsonObject resp =Json.createObjectBuilder().add("resp","response").build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp.toString());
    }
    
}
