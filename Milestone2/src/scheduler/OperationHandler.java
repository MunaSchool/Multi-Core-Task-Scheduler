package scheduler;

public class OperationHandler {
			public void print(String variable) {
				Integer value = memory.getValue(variable);
				if (value != null) {
					System.out.println(variable + " = " + value);
				} else {
					System.out.println(variable + " is not assigned.");
				}
			}
	    private SharedMemory memory;

	    public OperationHandler(SharedMemory memory) {
	        this.memory = memory;
	    }

	    public void assign(String variable, int value) {
	        memory.assign(variable, value);
	    }

	    public void performArithmetic(String operation, String var1, String var2, String resultVar) {
			int value1 = memory.getValue(var1);
			int value2 = memory.getValue(var2);
			int result = 0;
			switch (operation) {
				case "ADD":
					result = value1 + value2;
					break;
				case "SUB":
					result = value1 - value2;
					break;
				case "MUL":
					result = value1 * value2;
					break;
				case "DIV":
					if (value2 != 0) {
						result = value1 / value2;
					} else {
						System.out.println("Division by zero error.");
						return;
					}
					break;
				default:
					System.out.println("Unknown arithmetic operation: " + operation);
					return;
			}
			memory.assign(resultVar, result);
			System.out.println(resultVar + " = " + result);
}
}