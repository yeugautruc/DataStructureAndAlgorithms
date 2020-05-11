package de.ostfalia.algo.ws19.s1;

import de.ostfalia.algo.ws19.base.IMember;

public class Node {

	private IMember member;
	private Node next;
	
	public Node(IMember member, Node next) {
		this.member=member;
		this.next=next;
	}
	
	public IMember getMember() {
		return member;
	}
	public void setMember(IMember member) {
		this.member = member;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}

}