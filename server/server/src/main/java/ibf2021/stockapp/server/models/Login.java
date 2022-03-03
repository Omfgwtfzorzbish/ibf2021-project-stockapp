package ibf2021.stockapp.server.models;

import java.io.ByteArrayInputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Login {
    String username;
    String password;
    String email;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Login [email=" + email + ", password=" + password + ", username=" + username + "]";
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
 
    public static Login create(String jsonString){
        final Login l = new Login();
           JsonReader r = Json.createReader(new ByteArrayInputStream(jsonString.getBytes()) );
            JsonObject o = r.readObject();
                try {l.username=o.getString("username");
                l.password = o.getString("password");
                l.email =o.getString("email");
                } catch (Exception e) {    
                }
           return l;}

}
