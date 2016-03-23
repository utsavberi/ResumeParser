package model;
import java.util.ArrayList;

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

	public ArrayList<PeriodDescription> getEducation() {
		return education;
	}

	public void setEducation(ArrayList<PeriodDescription> education) {
		this.education = education;
	}

	public ArrayList<PeriodDescription> getExperience() {
		return experience;
	}

	public void setExperience(ArrayList<PeriodDescription> experience) {
		this.experience = experience;
	}

	public ArrayList<PeriodDescription> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<PeriodDescription> projects) {
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
	private String projectString ="";
	private ArrayList<String> skills = new ArrayList<String>();
	private ArrayList<PeriodDescription> education = new ArrayList<>();
	private ArrayList<PeriodDescription> experience = new ArrayList<>();
	private ArrayList<PeriodDescription> projects = new ArrayList<>();
	private ArrayList<String> achievements = new ArrayList<>();
	
	@Override
	public String toString(){
		return "{ Name : "+name+" , address : "+address+" , email : "+email
//				+" , phone : "+phone+" , links : "+links
//				+" , educationStr : <<"+educationString
////				+">> experienceStr : <<"+experienceString
//				+" experiences : "+experience
//				+" education : "+education
				+" projects : "+projects
				+" projectsStr : <<"+projectString
//				+">> , skillsStr : <<"+skillsString
				+">>"
				+"}";
	}

	public String getProjectString() {
		return projectString;
	}

	public void setProjectString(String projectString) {
		this.projectString = projectString;
	}
}
