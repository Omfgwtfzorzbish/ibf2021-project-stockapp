package ibf2021.stockapp.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2021.stockapp.server.models.Login;

import static ibf2021.stockapp.server.repositories.SQLs.*;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate template;

    //1 username does not exist //2 username password is valid //3 password invalid
    public Integer valUsernameAndPassword(Login loginInfo){
            final SqlRowSet rs = template.queryForRowSet(SQL_CHECK_USERNAME_EXIST,loginInfo.getUsername());
            final SqlRowSet rs2 = template.queryForRowSet(SQL_CHECK_PASSWORD_OK,loginInfo.getPassword());
            if(rs.next()) //rs.next() moves the cursor.
                if( rs.getInt("username_valid")>0)
                    if(rs2.next())
                        if(rs2.getInt("password_valid")>0)
                            {return 2;} else {return 3;}
        return 1;

    }
    //for spring security, find username
    public Login findByUsername(String username){
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_USERNAME_RECORD,username);
        if(rs.next())
            {Login user = new Login(); 
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                System.out.println("fr SQLSec findbyUsername: "+ user.getPassword()+user.getUsername());
                return user;}
        return null;
    }
    
    public void registerUser(Login reg){
        template.update(SQL_REGISTER_USER, reg.getUsername(),reg.getPassword(),reg.getEmail());
    }

}
