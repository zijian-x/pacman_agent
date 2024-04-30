package de.fh.stud.p3;

import de.fh.stud.p2.Knoten;

public class Tiefensuche extends Suche {

	public Tiefensuche(Knoten startKnoten) {
		super(startKnoten);
	}

	@Override
	public Knoten next() {
		Knoten next;
		do {
			next = opened.pollLast();
		} while (closed.contains(next));

		next.expand().forEach(neighbor -> {
			if (!closed.contains(neighbor))
				opened.offerLast(neighbor);
		});
		closed.add(next);

		return next;
	}

}
