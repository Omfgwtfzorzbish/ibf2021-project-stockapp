package ibf2021.stockapp.server.models;

import java.io.ByteArrayInputStream;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class portfolioItem {
    private String ticker;
    private String username;
    private Integer position;
    private Float price;
    private Date date_added;
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Date getDate_added() {
        return date_added;
    }
    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    //username, ticker and date added are compulsory
   public static portfolioItem create(String jsonString){
        final portfolioItem p = new portfolioItem();
           JsonReader r = Json.createReader(new ByteArrayInputStream(jsonString.getBytes()) );
            JsonObject o = r.readObject();
                try {p.ticker=o.getString("ticker");
                Date date = new Date();
                p.date_added = date;
                p.username ="test_username";
                } catch (Exception e) {
                    
                }
           return p;
   }

}
