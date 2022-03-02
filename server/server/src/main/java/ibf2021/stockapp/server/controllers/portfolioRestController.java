package ibf2021.stockapp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.stockapp.server.models.portfolioItem;
import ibf2021.stockapp.server.services.CandleServ;
import ibf2021.stockapp.server.services.RepoServ;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="/api/portfolio")
public class portfolioRestController {
    private final Logger logger = LoggerFactory.getLogger(portfolioRestController.class);
    @Autowired
    private CandleServ candleSvc;

    @Autowired
    private RepoServ repoServ;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTicker(@RequestBody String payload){
        portfolioItem addstock = null;
        try {
            addstock = portfolioItem.create(payload);   //contains ticker date, no username
        }catch(Exception e){
            JsonObject error = Json.createObjectBuilder()
                .add("error", e.getMessage())
                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
        }
        String s = repoServ.insertStock(addstock);
        JsonObject resp =Json.createObjectBuilder().add("date_added",s).build();

        logger.info("PAYLOAD >> ",payload);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp.toString());
    }
}
