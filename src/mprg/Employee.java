package mprg;

public class Employee {
	
	private String name;
	private int totalSales;
	private int salesPeriod;
	private double experienceMultiplier;
	
	public Employee(String name, int totalSales, int salesPeriod, double experienceMultiplier) {
		this.name = name;
		this.totalSales = totalSales;
		this.salesPeriod = salesPeriod;
		this.experienceMultiplier = experienceMultiplier;
	}

	public String getName() {
		return name;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public int getSalesPeriod() {
		return salesPeriod;
	}

	public double getExperienceMultiplier() {
		return experienceMultiplier;
	}

}