package main;
public class Experience{
	private String company;
	private DateRange dateRange;
	private String description;
	public Experience(String company, String description, DateRange dateRange){
		this.company = company;
		this.dateRange = dateRange;
		this.description = description;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	
	public String getCompany() {
		return company;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override 
	public String toString(){
		return "{ date range "+dateRange+" , company :"+company
				+" description : "+description
				+"}";
	}
	
}

