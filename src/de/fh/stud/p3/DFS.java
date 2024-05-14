package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class DFS implements SearchStrategy {

	private final Deque<PacmanNode> opened = new ArrayDeque<PacmanNode>();
	private final Set<PacmanNode> closed = new HashSet<>();

	public DFS(PacmanNode startNode) {
		startNode.expand().forEach(opened::offer);
		closed.add(startNode);
	}

	@Override
	public PacmanNode next() {
		var next = opened.pollLast();
		do {
			while (closed.contains(next))
				next = opened.pollLast();

			next.expand().forEach(neighbor -> {
				if (!closed.contains(neighbor))
					opened.offerLast(neighbor);
			});
			closed.add(next);
		} while (next.state() != PacmanTileType.DOT);

		return next;
	}

}
