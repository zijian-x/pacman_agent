package de.fh.stud.p3;

import java.util.Map;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class UCS extends InformedSearch {

	UCS(PacmanNode startNode) {
		super(startNode);
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
		} while (next.getKey().zustand() != PacmanTileType.DOT);

		return next.getKey();
	}

}
