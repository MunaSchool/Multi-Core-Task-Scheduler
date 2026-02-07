
package scheduler;

import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {
	private Queue<ProcessControlBlock> queue;

	public ReadyQueue() {
		this.queue = new LinkedList<>();
	}

	public synchronized void enqueue(ProcessControlBlock pcb) {
		queue.offer(pcb);
	}

	public synchronized ProcessControlBlock dequeue() {
		return queue.poll();
	}

	public synchronized boolean isEmpty() {
		return queue.isEmpty();
	}

	public synchronized void display() {
		System.out.print("Ready Queue: ");
		for (ProcessControlBlock pcb : queue) {
			System.out.print(pcb.getProcessId() + " ");
		}
		System.out.println();
	}
}
