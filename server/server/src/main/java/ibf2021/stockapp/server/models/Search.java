package ibf2021.stockapp.server.models;

import jakarta.json.JsonObject;

public class Search {
    // Integer count;
    // String result;
    String description;
    String displaySymbol;
    String symbol;
    String type;
    // public Integer getCount() {
    //     return count;
    // }
    // public void setCount(Integer count) {
    //     this.count = count;
    // }
    // public String getResult() {
    //     return result;
    // }
    // public void setResult(String result) {
    //     this.result = result;
    // }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDisplaySymbol() {
        return displaySymbol;
    }
    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public static Search popSearchData(JsonObject o){
        Search s = new Search();
        s.setDescription(o.getString("description"));
        s.setDisplaySymbol(o.getString("displaySymbol"));
        s.setSymbol(o.getString("symbol"));
        s.setType(o.getString("type"));
        return s;
    }
}
