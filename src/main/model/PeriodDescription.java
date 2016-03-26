package main.model;

public class PeriodDescription{
	private DateRange dateRange;
	private String description;
	public PeriodDescription( String description, DateRange dateRange){
		this.dateRange = dateRange;
		this.description = description;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	
	
	
	public String getDescription() {
		return description;
	}
	
	@Override 
	public String toString(){
		return "{ date range "+dateRange
				+" , \n description : "+description
				+"}\n";
	}
	
}

