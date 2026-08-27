package com.edge_news.price_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.edge_news.price_service.client.TickerAPIClient;
import com.edge_news.price_service.entities.Price;

@SpringBootTest 
class PriceServiceApplicationTests {

		@Autowired
		private TickerAPIClient tickerAPIClient;

    @Test
    void testGetTickerPriceData() {

				System.out.println((System.getenv("FINNHUB_API_KEY")));
				assertNotNull(System.getenv("FINNHUB_API_KEY"), "FINNHUB_API_KEY is NULL");
        Price price = tickerAPIClient.getTickerPriceData("DIA");

        assert price != null;

        System.out.println("Ticker: " + price.getTicker());
        System.out.println("Price: " + price.getPrice());
        System.out.println("Time: " + price.getTime());
    }
}