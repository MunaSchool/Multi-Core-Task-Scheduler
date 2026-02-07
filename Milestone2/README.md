# Multi-Core Task Scheduler

This project implements a multi-core execution system using a master-slave architecture in Java. One core acts as the master, coordinating execution, while two slave cores process tasks concurrently. The system supports variable assignments, arithmetic operations, and print commands, with each instruction executed in a single clock cycle.

## Features
- **Master-Slave Architecture:** Master schedules and delegates tasks to two slave cores.
- **Ready Queue:** Manages processes waiting for execution.
- **Shared Memory:** Ensures data consistency across all cores.
- **Process Control Block (PCB):** Tracks process ID, program counter, and memory boundaries.
- **Round Robin Scheduling:** Allocates time slices (quantum=2) to each process, rotating across cores.
- **Operations:** Supports variable assignment, addition, subtraction, multiplication, division, and print commands.
- **Execution Monitoring:** Displays ready queue, processor status, and memory state at each clock cycle.

## How It Works
- All programs arrive at time zero.
- The master core reads program files, parses instructions, and creates PCBs.
- Slave cores execute instructions concurrently, managed by the master using round robin scheduling.
- The system prints the ready queue, processor status, and memory state after each cycle.

## Usage
1. Place your program files in the `src/programs/` directory.
2. Run the main class to start the scheduler.
3. Follow console prompts for variable assignments.

## Example Outputs
- Ready queue contents after each cycle
- Current process executing on each core
- Memory state after each operation

## Evaluation Criteria
- Correctness of operations and scheduling
- Accurate processor and memory monitoring

## Repository
All code and documentation are available in this public repository.
