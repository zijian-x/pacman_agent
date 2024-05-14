package de.fh.stud.p3;

import java.util.HashSet;
import java.util.Set;

import de.fh.stud.p2.PacmanNode;

public abstract class Search {

	protected final Set<PacmanNode> closed = new HashSet<>();

	public abstract PacmanNode next();

}
