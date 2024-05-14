package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import de.fh.stud.p2.Node;

public abstract class UninformedSearch extends Search {

	protected final Deque<Node> opened = new ArrayDeque<Node>();

	public UninformedSearch(Node startNode) {
		startNode.expand().forEach(opened::offer);
		closed.add(startNode);
	}

}
