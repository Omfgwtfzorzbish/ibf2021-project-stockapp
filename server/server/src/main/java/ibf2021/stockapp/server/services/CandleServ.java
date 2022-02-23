package ibf2021.stockapp.server.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2021.stockapp.server.models.candle;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CandleServ {
    public static final String FINNHUB_URL = "https://finnhub.io/api/v1";
	public static final String API_KEY_FINNHUB = "API_KEY_FINNHUB";

	private final Logger logger = LoggerFactory.getLogger(CandleServ.class);

	private final String key;
	private long period;

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

		final RequestEntity<Void> candleReq = RequestEntity.get(url).build();
		final RestTemplate template = new RestTemplate();
		final ResponseEntity<String> resp = template.exchange(candleReq, String.class); 
				
		candle c = candle.stringToJobjToCandle(resp.getBody());

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(c.toAngular(c));

	}

	
}
