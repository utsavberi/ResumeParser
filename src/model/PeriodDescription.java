package model;

public class PeriodDescription{
	private String name;
	private DateRange dateRange;
	private String description;
	public PeriodDescription(String company, String description, DateRange dateRange){
		this.name = company;
		this.dateRange = dateRange;
		this.description = description;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override 
	public String toString(){
		return "{ date range "+dateRange+" \n, name :"+name
				+" , \n description : "+description
				+"}\n";
	}
	
}

