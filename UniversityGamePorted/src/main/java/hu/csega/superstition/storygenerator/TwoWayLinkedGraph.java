package hu.csega.superstition.storygenerator;

import java.util.ArrayList;
import java.util.List;

public class TwoWayLinkedGraph {

	private TWLNode actual;
	private List<TWLNode> nodes;

	public TwoWayLinkedGraph() {
		this.actual = null;
		this.nodes = new ArrayList<>(0);
	}

	public TWLNode getActual() {
		return actual;
	}

	public void doForAllNodes(TWLFunc func) {
		for(TWLNode node : nodes) {
			func.call(node);
		}
	}

	public void doForAllLinks(TWLFunc func) {
		for(TWLNode node : nodes) {
			node.doForAllLinks(func);
		}
	}

	public void addNode(TWLNode node) {
		nodes.add(node);
		node.onInserted();
	}

	public void Link(TWLNode from, TWLNode to, TWLLink link) {
		boolean notfound = true;
		for(TWLNode n : nodes) {
			if (n == from) {
				notfound = false;
				break;
			}
		}

		if(notfound) {
			nodes.add(from);
			from.onInserted();
		}

		notfound = false;

		for(TWLNode n : nodes) {
			if (n == to) {
				notfound = false;
				break;
			}
		}

		if(notfound) {
			nodes.add(to);
			to.onInserted();
		}

		link.setLinks(to, from);
		from.addLink(link);
		link.onLinked();
		from.onLinkStart();
		to.addLinkEnd(link);
		to.onLinkEnd();
	}

	public boolean isRelated(TWLNode n1, TWLNode n2) {
		return n1.isLinkedTo(n2) || n2.isLinkedTo(n1);
	}
}