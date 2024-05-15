package de.fh.stud.p3;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p2.PacmanNode;

public class AStar implements SearchStrategy {

	private class Weight implements Comparable<Weight> {

		public double cost;
		public double heuristic;

		private Weight(double cost, double heuristic) {
			this.cost = cost;
			this.heuristic = heuristic;
		}

		public double sum() {
			return cost + heuristic;
		}

		@Override
		public int compareTo(Weight w) {
			return Double.compare(this.sum(), w.sum());
		}

		@Override
		public String toString() {
			return String.format("[%.2f, %.2f]", this.cost, this.heuristic);
		}

	}

	private final Queue<Map.Entry<PacmanNode, Weight>> opened =
		new PriorityQueue<>((x, y) -> x.getValue().compareTo(y.getValue()));
	private final Set<PacmanNode> closed = new HashSet<>();

	private PacmanNode currentPos;
	private PacmanNode target;

	public AStar(PacmanNode startNode) {
		this.currentPos = startNode;
		this.target = findNextTarget(startNode);

		// assume that the start node doesn't have a dot and thus won't ever be a target
		startNode.expand().forEach(neighbor ->
				this.opened.offer(Map.entry(neighbor, new Weight(0, evaluateHeuristic(neighbor, this.target)))));
		this.closed.add(startNode);
	}

	private static double evaluateHeuristic(PacmanNode current, PacmanNode target) {
		double dx = Math.abs(target.x - current.x);
		double dy = Math.abs(target.y - current.y);
		return Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));
	}

	public void printView() {
		var view = this.currentPos.view;
		for (var x = 0; x < view[0].length; ++x) {
			for (var y = 0; y < view.length; ++y) {
				var cur = view[y][x];
				if (y == 0)
					System.out.print(x);
				else if (x == 0)
					System.out.print(y);
				else {
					var val = evaluateHeuristic(new PacmanNode(view, x, y), target);
					switch (cur) {
						case DOT:
							System.out.printf("%.2f", val);
							break;
						case EMPTY:
							System.out.printf("%.2f", val);
							break;
						case PACMAN:
							System.out.print('C');
							break;
						case WALL:
							System.out.print('|');
							break;
						default:
							break;
					}
				}
				System.out.print('\t');
			}
			System.out.println();
		}
	}

	@Override
	public PacmanNode next() {
		if (this.currentPos.equals(this.target)) {
			this.target = findNextTarget(currentPos);
			this.opened.clear();
			this.closed.clear();
			this.currentPos.expand().forEach(neighbor ->
					opened.offer(Map.entry(neighbor,
							new Weight(0, evaluateHeuristic(neighbor, this.target)))));
			this.closed.add(currentPos);
		}

		logState();

		var next = opened.poll();
		while (closed.contains(next.getKey()) ||
				!isNeighbor(this.currentPos, next.getKey()))
			next = opened.poll();

		var weight = next.getValue();
		next.getKey().expand().forEach(neighbor -> {
			if (!this.closed.contains(neighbor))
				this.opened.offer(Map.entry(neighbor,
							new Weight(weight.cost + 1, evaluateHeuristic(neighbor, this.target))));
		});
		this.closed.add(next.getKey());

		this.currentPos = next.getKey();
		return next.getKey();
	}

	private boolean isNeighbor(PacmanNode currentNode, PacmanNode checkNode) {
		var dx = Math.abs(currentNode.x - checkNode.x);
		var dy = Math.abs(currentNode.y - checkNode.y);
		return Math.abs(dx - dy) == 1;
	}

	private PacmanNode findNextTarget(PacmanNode currentNode) {
		Queue<PacmanNode> queue = new ArrayDeque<>();
		Set<PacmanNode> closed = new HashSet<>();
		queue.offer(currentNode);
		while (!queue.isEmpty()) {
			for (var n = queue.size(); n > 0; --n) {
				var node = queue.poll();
				if (closed.contains(node))
					continue;
				if (node.state() == PacmanTileType.DOT)
					return node;
				node.expand().forEach(queue::offer);
				closed.add(node);
			}
		}

		throw new IllegalStateException("All dots are eaten, the program shouldn't reach here");
	}

	@SuppressWarnings("unused")
	private void logState() {
		System.out.println("start node: " + currentPos);
		System.out.println("target node: " + target);
		System.out.println("pq: " + opened);
	}

}
