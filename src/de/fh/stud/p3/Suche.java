package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import de.fh.stud.p2.Knoten;

public abstract class Suche {

	protected final Deque<Knoten> opened = new ArrayDeque<Knoten>();
	protected final Set<Knoten> closed = new HashSet<>();

	public Suche(Knoten startKnoten) {
		startKnoten.expand().forEach(opened::offer);
		closed.add(startKnoten);
	}

	public abstract Knoten next();

}
