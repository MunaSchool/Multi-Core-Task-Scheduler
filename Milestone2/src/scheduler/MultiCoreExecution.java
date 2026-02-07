package scheduler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiCoreExecutionSystem {
    public static void main(String[] args) {
        ReadyQueue readyQueue = new ReadyQueue();
        SharedMemory memory = new SharedMemory();
        int quantum = 2; // Time slice for Round Robin

        // Read programs and create PCBs
        List<ProcessControlBlock> pcbs = new ArrayList<>();
        String[] programFiles = {"programs/program1.txt", "programs/program2.txt", "programs/program3.txt"};

        for (int i = 0; i < programFiles.length; i++) {
            List<Instruction> instructions = readProgram(programFiles[i]);
            ProcessControlBlock pcb = new ProcessControlBlock("Program" + (i + 1), instructions);
            pcbs.add(pcb);
            readyQueue.enqueue(pcb);
        }

        // Display initial state
        readyQueue.display();
        memory.display();

        // Create and run the Round Robin scheduler
        RoundRobinScheduler scheduler = new RoundRobinScheduler(readyQueue, memory, quantum);
        scheduler.schedule();
    }

    private static List<Instruction> readProgram(String filePath) {
        List<Instruction> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0];
                String[] args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
                instructions.add(new Instruction(command, args));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return instructions;
    }
}