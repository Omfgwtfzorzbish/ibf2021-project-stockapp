package ibf2021.stockapp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.stockapp.server.services.CandleServ;

@RestController
@RequestMapping(path="/api/search", produces=MediaType.APPLICATION_JSON_VALUE)
public class searchRestController {
    @Autowired
    public CandleServ candleServ;
    
    @GetMapping("ticklist")
    public ResponseEntity<String> searchTickers(@RequestParam(name = "q") String search){
        
        return candleServ.getSearchResults(search);}
}
