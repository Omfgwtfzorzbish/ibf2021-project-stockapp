package ibf2021.stockapp.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ibf2021.stockapp.server.models.portfolioItem;
import ibf2021.stockapp.server.repositories.PortfolioRepo;
import ibf2021.stockapp.server.repositories.UserRepo;


@Service
public class RepoServ {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PortfolioRepo portRepo;

    public String insertStock(portfolioItem item){
           return portRepo.insertPortfolioItem(item);
    }

    public List<portfolioItem> getPortfolio(String username){
        return userRepo.getPortfolio(username);
    }
}
