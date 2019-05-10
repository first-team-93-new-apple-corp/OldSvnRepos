import java.util.ArrayList;

public class Scenario {
	String output;
	ArrayList<String> options;
	ArrayList<Scenario> children;
	
	public Scenario(String output, ArrayList<String> options, ArrayList<Scenario> children) 
	{
		this.output = output;
		this.options = options;  
		this.children = children;  
	}
	
	public Scenario(String output) 
	{
		this.output = output;
		this.options = null;  
		this.children = null;  
	}
	
	public void printDescription()
	{
		System.out.println(this.output);
	}
	
	public void printOptions()
	{
		if(options == null)
		{
			return;
		}
		
		System.out.println();
		for (int i = 0; i < options.size(); ++i)
		{
			System.out.println(i + " - " + options.get(i));
		}
		System.out.println();
	}
	
	public boolean hasOptions()
	{	
		return (options != null);
	}
	
	public boolean hasChildren()
	{	
		return (children != null);
	}
	
	public boolean isUserChoiceValid(int userChoice)
	{
		if(!hasChildren() || !hasOptions())
		{
			return false;
		}
		
		if(userChoice >= children.size() || userChoice < 0) {
			return false;
		}
		
		return true;
	}
	
	public Scenario getNextScenario(int userChoice) {
		if(!isUserChoiceValid(userChoice)) {
			return null;
		}

		return children.get(userChoice);
	}
}
