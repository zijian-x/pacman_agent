package de.fh.stud.p3;

import de.fh.stud.p2.PacmanNode;

public class Search {

	protected final SearchStrategy strategy;

	public Search(SearchStrategy strategy) {
		this.strategy = strategy;
	}

	public PacmanNode next() {
		return this.strategy.next();
	}

}
