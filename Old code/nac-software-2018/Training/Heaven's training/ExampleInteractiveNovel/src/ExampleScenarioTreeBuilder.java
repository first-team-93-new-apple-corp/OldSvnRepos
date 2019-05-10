import java.util.*;
public class ExampleScenarioTreeBuilder implements ScenarioTreeBuilder{
	public Scenario buildTree()
	{
		Scenario end0 = new Scenario("Ending 0");
		Scenario end1 = new Scenario("Ending 1");
		Scenario end2 = new Scenario("Ending 2");
		Scenario end3 = new Scenario("Ending 3");
		Scenario end4 = new Scenario("Ending 4");
		Scenario end5 = new Scenario("Ending 5");
		Scenario end6 = new Scenario("Ending 6");
		Scenario end7 = new Scenario("Ending 7");
		
		ArrayList<Scenario> children = new ArrayList<Scenario>();
		children.add(end6);
		children.add(end7);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario6 = new Scenario("Scenario6", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end4);
		children.add(end5);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario5 = new Scenario("Scenario5", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end2);
		children.add(end3);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario4 = new Scenario("Scenario4", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end0);
		children.add(end1);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario3 = new Scenario("Scenario3", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(scenario5);
		children.add(scenario6);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario2 = new Scenario("Scenario2", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(scenario3);
		children.add(scenario4);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario1 = new Scenario("Scenario1", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(scenario1);
		children.add(scenario2);
		options = new ArrayList<String>();
		options.add("Option 0");
		options.add("Option 1");
		
		Scenario scenario0 = new Scenario("Scenario0", options, children);
		
		return scenario0;
	}
}
