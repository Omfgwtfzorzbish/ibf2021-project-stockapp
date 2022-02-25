package ibf2021.stockapp.server.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2021.stockapp.server.controllers.stockRestController;
import ibf2021.stockapp.server.models.candle;
import ibf2021.stockapp.server.models.Search;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CandleServ {
    public static final String FINNHUB_URL = "https://finnhub.io/api/v1";
	public static final String API_KEY_FINNHUB = "API_KEY_FINNHUB";

	private final Logger logger = LoggerFactory.getLogger(CandleServ.class);

	private final String key;
	private long period;
	private String tickListResult;
	private List<Search> searchList;
    public CandleServ() {
		key = System.getenv(API_KEY_FINNHUB); //logger.info(key);

		if (Objects.isNull(key))
			logger.info("%s key is not set".formatted(API_KEY_FINNHUB));
	}

	public ResponseEntity<String> getCandles(String ticker, String reso){
		
		switch (reso) {
			case "D":
				period = 1296000; //24*60*60*15
				break;
			case "60":
				period = 54000; //60*60*15
				break;
			default:
				period = 13500;//15*60*15
				break;
		}
		long unixNow = System.currentTimeMillis()/1000L;
		long unixIni = System.currentTimeMillis()/1000L - period;

		final String url = UriComponentsBuilder.fromUriString(FINNHUB_URL + "/stock/candle")
			.queryParam("symbol", ticker)
			.queryParam("resolution", reso)
			.queryParam("from", Long.toString(unixIni))
			.queryParam("to", Long.toString(unixNow))
			.queryParam("token", key)
			.toUriString();
		logger.info(url);
		final RequestEntity<Void> candleReq = RequestEntity.get(url).build();
		final RestTemplate template = new RestTemplate();
		final ResponseEntity<String> resp = template.exchange(candleReq, String.class); 
				
		candle c = candle.stringToJobjToCandle(resp.getBody());


		return ResponseEntity.status(HttpStatus.ACCEPTED).body(c.toAngular(c));

	}

	public ResponseEntity<String> getSearchResults(String search){
		
		final String url = UriComponentsBuilder.fromUriString(FINNHUB_URL + "/search")
			.queryParam("q", search)
			.queryParam("token", key)
			.toUriString();

			final RequestEntity<Void> searchRequest = RequestEntity.get(url).build();
			final RestTemplate template = new RestTemplate();
			final ResponseEntity<String> resp = template.exchange(searchRequest, String.class); //need to remove count and result from the response.

			
			try{InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
				final JsonReader reader = Json.createReader(is);
                final JsonObject result = reader.readObject();
                final JsonArray res = result.getJsonArray("result");
				//tickListResult=res.toString();
				searchList = res.stream()
					.map(v->(JsonObject)v).map(Search::popSearchData).collect(Collectors.toList());
					if(searchList.size()>5){
						for (int i = searchList.size() - 1; i > 4; --i)
  								searchList.remove(i);
					}
			}catch (Exception e) {
                logger.error("error candleserv");}

				JsonArrayBuilder ab = Json.createArrayBuilder();
				JsonObjectBuilder b = Json.createObjectBuilder();
					for(int i = 0; i<searchList.size() ;i++){
						b.add("description", searchList.get(i).getDescription());
						b.add("displaySymbol", searchList.get(i).getDisplaySymbol());
						b.add("symbol", searchList.get(i).getSymbol());
						b.add("type", searchList.get(i).getType());
						ab.add(b);
					}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ab.build().toString());
	}
	
}
