package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Util;
import de.fh.pacman.PacmanAgent;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.stud.p2.Knoten;

public class MyAgent_P3 extends PacmanAgent {

	private PacmanAction nextAction;

	private final Queue<Knoten> path = new ArrayDeque<>();

	private Suche suche;
	private Knoten currentKnoten;
	private Knoten nextKnoten;

	public MyAgent_P3(String name) {
		super(name);
	}

	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
		Util.printView(percept.getView());

		if (path.isEmpty())
			fillPath(suche.next());

		this.nextKnoten = path.poll();
		this.nextAction = toTargetAction();
		this.currentKnoten = this.nextKnoten;

		return this.nextAction;
	}

	private void fillPath(Knoten target) {
		var visited = new HashSet<Knoten>();
		Queue<Map.Entry<ArrayDeque<Knoten>, Knoten>> que = new ArrayDeque<>();

		que.offer(Map.entry(new ArrayDeque<Knoten>(), target));
		while (!que.isEmpty()) {
			for (var size = que.size(); size > 0; --size) {
				var entry = que.poll();
				var history = entry.getKey();
				var cur = entry.getValue();
				if (visited.contains(cur))
					continue;
				if (cur.equals(this.currentKnoten)) {
					while (!history.isEmpty())
						path.offer(history.pollLast());
					return;
				}

				history.offerLast(cur);
				visited.add(cur);
				cur.expand().forEach(neighbor ->
						que.offer(Map.entry(new ArrayDeque<Knoten>(history), neighbor)));
			}
		}

		throw new IllegalStateException("Path to node not found");
	}

	private PacmanAction toTargetAction() {
		int dx = nextKnoten.x - this.currentKnoten.x;
		int dy = nextKnoten.y - this.currentKnoten.y;

		if (dx != 0 && dy != 0)
			throw new IllegalStateException("Error: Next node isn't the immediate neighbor");
		if (dx == 0 && dy == 0)
			return PacmanAction.WAIT;
		if (dx != 0)
			return (dx > 0) ? PacmanAction.GO_EAST : PacmanAction.GO_WEST;
		return (dy > 0) ? PacmanAction.GO_SOUTH : PacmanAction.GO_NORTH;
	}

	@Override
	protected void onGameStart(PacmanStartInfo startInfo) {
		this.currentKnoten = new Knoten(startInfo.getPercept().getView(),
									startInfo.getStartX(), startInfo.getStartY());
		suche = new Tiefensuche(this.currentKnoten);
	}

	@Override protected void onGameover(PacmanGameResult gameResult) {
	}

}
