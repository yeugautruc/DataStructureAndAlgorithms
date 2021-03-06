package de.ostfalia.algo.ws19.s4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.TreeSet;

import de.ostfalia.algo.ws19.base.*;

/*Operations which work on a single element are all O(ln n) 
comparisons except first and last which are O(1) comparisons or O(ln N) node search time.
comparator(), iterator(), clear(), first(), isEMpty(), size(), last(), pollFirst(), pollLast() are O(1)
add(), ceiling(), contains(), floor(), headSet(), higher(), lower(), remove(), subSet(), tailSet() are O(ln N)
clone(), equals(), hashCode(), toArray() and toString() are O(n)*/

public class ManagementTree implements IManagement {
	private NavigableSet<IMember> tree = new TreeSet<IMember>();
	private int opNumber;
	private long lastActionTime;

	public ManagementTree(String file) {
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

	public ManagementTree(String[] data) throws IOException {
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

	public ManagementTree() {
	}
	/*
	 * Insert
	 */

	@Override
	public void insert(IMember member) {
		opNumber = 1;
		long timeBefore = System.currentTimeMillis();
		tree.add(member);
		setLastActionTime(System.currentTimeMillis() - timeBefore);
	}

	/*
	 * Search
	 */
	@Override
	public IMember search(long key) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 0;
		IMember tmp = tree.floor(new Member(key));
		if (tmp != null && 0 == tmp.compareTo(new Member(key))) {
			lastActionTime = System.currentTimeMillis() - timeBefore;
			return tmp;
		}
		lastActionTime = System.currentTimeMillis() - timeBefore;
		return null;
	}

	@Override
	public IMember search(String name, String firstName) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 0;
		for (IMember member : tree) {
			opNumber++;
			if (member.getName().equals(name) && member.getFirstName().equals(firstName)) {
				lastActionTime = System.currentTimeMillis() - timeBefore;
				return member;
			}
		}
		lastActionTime = System.currentTimeMillis() - timeBefore;
		return null;
	}

	/*
	 * Size
	 */
	@Override
	public int size() {
		opNumber = 1;
		return tree.size();
	}

	@Override
	public int size(KindOfSport kindOfSport) {
		int count = 0;
		opNumber = 0;
		long timeBefore = System.currentTimeMillis();
		for (IMember iMember : tree) {
			opNumber++;
			if (iMember.getKindOfSport() == kindOfSport)
				count++;
		}
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return count;
	}

	/*
	 * Delete
	 */

	/*
	 * to Array
	 */
	@Override
	public IMember[] toArray() {
		int index = 0;
		opNumber = 0;
		long timeBefore = System.currentTimeMillis();
		IMember[] array = new IMember[size()];
		for (IMember iMember : tree) {
			opNumber++;
			array[index++] = iMember;
		}
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return array;
	}

	/*
	 * Discipline
	 */
	@Override
	public IMember[] discipline(KindOfSport kindOfSport) {
		int index = 0;
		opNumber = 0;
		long timeBefore = System.currentTimeMillis();
		IMember[] array = new IMember[size(kindOfSport)];
		for (IMember iMember : tree) {
			opNumber++;
			if (iMember != null)
				if (iMember.getKindOfSport() == kindOfSport)
					array[index++] = iMember;
		}
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return array;
	}

	/*
	 * Height
	 */
	@Override
	public int height() {
		return 0;
	}

	/*
	 * Getter Setter
	 */

	@Override
	public int numberOfOperations() {
		return opNumber;
	}

	public int getOpNumber() {
		return opNumber;
	}

	@Override
	public long getLastActionTime() {
		return lastActionTime;
	}

	@Override
	public void setLastActionTime(long lastActionTime) {
		this.lastActionTime = lastActionTime;
	}

}