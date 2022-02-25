package ibf2021.stockapp.server.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;



import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

import jakarta.json.JsonArrayBuilder;



public class candle {
    String[] c;
    String[] h;
    String[] l;
    String[] o;
    String[] t;
    String[] v;
    String s;

    public String[] getC() {
        return this.c;
    }

    public void setC(String[] c) {
        this.c = c;
    }

    public String[] getH() {
        return this.h;
    }

    public void setH(String[] h) {
        this.h = h;
    }

    public String[] getL() {
        return this.l;
    }

    public void setL(String[] l) {
        this.l = l;
    }

    public String[] getO() {
        return this.o;
    }

    public void setO(String[] o) {
        this.o = o;
    }

    public String[] getT() {
        return this.t;
    }

    public void setT(String[] t) {
       
            for(int i = 0; i< t.length; i++){
              Date d =  new Date(Long.valueOf(t[i])*1000);
              DateFormat dF = new SimpleDateFormat("MM/dd/yyyy hh:mm: a");
              t[i] = dF.format(d); 
            }
        this.t = t;
    }

    public String[] getV() {
        return this.v;
    }

    public void setV(String[] v) {
        this.v = v;
    }

    public String getS() {
        return this.s;
    }

    public void setS(String s) {
        this.s = s;
    }

    // @Override
    // public String toString(){
    //     //check values are stored in candle.java (is ok)
    //     return "c:%s, h:%s, l:%s, o:%s, t:%s, v:%s, s:%s".formatted(Arrays.toString(c),Arrays.toString(h),Arrays.toString(l),Arrays.toString(o),Arrays.toString(t),Arrays.toString(v),s);
    // }
    public static candle stringToJobjToCandle(String s){
        try(InputStream is = new ByteArrayInputStream(s.getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            return jsonToCandle(o);

        }catch(Exception ex){
            ex.printStackTrace();
            return null; 
        }
    }

    public static candle jsonToCandle(JsonObject o){
      
        candle c = new candle();
            // List<String> listC = o.getJsonArray("c").stream().map(v->v.toString()).collect(Collectors.toList());
            // c.setC(listC.toArray(new String[0]));

            c.setC(o.getJsonArray("c").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
            c.setH(o.getJsonArray("h").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
            c.setL(o.getJsonArray("l").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
            c.setO(o.getJsonArray("o").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
            c.setT(o.getJsonArray("t").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
            c.setV(o.getJsonArray("v").stream().map(v->v.toString()).collect(Collectors.toList()).toArray(new String[0]));
        
            c.setS(o.getString("s"));
        return c;
    }
    //to angular in following format   series: [{
                                        //   data: [
                                        //     [1538856000000, O, H, L, C],     only return this
                                        //     [1538856900000, 6595.16, 6604.76, 6590.73, 6593.86]
                                        //   ]
                                        //   }]
    public String toAngular(candle c){
    //     int numOfCandles = c.getT().length;    //FOR APEX?
       
    //     JsonObjectBuilder candles=Json.createObjectBuilder();
    //     JsonArrayBuilder singleCandle= Json.createArrayBuilder();
    //              for(int i = 0; i<numOfCandles; i++){
    //                     singleCandle.add(c.getO()[i]).add(c.getH()[i]).add(c.getL()[i]).add(c.getC()[i]);
    //                     candles.add("time"+Integer.toString(i), c.getT()[i]).add("candle"+Integer.toString(i), singleCandle);
    //                  }   

        
        //String singleCandle;
            int numOfCandles = c.getT().length;
            //String[] data = new String[numOfCandles];

            JsonObjectBuilder candles=Json.createObjectBuilder();
            JsonArrayBuilder time = Json.createArrayBuilder();
            JsonArrayBuilder open = Json.createArrayBuilder();
            JsonArrayBuilder high = Json.createArrayBuilder();
            JsonArrayBuilder low = Json.createArrayBuilder();
            JsonArrayBuilder close = Json.createArrayBuilder();

            for(int i = 0; i<numOfCandles; i++){
                // singleCandle = "['%s', %s, %s, %s, %s]".formatted(c.getT()[i], c.getO()[i], c.getH()[i], c.getL()[i], c.getC()[i]);
                // candles.add("candle"+Integer.toString(i), singleCandle);
                time.add(c.getT()[i]);
                open.add(c.getO()[i]);
                high.add(c.getH()[i]);
                low.add(c.getL()[i]);
                close.add(c.getC()[i]);
            }
            candles.add("t", time).add("o", open).add("h", high).add("l", low).add("c", close);

        return candles.build().toString();

    }
}