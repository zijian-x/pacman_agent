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
import de.fh.stud.p2.PacmanNode;

public class MyAgent_P3 extends PacmanAgent {

	private PacmanAction nextAction;

	private final Queue<PacmanNode> path = new ArrayDeque<>();

	private Search suche;
	private PacmanNode currentNode;
	private PacmanNode nextNode;

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

		this.nextNode = path.poll();
		this.nextAction = getActionToTarget();
		this.currentNode = this.nextNode;

		return this.nextAction;
	}

	// TODO stop using member variables currentNode
	private void fillPath(PacmanNode target) {
		var visited = new HashSet<PacmanNode>();
		Queue<Map.Entry<ArrayDeque<PacmanNode>, PacmanNode>> que = new ArrayDeque<>();

		que.offer(Map.entry(new ArrayDeque<PacmanNode>(), target));
		while (!que.isEmpty()) {
			for (var size = que.size(); size > 0; --size) {
				var entry = que.poll();
				var history = entry.getKey();
				var cur = entry.getValue();
				if (visited.contains(cur))
					continue;
				if (cur.equals(this.currentNode)) {
					while (!history.isEmpty())
						path.offer(history.pollLast());
					return;
				}

				history.offerLast(cur);
				visited.add(cur);
				cur.expand().forEach(neighbor -> que.offer(Map.entry(new ArrayDeque<PacmanNode>(history), neighbor)));
			}
		}

		throw new IllegalStateException("Path to node not found");
	}

	// TODO stop using member variables currentNode, nextNode
	private PacmanAction getActionToTarget() {
		int dx = nextNode.x - this.currentNode.x;
		int dy = nextNode.y - this.currentNode.y;

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
		this.currentNode = new PacmanNode(startInfo.getPercept().getView(),
				startInfo.getStartX(), startInfo.getStartY());
		suche = new UCS(this.currentNode);
	}

	@Override
	protected void onGameover(PacmanGameResult gameResult) {
	}

}
