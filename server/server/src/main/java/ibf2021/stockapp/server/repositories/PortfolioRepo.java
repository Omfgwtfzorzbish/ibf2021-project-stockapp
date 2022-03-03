package ibf2021.stockapp.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2021.stockapp.server.models.portfolioItem;

import static ibf2021.stockapp.server.repositories.SQLs.*;


@Repository
public class PortfolioRepo {
    
    @Autowired
    private JdbcTemplate template;

    public String insertPortfolioItem(portfolioItem item){
        System.out.println("FROM INSER PORT "+ item.getUsername());
        template.update(SQL_ADD_PORFOLIOITEM, item.getUsername(),item.getTicker(),item.getDate_added());
       
        return item.getDate_added().toString();
    }
}
