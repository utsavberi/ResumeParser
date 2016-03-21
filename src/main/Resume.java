package main;
import java.time.LocalDate;
import java.util.ArrayList;



class Education{
	String college;
	DateRange dateRange;
	String stream;
}


public class Resume {
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public String getEducationString() {
		return educationString;
	}

	public void setEducationString(String educationString) {
		this.educationString = educationString;
	}

	public String getExperienceString() {
		return experienceString;
	}

	public void setExperienceString(String experienceString) {
		this.experienceString = experienceString;
	}

	public String getSkillsString() {
		return skillsString;
	}

	public void setSkillsString(String skillsString) {
		this.skillsString = skillsString;
	}

	public ArrayList<String> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}

	public ArrayList<Education> getEducation() {
		return education;
	}

	public void setEducation(ArrayList<Education> education) {
		this.education = education;
	}

	public ArrayList<Experience> getExperience() {
		return experience;
	}

	public void setExperience(ArrayList<Experience> experience) {
		this.experience = experience;
	}

	public ArrayList<String> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<String> projects) {
		this.projects = projects;
	}

	public ArrayList<String> getAchievements() {
		return achievements;
	}

	public void setAchievements(ArrayList<String> achievements) {
		this.achievements = achievements;
	}

	private String name;
	private String address="";
	private String email;
	private String phone;
	private String links;
	private String educationString="";
	private String experienceString="";
	private String skillsString="";
	private ArrayList<String> skills = new ArrayList<String>();
	private ArrayList<Education> education = new ArrayList<Education>();
	private ArrayList<Experience> experience = new ArrayList<>();
	private ArrayList<String> projects = new ArrayList<String>();
	private ArrayList<String> achievements = new ArrayList<>();
	
	@Override
	public String toString(){
		return "{ Name : "+name+" , address : "+address+" , email : "+email
				+" , phone : "+phone+" , links : "+links
				+" , educationStr : <<"+educationString
				+">> experienceStr : <<"+experienceString
				+" experiences : "+experience
				+">> , skillsStr : <<"+skillsString
				+">>"
				+"}";
	}
}
