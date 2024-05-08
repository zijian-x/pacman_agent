package de.fh.stud.p3;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.Knoten;

public class Tiefensuche extends Suche {

	public Tiefensuche(Knoten startKnoten) {
		super(startKnoten);
	}

	@Override
	public Knoten next() {
		Knoten next = opened.pollLast();
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
