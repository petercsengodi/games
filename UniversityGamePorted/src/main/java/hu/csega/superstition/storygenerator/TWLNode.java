package hu.csega.superstition.storygenerator;

import java.util.ArrayList;
import java.util.List;

public class TWLNode {

	protected List<TWLLink> links, linkends;
	protected TWLNode actual;

	public TWLNode() {
		links = new ArrayList<>(0);
		linkends = new ArrayList<>(0);
		actual = null;
	}

	public TWLNode getActual() {
		return actual;
	}

	public void doForAllLinks(TWLFunc func) {
		for(TWLLink link : links) {
			func.call(link);
		}
	}

	public void addLink(TWLLink _link) {
		links.add(_link);
	}

	public void addLinkEnd(TWLLink _link) {
		linkends.add(_link);
	}

	public boolean isLinkedTo(TWLNode n) {
		boolean isLinked = false;

		for(TWLLink link : links) {
			if(n == link.getTo()) {
				isLinked = true;
				break;
			}
		}

		return isLinked;
	}

	public void onInserted(){
	}

	public void onLinkEnd(){
	}

	public void onLinkStart(){
	}

}