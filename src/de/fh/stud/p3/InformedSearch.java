package de.fh.stud.p3;

import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.fh.stud.p2.Node;

public abstract class InformedSearch extends Search {

	protected final Queue<Map.Entry<Node, Double>> opened = new PriorityQueue<>(
			(x, y) -> Double.compare(x.getValue(), y.getValue()));

	public InformedSearch(Node startNode) {
		startNode.expand().forEach(neighbor -> opened.offer(Map.entry(neighbor, 0.0)));
		closed.add(startNode);
	}

}
