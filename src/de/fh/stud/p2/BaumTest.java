package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanTileType;

public class BaumTest {

    public static void main(String[] args) {
        // Anfangszustand nach Aufgabe
        PacmanTileType[][] view = {
                { PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.EMPTY, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.WALL },
                { PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL }
        };
        // Startposition des Pacman
        int posX = 1, posY = 1;
        var start = new Knoten(view, posX, posY);
    }
}
