package de.ostfalia.algo.ws19.s3;

import de.ostfalia.algo.ws19.base.IMember;

public class Node {

	private IMember member;
	private Node left;
	private Node right;

	public Node(IMember member) {
		this.member = member;
		if (member.getKey() < this.member.getKey())
			this.left.setMember(member);
		else if (member.getKey() > this.member.getKey())
			this.right.setMember(member);
	}

	public IMember getMember() {
		return member;
	}

	public void setMember(IMember member) {
		this.member = member;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}