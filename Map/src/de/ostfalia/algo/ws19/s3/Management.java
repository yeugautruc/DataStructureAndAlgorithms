package de.ostfalia.algo.ws19.s3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.ostfalia.algo.ws19.base.*;
import de.ostfalia.algo.ws19.s3.Node;

public class Management implements IManagement {
	private Node root;
	private int opNumber;
	private long lastActionTime;

	public Management(String file) {
		try {
			int tmpOperation = 1;
			File txtFile = new File(file);
			FileReader fr = new FileReader(txtFile);
			BufferedReader br = new BufferedReader(fr);
			String data = br.readLine();
			if (file != null) {
				long timeBefore = System.currentTimeMillis();
				while (data != null) {
					opNumber = 0;
					this.insert(new Member(data));
					tmpOperation += opNumber;
					data = br.readLine();
				}
				opNumber = ++tmpOperation;
				lastActionTime = System.currentTimeMillis() - timeBefore;
			}
			br.close();
			fr.close();
		} catch (Exception e) {
		}
	}

	public Management(String[] data) throws IOException {
		int tmpOperation = 1;
		long timeBefore = System.currentTimeMillis();
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null && data[i].length() != 0) {
				opNumber = 0;
				this.insert(new Member(data[i]));
				tmpOperation += opNumber;
			}
		}
		opNumber = ++tmpOperation;
		lastActionTime = System.currentTimeMillis() - timeBefore;
	}

	public Management() {
	}
	/*
	 * Insert
	 */

	@Override
	public void insert(IMember member) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 0;
		root = insertNode(root, member);
		lastActionTime = System.currentTimeMillis() - timeBefore;
	}

	public Node insertNode(Node current, IMember member) {
		if (current == null) {
			return new Node(member);
		} else if (member.getKey() > current.getMember().getKey()) {
			// whether member key is greater. Should be inserted to right
			opNumber++;
			current.setRight(insertNode(current.getRight(), member));
			// comeback to 69 test if node null
		} else {
			opNumber++;
			current.setLeft(insertNode(current.getLeft(), member));
		}
		return current;
	}

	/*
	 * Search
	 */
	@Override
	public IMember search(long key) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 0;
		IMember out = searchNode(root, key);
		lastActionTime = System.currentTimeMillis() - timeBefore;
		return out;

	}

	public IMember searchNode(Node root, long key) {
		opNumber++;
		if (root == null)
			return null;
		if (root.getMember().getKey() == key) // if member key = key, found
			return root.getMember();
		if (root.getMember().getKey() > key) // if member key > key, go left branch to find
			return searchNode(root.getLeft(), key);
		return searchNode(root.getRight(), key);
	}

	@Override
	public IMember search(String name, String firstName) {
		long timeBefore = System.currentTimeMillis();
		Node out = searchNode(root, name, firstName);
		lastActionTime = System.currentTimeMillis() - timeBefore;
		if (out != null) {
			return out.getMember();
		} else
			return null;
	}

	public Node searchNode(Node root, String name, String firstName) {
		if (root == null
				|| root.getMember().getName().equals(name) && root.getMember().getFirstName().equals(firstName))
			return root;
		else {
			// just go to every node, if null end node search, the other keep finding
			// if not null, go to deeper branch to find
			// if found node, return node found
			Node outL = searchNode(root.getLeft(), name, firstName);
			if (outL != null)
				return outL;
			Node outR = searchNode(root.getRight(), name, firstName);
			if (outR != null)
				return outR;
		}
		return null;

	}

	/*
	 * Size
	 */
	@Override
	public int size() {
		opNumber = 0;
		return sizeNode(root);
	}

	public int sizeNode(Node root) {
		if (root == null || root.getMember() == null) {
			return 0;
		} else {
			opNumber++;
			// take this node and deeper node to count
			return 1 + sizeNode(root.getLeft()) + sizeNode(root.getRight());
		}
	}

	@Override
	public int size(KindOfSport kindOfSport) {
		opNumber = 0;
		return size(root, kindOfSport);
	}

	public int size(Node root, KindOfSport kindOfSport) {
		if (root == null) {
			return 0;
		} else if (root.getMember().getKindOfSport().equals(kindOfSport)) {
			opNumber++;
			return 1 + size(root.getLeft(), kindOfSport) + size(root.getRight(), kindOfSport);
		} else {
			opNumber++;
			return size(root.getLeft(), kindOfSport) + size(root.getRight(), kindOfSport);
		}
	}

	/*
	 * Delete
	 */
	@Override
	public void deleteKey(long key) {
		root = deleteNode(root, key);
	}

	public Node deleteNode(Node root, long key) {
		// if root null
		if (root == null)
			return root;
		// if not check in tree
		if (key < root.getMember().getKey())
			root.setLeft(deleteNode(root.getLeft(), key));
		else if (key > root.getMember().getKey())
			root.setRight(deleteNode(root.getRight(), key));
		else { // found the node got to delete
				// node don't have children or just 1 children
			if (root.getLeft() == null)
				return root.getRight();
			else if (root.getRight() == null)
				return root.getLeft();
			// if node have 2 children node
			root.getMember().setKey(minValue(root.getRight()));
			root.setRight(deleteNode(root.getRight(), root.getMember().getKey()));
		}

		return root;
	}

	public long minValue(Node root) {
		long minv = root.getMember().getKey();
		while (root.getLeft() != null) {
			minv = root.getLeft().getMember().getKey();
			root = root.getLeft();
		}
		return minv;
	}

	/*
	 * to Array
	 */
	@Override
	public IMember[] toArray() {
		IMember[] array = new IMember[size()];
		// the arrays length must be equivalent to the number of Nodes in the tree
		addNodeToArray(root, array);
		return array;
	}

	private int index = 0;

	public void addNodeToArray(Node node, IMember[] array) {
		/*
		 * recursion anchor: when the node is null an empty leaf was reached (doesn't
		 * matter if it is left or right, just end the method call
		 */
		if (node != null) {
			// InOrder Traversal
			addNodeToArray(node.getLeft(), array); // first do every left child tree
			array[index++] = node.getMember();// then write the member in the array
			addNodeToArray(node.getRight(), array); // do the same with the right child
		}
	}

	/*
	 * Discipline
	 */
	@Override
	public IMember[] discipline(KindOfSport kindOfSport) {
		IMember[] arr = new IMember[size(kindOfSport)];
		addNode(root, arr, kindOfSport);
		return arr;
	}

	private void addNode(Node node, IMember[] arr, KindOfSport kindOfSport) {
		if (node != null) {
			addNode(node.getLeft(), arr, kindOfSport);
			int counter = 0;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					counter++;
				}
			}
			if (node.getMember().getKindOfSport() == kindOfSport) {
				arr[counter] = node.getMember();
			}
			addNode(node.getRight(), arr, kindOfSport);
		}
	}

	/*
	 * Height
	 */
	@Override
	public int height() {
		return heightNode(root);
	}

	public int heightNode(Node a) {
		if (a == null || isLeaf(a)) // height will be 0 if the node is leaf or null
			return 1;
		// height of a node will be 1+ greater among height of right subtree and height
		// of left subtree
		return (getMax(heightNode(a.getLeft()), heightNode(a.getRight())) + 1);
	}

	public static boolean isLeaf(Node a) {
		if (a.getRight() == null && a.getLeft() == null)
			return true;
		return false;
	}

	public static int getMax(int a, int b) {
		return (a > b) ? a : b;
	}

	/*
	 * Getter Setter
	 */
	@Override
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	@Override
	public int numberOfOperations() {
		return opNumber;
	}

	public int getOpNumber() {
		return opNumber;
	}

	public long getLastActionTime() {
		return lastActionTime;
	}

	public void setLastActionTime(long lastActionTime) {
		this.lastActionTime = lastActionTime;
	}
//	Arrays.sort(array, (IMember o1, IMember o2) -> Long.compare(o1.getKey(), o2.getKey()));

}