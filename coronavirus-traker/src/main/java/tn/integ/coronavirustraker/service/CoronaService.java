package tn.integ.coronavirustraker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.integ.coronavirustraker.models.LocationState;

@Service
public class CoronaService {

	private static String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationState> allState= new ArrayList<LocationState>();
	
	
	
	public List<LocationState> getAllState() {
		return allState;
	}



	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchCoronaData() throws IOException, InterruptedException {
		List<LocationState> newState= new ArrayList<LocationState>();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		StringReader in = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationState locationState =  new LocationState();
			locationState.setState(record.get("Province/State"));
			locationState.setCountry(record.get("Country/Region"));
			locationState.setLatestTotalCases((Integer.parseInt(record.get(record.size()-1))));
			
			int newCases = Integer.parseInt(record.get(record.size()-1)) - Integer.parseInt(record.get(record.size()-2));
			locationState.setNewCases(newCases);
			
			//System.out.println(locationState.toString());
			newState.add(locationState);
		}
		newState = newState.stream().sorted((a,b)-> a.compareTo(b) ).collect(Collectors.toList());
		this.allState = newState;

	}
}
