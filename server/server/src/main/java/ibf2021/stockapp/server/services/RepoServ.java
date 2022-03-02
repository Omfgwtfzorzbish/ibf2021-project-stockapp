package ibf2021.stockapp.server.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ibf2021.stockapp.server.models.portfolioItem;
import ibf2021.stockapp.server.repositories.PortfolioRepo;


@Service
public class RepoServ {
    

    @Autowired
    private PortfolioRepo portRepo;

    public String insertStock(portfolioItem item){
           return portRepo.insertPortfolioItem(item);
        
    }
}
