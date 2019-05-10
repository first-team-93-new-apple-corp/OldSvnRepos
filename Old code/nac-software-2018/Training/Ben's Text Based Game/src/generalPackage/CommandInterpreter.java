package generalPackage;

import java.util.Dictionary;
import java.util.Hashtable;

import Commands.CommandInterface;

public class CommandInterpreter
{
	private Dictionary<String, CommandInterface> commandList;
	
	public CommandInterpreter()
	{
		commandList = new Hashtable<String, CommandInterface>();
	}
}
