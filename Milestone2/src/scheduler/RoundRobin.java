class RoundRobinScheduler {
    private ReadyQueue readyQueue;
    private SharedMemory memory;
    private OperationHandler operationHandler;
    private int quantum;

    public RoundRobinScheduler(ReadyQueue readyQueue, SharedMemory memory, int quantum) {
        this.readyQueue = readyQueue;
        this.memory = memory;
        this.operationHandler = new OperationHandler(memory);
        this.quantum = quantum;
    }

    public void schedule() {
        SlaveCore[] cores = new SlaveCore[2];
        for (int i = 0; i < 2; i++) {
            cores[i] = new SlaveCore("Core" + (i + 1), memory, operationHandler, quantum);
        }

        int cycle = 0;
        while (!readyQueue.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                if (!readyQueue.isEmpty()) {
                    ProcessControlBlock pcb = readyQueue.dequeue();
                    cores[i].assignProcess(pcb);
                } else {
                    cores[i].assignProcess(null);
                }
            }

            // Start slave cores
            for (SlaveCore core : cores) {
                core.start();
            }
            // Wait for slave cores to finish
            for (SlaveCore core : cores) {
                try {
                    core.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Re-enqueue unfinished processes
            for (SlaveCore core : cores) {
                ProcessControlBlock pcb = core.getAssignedProcess();
                if (pcb != null && pcb.hasMoreInstructions()) {
                    readyQueue.enqueue(pcb);
                }
            }

            System.out.println("Cycle " + (++cycle) + ":");
            readyQueue.display();
            memory.display();
        }
    }
    // Slave core thread
    private static class SlaveCore extends Thread {
        private String coreName;
        private SharedMemory memory;
        private OperationHandler operationHandler;
        private int quantum;
        private ProcessControlBlock pcb;

        public SlaveCore(String coreName, SharedMemory memory, OperationHandler operationHandler, int quantum) {
            this.coreName = coreName;
            this.memory = memory;
            this.operationHandler = operationHandler;
            this.quantum = quantum;
        }

        public void assignProcess(ProcessControlBlock pcb) {
            this.pcb = pcb;
        }

        public ProcessControlBlock getAssignedProcess() {
            return pcb;
        }

        @Override
        public void run() {
            if (pcb == null) {
                System.out.println(coreName + ": Idle");
                return;
            }
            int timeSlice = 0;
            System.out.println(coreName + ": Executing " + pcb.getProcessId());
            while (timeSlice < quantum && pcb.hasMoreInstructions()) {
                Instruction instruction = pcb.getCurrentInstruction();
                executeInstruction(instruction, pcb);
                pcb.incrementProgramCounter();
                timeSlice++;
            }
        }

        private void executeInstruction(Instruction instruction, ProcessControlBlock pcb) {
            switch (instruction.command) {
                case "ASSIGN":
                    System.out.print(coreName + " Enter value for " + instruction.args[0] + ": ");
                    java.util.Scanner scanner = new java.util.Scanner(System.in);
                    int value = scanner.nextInt();
                    operationHandler.assign(instruction.args[0], value);
                    break;
                case "ADD":
                case "SUB":
                case "MUL":
                case "DIV":
                    operationHandler.performArithmetic(instruction.command, instruction.args[0], instruction.args[1], instruction.args[2]);
                    break;
                case "PRINT":
                    operationHandler.print(instruction.args[0]);
                    break;
                default:
                    System.out.println(coreName + " Unknown command: " + instruction.command);
            }
        }
    }

    private void executeInstruction(Instruction instruction, ProcessControlBlock pcb) {
        switch (instruction.command) {
            case "ASSIGN":
                System.out.print("Enter value for " + instruction.args[0] + ": ");
                Scanner scanner = new Scanner(System.in);
                int value = scanner.nextInt();
                operationHandler.assign(instruction.args[0], value);
                break;
            case "ADD":
                operationHandler.performArithmetic("ADD", instruction.args[0], instruction.args[1], instruction.args[2]);
                break;
            case "SUB":
                operationHandler.performArithmetic("SUB", instruction.args[0], instruction.args[1], instruction.args[2]);
                break;
            case "MUL":
                operationHandler.performArithmetic("MUL", instruction.args[0], instruction.args[1], instruction.args[2]);
                break;
            case "DIV":
                operationHandler.performArithmetic("DIV", instruction.args[0], instruction.args[1], instruction.args[2]);
                break;
            case "PRINT":
                operationHandler.print(instruction.args[0]);
                break;
            default:
                System.out.println("Unknown command: " + instruction.command);
        }
        memory.display(); // Display memory state after each instruction
    }
}