package tn.integ.coronavirustraker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronavirusTrakerApplication {

	public static void main(String[] args) {  
		SpringApplication.run(CoronavirusTrakerApplication.class, args);
	}

}
