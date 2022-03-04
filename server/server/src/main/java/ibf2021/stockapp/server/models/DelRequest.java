package ibf2021.stockapp.server.models;

public class DelRequest {
    String username;
    String ticker;
    String date_added;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
  
}
