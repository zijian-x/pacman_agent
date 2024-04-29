package de.fh.stud.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.fh.pacman.enums.PacmanTileType;

public class Knoten {

    public PacmanTileType[][] view;
    public final int x, y;

    public Knoten(PacmanTileType[][] view, int x, int y) {
        this.view = view;
        this.x = x;
        this.y = y;
    }

    public List<Knoten> expand() {
        List<Knoten> list = new ArrayList<>();
        this.view[x][y] = PacmanTileType.EMPTY;
        for (var i = -1; i < 2; ++i) {
            for (var j = -1; j < 2; ++j) {
                if (i == j || Math.abs(i - j) > 1)
                    continue;
                var newX = x + i;
                var newY = y + j;
                if (newX > 0 && newY > 0 && newX < view.length && newY < view[newX].length &&
                        view[newX][newY] != PacmanTileType.WALL) {
                    var newView = copyView(view);
                    list.add(new Knoten(newView, newX, newY));
                }
            }
        }

        return list;
    }

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
        if (!(o instanceof Knoten))
            return false;

        var knoten = (Knoten)o;
		// FIXME probably need to compare references for properly expanding
        if (this.view.length != knoten.view.length ||
                this.view[0].length != knoten.view[0].length)
            return false;
        for (var i = 0; i < view.length; ++i) {
            for (var j = 0; j < view[i].length; ++j) {
                if (view[i][j] != knoten.view[i][j])
                    return false;
            }
        }

        return this.x == knoten.x &&
            this.y == knoten.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(view, x, y);
    }

}
