package de.fh.stud.p3;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.Knoten;

public class Breitensuche extends Suche {

	public Breitensuche(Knoten startKnoten) {
		super(startKnoten);
        System.out.println("start node: " + startKnoten);
	}

	@Override
	public Knoten next() {
		Knoten next = opened.pollFirst();
        System.out.println("before expansion: " + this.opened);
        do {
            while (closed.contains(next))
                next = opened.pollFirst();

            next.expand().forEach(neighbor -> {
                if (!closed.contains(neighbor))
                    opened.offerLast(neighbor);
            });
            closed.add(next);
        } while (next.zustand() != PacmanTileType.DOT);

        System.out.println("after expansion: " + this.opened);
        System.out.println("return node: " + next);
		return next;
	}

}
