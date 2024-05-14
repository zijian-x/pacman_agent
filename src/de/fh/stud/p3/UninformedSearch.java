package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import de.fh.stud.p2.PacmanNode;

public abstract class UninformedSearch extends Search {

	protected final Deque<PacmanNode> opened = new ArrayDeque<PacmanNode>();

	public UninformedSearch(PacmanNode startNode) {
		startNode.expand().forEach(opened::offer);
		closed.add(startNode);
	}

}
