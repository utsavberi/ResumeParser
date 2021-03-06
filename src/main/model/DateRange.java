package main.model;

import java.time.LocalDate;

public class DateRange{
	LocalDate dateStart;
	LocalDate dateEnd;
	public DateRange(LocalDate dateStart, LocalDate dateEnd){
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	@Override
	public String toString(){
		return dateStart+" - "+dateEnd;
	}
	
	@Override
	public boolean equals(Object other){
		
		DateRange that = (DateRange) other;
		if(that.dateStart == null && this.dateStart == null 
				&& this.dateEnd==null && that.dateEnd == null) return true;
		if(that.dateStart==null && this.dateStart == null ) 
			return this.dateEnd.equals(that.dateEnd);
		if(that.dateEnd==null && this.dateEnd == null ) 
			return this.dateStart.equals(that.dateStart);
		
		return (that.dateStart .equals(this.dateStart) && that.dateEnd.equals(this.dateEnd));
	}
	
	@Override
	public int hashCode(){
		return dateStart.hashCode() ^ dateEnd.hashCode();
	}
}