package de.fh.stud.p1;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.PacmanAgent;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;

public class MyAgent_P1 extends PacmanAgent {

    private PacmanAction currentAction, nextAction;

    public MyAgent_P1(String name) {
        super(name);
    }

    public static void main(String[] args) {
        MyAgent_P1 agent = new MyAgent_P1("MyAgent");
        Agent.start(agent, "127.0.0.1", 5000);
    }

    @Override
    public PacmanAction action(PacmanPercept percept,
            PacmanActionEffect actionEffect) {
        var view = percept.getView();
        Util.printView(view);
        printPos(percept);

        nextAction = avoidWallPreemptively(percept, actionEffect);

        // Zusatzaufgabe
        var x = percept.getPosX();
        var y = percept.getPosY();
        if (nextAction == PacmanAction.GO_NORTH &&
                view[x + 1][y] == PacmanTileType.DOT)
            nextAction = PacmanAction.GO_EAST;

        currentAction = nextAction;
        return nextAction;
    }

    private PacmanAction avoidWallPreemptively(PacmanPercept percept,
            PacmanActionEffect actionEffect) {
        var grid = percept.getView();
        var x = percept.getPosX();
        var y = percept.getPosY();
        switch (currentAction) {
            case GO_EAST:
                if (x + 2 == grid.length)
                    return PacmanAction.GO_SOUTH;
                break;
            case GO_NORTH:
                if (0 + 1 == y)
                    return PacmanAction.GO_EAST;
                break;
            case GO_SOUTH:
                if (y + 2 == grid[0].length)
                    return PacmanAction.GO_WEST;
                break;
            case GO_WEST:
                if (0 + 1 == x)
                    return PacmanAction.GO_NORTH;
                break;
            default:
                break;
        }

        return currentAction;
    }

    private void printPos(PacmanPercept percept) {
        var view = percept.getView();
        var pos = percept.getPosition();
        var x = pos.x;
        var y = pos.y;
        System.out.println("row len: " + view[0].length);
        System.out.println("col len: " + view.length);
        System.out.println("cur row: " + y);
        System.out.println("cur col: " + x);
        System.out.println("current: " + view[x][y]);
        System.out.println("right neighbor: " + view[x][y + 1]);
    }

    @Override
    protected void onGameStart(PacmanStartInfo startInfo) {
        nextAction = PacmanAction.GO_EAST;
        currentAction = nextAction;
        System.out.println("set start action to go east");
    }

    @Override
    protected void onGameover(PacmanGameResult gameResult) {
    }
}
