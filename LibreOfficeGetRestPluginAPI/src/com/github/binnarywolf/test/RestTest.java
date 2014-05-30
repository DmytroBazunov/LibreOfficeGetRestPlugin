package com.github.binnarywolf.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.Request;

import com.github.binnarywolf.rest.RestClient;

public class RestTest {

	@Test
	public void getRequestTest() {
		RestClient client = new RestClient();
		String url = "https://live.reveredata.com/api/v0_1/company/revid/777/geort/fy2012.json?regions=Americas,Europe,Asia,China";
		String expectedResponse = "[{\"requestedIdentifier\":\"777\",\"requestedIdentifierType\":\"REVID\",\"requestedDate\":\"2014-05-30\",\"companyName\":\"Apple Inc\",\"companyTicker\":\"AAPL\",\"companyRevereId\":\"777\",\"periodStart\":\"2011-09-30\",\"periodEnd\":\"2012-09-29\",\"region\":\"Europe\",\"estimatedPercent\":19.656862459382253,\"estimatedAmount\":3.0764562297929977E10,\"confidence\":0.9002921987357246},{\"requestedIdentifier\":\"777\",\"requestedIdentifierType\":\"REVID\",\"requestedDate\":\"2014-05-30\",\"companyName\":\"Apple Inc\",\"companyTicker\":\"AAPL\",\"companyRevereId\":\"777\",\"periodStart\":\"2011-09-30\",\"periodEnd\":\"2012-09-29\",\"region\":\"Americas\",\"estimatedPercent\":46.43808654415262,\"estimatedAmount\":7.267932048852238E10,\"confidence\":0.977866925720263},{\"requestedIdentifier\":\"777\",\"requestedIdentifierType\":\"REVID\",\"requestedDate\":\"2014-05-30\",\"companyName\":\"Apple Inc\",\"companyTicker\":\"AAPL\",\"companyRevereId\":\"777\",\"periodStart\":\"2011-09-30\",\"periodEnd\":\"2012-09-29\",\"region\":\"China\",\"estimatedPercent\":14.04230209882504,\"estimatedAmount\":2.1977326168829094E10,\"confidence\":0.98681127},{\"requestedIdentifier\":\"777\",\"requestedIdentifierType\":\"REVID\",\"requestedDate\":\"2014-05-30\",\"companyName\":\"Apple Inc\",\"companyTicker\":\"AAPL\",\"companyRevereId\":\"777\",\"periodStart\":\"2011-09-30\",\"periodEnd\":\"2012-09-29\",\"region\":\"Asia\",\"estimatedPercent\":24.789481072155393,\"estimatedAmount\":3.8797521036408966E10,\"confidence\":0.9718888175064978}]";
		String response = client.get(url);
		assertEquals(expectedResponse, response);
	}
}
