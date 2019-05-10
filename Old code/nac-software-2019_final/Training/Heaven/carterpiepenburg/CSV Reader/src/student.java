public class student {

	public String name;
	public String GPA;
	public String studentID;
	public String graduationDate;
	public String firstMember;
	
	student(String consname, String consGPA, String consstudentID, String consgraduationDate, String consfirstMember){
		
		this.name = consname;
		this.GPA = consGPA;
		this.studentID = consstudentID;
		this.graduationDate = consgraduationDate;
		this.firstMember = consfirstMember;
		
	}
	
	public String toString() {
		
		String compiledString = ("Name: " + this.name + ", GPA: " + this.GPA + ", ID: " + this.studentID + ", Grad Date: " + this.graduationDate + ", First Member: " + this.firstMember);
		
		return compiledString;
		
	}
	
}
