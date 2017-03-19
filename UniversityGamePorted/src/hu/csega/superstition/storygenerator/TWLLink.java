package hu.csega.superstition.storygenerator;

public class TWLLink {
	protected TWLNode from;
	protected TWLNode to;

	public TWLLink() {
		this.to = null;
		this.from = null;
	}

	public TWLLink(TWLNode to) {
		this.to = to;
		this.from = null;
	}

	public TWLLink(TWLNode to, TWLNode from) {
		this.to = to;
		this.from = from;
	}

	public void setLink(TWLNode to) {
		this.to = to;
	}

	public void setLinks(TWLNode to, TWLNode from) {
		this.to = to;
		this.from = from;
	}

	public void callFunction(TWLFunc func) {
		func.call(to);
	}

	public TWLNode getFrom() {
		return from;
	}

	public TWLNode getTo() {
		return to;
	}

	public void onLinked(){
	}
}
