package de.fh.stud.p3;

import java.util.HashSet;
import java.util.Set;

import de.fh.stud.p2.Node;

public abstract class Search {

	protected final Set<Node> closed = new HashSet<>();

	public abstract Node next();

}
