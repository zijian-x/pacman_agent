package de.fh.stud.p3;

import de.fh.stud.p2.PacmanNode;

public interface SearchStrategy {

	public PacmanNode next();
	public int openedSize();
	public int closedSize();

}
