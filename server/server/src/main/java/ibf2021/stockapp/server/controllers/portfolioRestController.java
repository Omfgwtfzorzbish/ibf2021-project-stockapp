package ibf2021.stockapp.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.stockapp.server.models.DelRequest;
import ibf2021.stockapp.server.models.portfolioItem;
import ibf2021.stockapp.server.services.CandleServ;
import ibf2021.stockapp.server.services.RepoServ;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="/api")
public class portfolioRestController {
    private final Logger logger = LoggerFactory.getLogger(portfolioRestController.class);
    @Autowired
    private CandleServ candleSvc;

    @Autowired
    private RepoServ repoServ;

    @PostMapping(path="portfolio" ,consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(path="stock/ticklist/getport", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPortfolio(@RequestBody String payload){
        String username="";
        JsonReader r = Json.createReader(new ByteArrayInputStream(payload.getBytes()) );
            JsonObject o = r.readObject();
                try {username=o.getString("username");} catch (Exception e) {}

            JsonArrayBuilder ab =Json.createArrayBuilder();
            repoServ.getPortfolio(username).stream()
                .forEach(p -> ab.add(p.toJson()));
                String p =ab.build().toString();
        System.out.println("FROM POSTMAP GET PORT>> "+p);
        return ResponseEntity.ok(p);
    }

    @PostMapping(path="stock/ticklist/delete", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delStock(@RequestBody String payload){
        DelRequest delStock =new DelRequest();
        JsonReader r = Json.createReader(new ByteArrayInputStream(payload.getBytes()) );
            JsonObject o = r.readObject();
                try {delStock.setUsername(o.getString("username"));
                    delStock.setTicker(o.getString("ticker"));
                    System.out.println("FRom delSTOCK " + delStock.getTicker() + delStock.getUsername());
            } catch (Exception e) {e.printStackTrace();}
            String s = repoServ.delStock(delStock);
        return ResponseEntity.ok(Json.createObjectBuilder().add("resp", s).build().toString());
    }
}
