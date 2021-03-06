package ibf2021.stockapp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.stockapp.server.services.CandleServ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="/api/stock", produces=MediaType.APPLICATION_JSON_VALUE)
public class stockRestController {
  
    private final Logger logger = LoggerFactory.getLogger(stockRestController.class);
    @Autowired
    public CandleServ candleServ;

    @GetMapping(path="{ticker}")
    public ResponseEntity<String> getCandles(@PathVariable String ticker){
        
        logger.info("PATH VARIABLE" + ticker);
        return candleServ.getCandles(ticker, "D");
    }

}

