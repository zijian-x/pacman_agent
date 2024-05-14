package de.fh.stud.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.fh.pacman.enums.PacmanTileType;

public class PacmanNode {

	public PacmanTileType[][] view;
	public final int x, y;

	public PacmanNode(PacmanTileType[][] view, int x, int y) {
		this.view = view;
		this.x = x;
		this.y = y;
	}

	public List<PacmanNode> expand() {
		List<PacmanNode> list = new ArrayList<>();
		for (var i = -1; i < 2; ++i) {
			for (var j = -1; j < 2; ++j) {
				if (i == j || Math.abs(i - j) > 1)
					continue;
				var newX = x + i;
				var newY = y + j;
				if (newX > 0 && newY > 0 && newX < view.length && newY < view[newX].length &&
						view[newX][newY] != PacmanTileType.WALL) {
					// var newView = copyView(view);
					list.add(new PacmanNode(view, newX, newY));
				}
			}
		}

		return list;
	}

	public PacmanTileType state() {
		return this.view[this.x][this.y];
	}

	@SuppressWarnings("unused")
	private PacmanTileType[][] copyView(final PacmanTileType[][] view) {
		var newView = new PacmanTileType[view.length][view[0].length];
		for (var i = 0; i < view.length; ++i) {
			for (var j = 0; j < view[i].length; ++j) {
				newView[i][j] = view[i][j];
			}
		}
		return newView;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PacmanNode))
			return false;

		var node = (PacmanNode) o;

		return this.view == node.view &&
				this.x == node.x &&
				this.y == node.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(view, x, y);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

}
