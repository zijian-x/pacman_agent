package de.fh.stud.p2;

import java.util.ArrayDeque;
import java.util.HashSet;

import de.fh.pacman.enums.PacmanTileType;

public class BaumTest {

	public static void main(String[] args) {
		// Anfangszustand nach Aufgabe
		PacmanTileType[][] view = {
				{ PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.EMPTY, PacmanTileType.DOT, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.WALL },
				{ PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL },
		};
		// Startposition des Pacman
		int posX = 1, posY = 1;
		var start = new PacmanNode(view, posX, posY);
		var deque = new ArrayDeque<PacmanNode>();
		var set = new HashSet<PacmanNode>();
		deque.offer(start);
		set.add(start);
		while (!deque.isEmpty()) {
			var node = deque.pollLast();
			node.expand().forEach(k -> {
				if (set.add(k))
					deque.offer(k);
			});
			System.out.println(node.x + " " + node.y);
		}
	}
}
