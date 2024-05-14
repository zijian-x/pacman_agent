package de.fh.stud.p3;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class BFS extends UninformedSearch {

	public BFS(PacmanNode startNode) {
		super(startNode);
	}

	@Override
	public PacmanNode next() {
		var next = opened.pollFirst();
		do {
			while (closed.contains(next))
				next = opened.pollFirst();

			next.expand().forEach(neighbor -> {
				if (!closed.contains(neighbor))
					opened.offerLast(neighbor);
			});
			closed.add(next);
		} while (next.zustand() != PacmanTileType.DOT);

		return next;
	}

}
