package ibf2021.stockapp.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2021.stockapp.server.models.Login;
import ibf2021.stockapp.server.models.portfolioItem;

import static ibf2021.stockapp.server.repositories.SQLs.*;

import java.util.ArrayList;
import java.util.List;

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

    public List<portfolioItem> getPortfolio(String username){
        System.out.println("FROM USERREPO USERNAE>>" + username);
            final SqlRowSet rs = template.queryForRowSet(SQL_GET_PORTFOLIO_BY_USERNAME,username);
           List<portfolioItem> portfolio = new ArrayList<portfolioItem>();
                while(rs.next())
                    {portfolioItem item = new portfolioItem();
                        item.setUsername(rs.getString("username"));
                        item.setTicker(rs.getString("ticker"));
                        //item.setPrice(rs.getFloat("price"));
                        //item.setPosition(rs.getInt("positon"));
                        item.setDate_added(rs.getDate("date_added"));
                        portfolio.add(item);
                    }
            return portfolio;
    }
}
