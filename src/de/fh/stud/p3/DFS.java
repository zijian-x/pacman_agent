package de.fh.stud.p3;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class DFS extends UninformedSearch {

	public DFS(PacmanNode startNode) {
		super(startNode);
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
		} while (next.zustand() != PacmanTileType.DOT);

		return next;
	}

}
