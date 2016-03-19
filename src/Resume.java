import java.util.ArrayList;
import java.util.Calendar;

class DateRange{
	Calendar dateStart;
	Calendar dateEnd;
}

class Education{
	String college;
	DateRange dateRange;
	String stream;
}

class Experience{
	String company;
	DateRange dateRange;
}

public class Resume {
	String name;
	String address;
	String email;
	String phone;
	String links;
	ArrayList<String> skills = new ArrayList<String>();
	ArrayList<Education> education = new ArrayList<Education>();
	ArrayList<Experience> experiences = new ArrayList<>();
	ArrayList<String> projects = new ArrayList<String>();
	ArrayList<String> achievements = new ArrayList<>();
	
	@Override
	public String toString(){
		return "{ Name : "+name+" , address : "+address+" , email : "+email
				+" , phone : "+phone+" , links : "+links
				+"}";
	}
}
