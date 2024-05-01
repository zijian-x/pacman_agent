package de.fh.stud.p3;

import de.fh.stud.p2.Knoten;

public class Breitensuche extends Suche {

	public Breitensuche(Knoten startKnoten) {
		super(startKnoten);
	}

	@Override
	public Knoten next() {
		Knoten next;
		do {
			next = opened.pollFirst();
		} while (closed.contains(next));

		next.expand().forEach(neighbor -> {
			if (!closed.contains(neighbor))
				opened.offerLast(neighbor);
		});
		closed.add(next);

		return next;
	}

}
