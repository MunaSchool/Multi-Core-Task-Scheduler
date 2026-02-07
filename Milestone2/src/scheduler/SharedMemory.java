package scheduler;



	class SharedMemory {
	    private Map<String, Integer> memory;

	    public SharedMemory() {
	        this.memory = new HashMap<>();
	    }

	    public synchronized void assign(String variable, int value) {
	        memory.put(variable, value);
	    }

	    public synchronized Integer getValue(String variable) {
	        return memory.get(variable);
	    }

	    public synchronized void display() {
	        System.out.println("Memory State: " + memory);
	    }
	}
