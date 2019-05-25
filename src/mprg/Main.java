package mprg;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		String pathToData = "", pathToDefinition = "";
		File fileData = null, fileDefinition = null;
		
		while(fileData == null) {
			System.out.print("Path to JSON data file: ");
			pathToData = scanner.nextLine();
			fileData = new File(pathToData);
			if(!fileData.exists()) {
				System.out.println("The file specified doesn't exist!");
				fileData = null;
			}
		}
		
		while(fileDefinition == null) {
			System.out.print("Path to JSON report definition file: ");
			pathToDefinition = scanner.nextLine();
			fileDefinition = new File(pathToDefinition);
			if(!fileDefinition.exists()) {
				System.out.println("The file specified doesn't exist!");
				fileDefinition = null;
			}
		}
		
		scanner.close();
		
		ArrayList<Employee> employees = new ArrayList<>();
		
		try {
	        JSONParser parser = new JSONParser();
	        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(fileData));

	        for (Object o : jsonArray) {
	            JSONObject empl = (JSONObject) o;

	            String name = (String) empl.get("name");
	            int totalSales = (int) (long) empl.get("totalSales");
	            int salesPeriod = (int) (long) empl.get("salesPeriod");
	            double experienceMultiplier = (double) empl.get("experienceMultiplier");
	            
	            employees.add(new Employee(name, totalSales, salesPeriod, experienceMultiplier));
	        }
	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
		
		ReportDefinition reportDefinition = null;
		
		try {
	        JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(fileDefinition));

	        int topPerformersThreshold = (int) (long) jsonObject.get("topPerformersThreshold");
	        boolean useExperienceMultiplier = (boolean) jsonObject.get("useExperienceMultiplier");
	        int periodLimit = (int) (long) jsonObject.get("periodLimit");
	        reportDefinition = new ReportDefinition(topPerformersThreshold, useExperienceMultiplier, periodLimit);
	        
	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
		
		
		if(employees.size() != 0 && reportDefinition != null) {
			ArrayList<SingleResult> results = new ArrayList<>();
			
			for(Employee e: employees) {
				if(e.getSalesPeriod() <= reportDefinition.getPeriodLimit()) {
					if(reportDefinition.isUseExperienceMultiplier()) {
						double score = e.getTotalSales()/e.getSalesPeriod()*e.getExperienceMultiplier();
						results.add(new SingleResult(e.getName(), score));
					} else {
						double score = e.getTotalSales()/e.getSalesPeriod();
						results.add(new SingleResult(e.getName(), score));
					}
				}
			}
			
			final int n = results.size();
			
			for(int i=0; i < n; i++) {
				for(int j=1; j < (n-i); j++) {
					if( results.get(j-1).getScore() < results.get(j).getScore() ) {
						Collections.swap(results, j-1, j);
					}
				}
			}
			
			int iterations = reportDefinition.getTopPerformersThreshold();
			if(n < reportDefinition.getTopPerformersThreshold()) iterations = n;
			
			FileWriter csvWriter;
			try {
				csvWriter = new FileWriter("report_results.csv");
				csvWriter.append("Name");
				csvWriter.append(",");
				csvWriter.append("Score");
				csvWriter.append("\n");
				
				for(int i=0; i<iterations; i++) {
		        	//System.out.println(results.get(i).getName() + ", " + results.get(i).getScore());
					csvWriter.append(results.get(i).getName());
					csvWriter.append(',');
					csvWriter.append(results.get(i).getScore()+"");
					csvWriter.append("\n");
		        }
				
				csvWriter.flush(); 
				csvWriter.close(); 
				System.out.println("Report results generated.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
			
		}
		
	}

}