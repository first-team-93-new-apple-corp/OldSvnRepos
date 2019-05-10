/**
 * Student Class
 * 
 * @author mark.tervo
 */
public class Student implements Comparable<Student>{
	private String name;
	private float gpa;
	private int studentId;
	private String expGradDate;
	private boolean isMemberOfFirst;
	
	
	/**
	 * Student constructor
	 * 
	 * @param name Student name
	 * @param gpa Student GPA
	 * @param studentId Student ID
	 * @param expGradDate Student Expected Graduation Date
	 * @param isMemberOfFirst True if the Student is a member of FIRST
	 */
	public Student(String name, float gpa, int studentId, String expGradDate, boolean isMemberOfFirst) {
		this.name = name;
		this.gpa = gpa;
		this.studentId = studentId;
		this.expGradDate = expGradDate;
		this.isMemberOfFirst = isMemberOfFirst;
	}
	
	/**
	 * Gets student name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets student gpa
	 * 
	 * @return gpa
	 */
	public float getGpa() {
		return gpa;
	}
	
	/**
	 * Gets student's Student ID
	 * 
	 * @return studentId
	 */
	public int getStudentId() {
		return studentId;
	}
	
	/**
	 * Gets student expected graduation date
	 * 
	 * @return expGradDate
	 */
	public String getExpGradDate() {
		return expGradDate;
	}
	
	/**
	 * Returns true if the student is a member of FIRST
	 * 
	 * @return isMemberOfFirst
	 */
	public boolean isMemberOfFirst() {
		return isMemberOfFirst;
	}

	@Override
	/**
	 * Implements compareTo method from the Comparable interface
	 * 
	 * @param student
	 * @return
	 */
	public int compareTo(Student student) {
		return name.compareTo(student.getName());
	}
}
