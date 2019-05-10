/*Program a calculator which performs various calculations based on the user input from the console. After performing the calculation, your calculator should print the result in the console.

Program behavior:
	The calculator will first ask the user which operation they want to perform, and the user will then enter the operation:
		+
		-
		/
		*
		Stretch Goal:
		%
		^
		log
		cos
		sin
		tan
		
	Next, the calculator will ask the user for the operands. If there is more than one operand for the operator, the calculator will prompt the user for the operands one at a time.
	
	After all the operands are entered, the calculator will print out the result of the calculation, and ask for the next operation
	
	If the user enters something invalid, the calculator will print out an error message and return to the "asking for operator" stage
	
	If the user enters q or Q, the program will exit
		
	Stretch goal: if the user enters "ans" for an operand, the calculator will use the value from the last valid calculation. If no calculations have been performed since starting the program, the calculator should print an error if the user attempts to use "ans" as an operand.
*/

import java.util.Scanner;

public class calculator {
	
	public static void main(String args[]) {
		
		//Choose operation
		System.out.println(" Enter an operation: (+, -, /, or *) ");
		Scanner calcInput = new Scanner(System.in);
		String operInput = calcInput.nextLine();
		
		//Enter first number
		System.out.println("Enter the first number:");
		String num1Input = calcInput.nextLine();
		
		//Enter second number
		System.out.println("Enter the second number:");
		String num2Input = calcInput.nextLine();
		
		String chosenOper;
		int answer = 0;
		
		if (operInput.equals("+")) {
		
			chosenOper = "+";
			answer = Integer.parseInt(num1Input) + Integer.parseInt(num2Input);
			
		}
		
		else if (operInput.equals("-")) {
		
			chosenOper = "-";
			
			answer = Integer.parseInt(num1Input) - Integer.parseInt(num2Input);
			
		}
		
		else if (operInput.equals("*")) {
			
			chosenOper = "*";
			answer = Integer.parseInt(num1Input) * Integer.parseInt(num2Input);
			
		}
		
		else if (operInput.equals("/")) {
		
			chosenOper = "/";
			answer = Integer.parseInt(num1Input) / Integer.parseInt(num2Input);
		
		}
		
		else {
			
			chosenOper = "Error";
			
		}
		
		System.out.println(num1Input + chosenOper + num2Input + "=" + answer);
		
	}
}
