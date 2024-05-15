package de.fh.stud.p3;

import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class UCS implements SearchStrategy {

	private final Queue<Map.Entry<PacmanNode, Double>> opened = new PriorityQueue<>(
			(x, y) -> Double.compare(x.getValue(), y.getValue()));
	private final Set<PacmanNode> closed = new HashSet<>();

	UCS(PacmanNode startNode) {
		startNode.expand().forEach(neighbor -> opened.offer(Map.entry(neighbor, 0.0)));
		closed.add(startNode);
	}

	@Override
	public PacmanNode next() {
		var next = opened.poll();
		do {
			while (closed.contains(next.getKey()))
				next = opened.poll();

			var cost = next.getValue();
			next.getKey().expand().forEach(neighbor -> {
				if (!closed.contains(neighbor))
					opened.offer(Map.entry(neighbor, cost + 1));
			});
			closed.add(next.getKey());
		} while (next.getKey().state() != PacmanTileType.DOT);

		return next.getKey();
	}

	@Override
	public int openedSize() {
		return opened.size();
	}

	@Override
	public int closedSize() {
		return closed.size();
	}

}
