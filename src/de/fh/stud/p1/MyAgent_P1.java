package de.fh.stud.p1;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.PacmanAgent;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;

public class MyAgent_P1 extends PacmanAgent {

    private PacmanAction currentAction, nextAction;

    public MyAgent_P1(String name) {
        super(name);
    }

    public static void main(String[] args) {
        MyAgent_P1 agent = new MyAgent_P1("MyAgent");
        Agent.start(agent, "127.0.0.1", 5000);
    }

    private void printCurrentPos(PacmanPercept percept) {
        var x = percept.getPosX();
        var y = percept.getPosY();
        System.out.println("x: " + x);
        System.out.println("y: " + y);
    }

    @Override
    public PacmanAction action(PacmanPercept percept,
            PacmanActionEffect actionEffect) {
        var view = percept.getView();

        Util.printView(view);
        printCurrentPos(percept);

        nextAction = avoidWallPreemptively(percept, actionEffect);

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
                if (x + 2 == grid[y].length)
                    return PacmanAction.GO_SOUTH;
                break;
            case GO_NORTH:
                if (0 + 1 == y)
                    return PacmanAction.GO_EAST;
                break;
            case GO_SOUTH:
                if (y + 2 == grid.length)
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
