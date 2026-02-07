package scheduler;

import java.util.List;

class ProcessControlBlock {
    private String processId;
    private int programCounter;
    private List<Instruction> instructions;

    public ProcessControlBlock(String processId, List<Instruction> instructions) {
        this.processId = processId;
        this.instructions = instructions;
        this.programCounter = 0; 
    }

    public String getProcessId() {
        return processId;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void incrementProgramCounter() {
        programCounter++;
    }

    public boolean hasMoreInstructions() {
        return programCounter < instructions.size();
    }

    public Instruction getCurrentInstruction() {
        return instructions.get(programCounter);
    }
}
