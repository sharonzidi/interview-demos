package main.java.com.quickbase;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import main.java.com.quickbase.devint.ConcreteStatService;
import main.java.com.quickbase.devint.CountryDao;
import main.java.com.quickbase.devint.IStatService;

/**
 * The main method of the executable JAR generated from this repository. This is
 * to let you execute something from the command-line or IDE for the purposes of
 * demonstration, but you can choose to demonstrate in a different way (e.g. if
 * you're using a framework)
 */
public class Main {
	public static void main(String args[]) {
		System.out.println("Starting.");
		CountryDao countryDao = CountryDao.getInstance();
		try {
			// call GetCountryPopulations function to get country population from Database
			List<Pair<String, Integer>> countryPopulationFromDB = countryDao.getCountryPopulations();

			// initialize a ConcreteStatService instance
			// call GetCountryPopulations function to get country population from
			// ConcfreteStateService
			IStatService cs = new ConcreteStatService();
			List<Pair<String, Integer>> countryPopulationFromStatService = cs.GetCountryPopulations();

			// use a hashmap to store countries and their population
			HashMap<String, Integer> dedupCountryPop = new HashMap<>();
			// add each country and their population from StatService into the hashmap
			for (Pair<String, Integer> pair : countryPopulationFromStatService) {
				String name = pair.getLeft();
				Integer population = pair.getRight();
				dedupCountryPop.put(name, population);
			}

			// add each country and their population from Database into the hashmap
			for (Pair<String, Integer> pair : countryPopulationFromDB) {
				// If there is duplicate country name, the population value from DB table will
				// overwrite
				// the concreteStatService.
				String name = pair.getLeft();
				Integer population = pair.getRight();
				dedupCountryPop.put(name, population);
			}
			// iterate the keyset in the hashmap
			for (String countryName : dedupCountryPop.keySet()) {
				// use stringBuilder to create strings and store total population for each country
				StringBuilder sb = new StringBuilder();
				sb.append(countryName);
				sb.append(": ");
				sb.append(dedupCountryPop.get(countryName));
				System.out.println(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}