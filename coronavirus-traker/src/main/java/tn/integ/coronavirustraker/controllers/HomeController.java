package tn.integ.coronavirustraker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tn.integ.coronavirustraker.models.LocationState;
import tn.integ.coronavirustraker.service.CoronaService;

@Controller
public class HomeController {
	
	@Autowired
	CoronaService coronaService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationState> allState = coronaService.getAllState();
		model.addAttribute("locationStates",allState);
		
		int totalReportedCases = allState.stream().mapToInt(loc -> loc.getLatestTotalCases()).sum();
		int totalNewCases = allState.stream().mapToInt(loc -> loc.getNewCases()).sum();
		
		
		model.addAttribute("totalReportedCases",totalReportedCases);
		model.addAttribute("totalNewCases",totalNewCases);
		
		return "home";
	}
}
