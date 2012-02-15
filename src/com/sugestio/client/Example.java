package com.sugestio.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sugestio.client.model.Consumption;
import com.sugestio.client.model.Recommendation;

public class Example {

	private SugestioClient client;
	private String account = "sandbox";
	private String secret = "demo"; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Example example = new Example();		
		//example.getRecommendations();
		//example.addConsumption();
		example.addConsumptionsBulk();
		example.shutdown();
	}
	
	public Example() {
		this.client = new SugestioClient(account, secret);
	}
	
	public void shutdown() {
		client.shutdown();
	}
	
	public void getRecommendations() {
		
		try {
			
			// User 1 has recommendations
			List<Recommendation> recs = client.getRecommendations("1");
			// User 6 has no recommendations and will raise a 404 Not Found
			//List<Recommendation> recs = client.getRecommendations("6");
			
			for (Recommendation rec : recs) {
				System.out.println(rec.toString());
			}
			
		} catch (SugestioException e) {
			e.getSugestioResult().printReport();
		}
	}
	
	public void addConsumption() {		
		
		// Create a consumption with all required attributes
		Consumption c = new Consumption("u1", "i1");
		// Create a consumption missing a required attribute to raise 400 Bad Request
		//Consumption c = new Consumption("u1", null);
		
		try {
			SugestioResult<String> result = client.addConsumption(c);			
			result.printReport();			
		} catch (SugestioException e) {
			e.getSugestioResult().printReport();
		}
	}
	
	public void addConsumptionsBulk() {		
		
		List<Consumption> consumptions = new ArrayList<Consumption>();
		consumptions.add(new Consumption("u1", "i1"));
		consumptions.add(new Consumption("u1", "i2"));
		consumptions.add(new Consumption("u2", "i1"));
		Map<List<Consumption>, SugestioResult<String>> results;
		results = client.addConsumptions(consumptions);
		
		for (SugestioResult<String> result : results.values()) {
			result.printReport();
		}
		
	}

}
