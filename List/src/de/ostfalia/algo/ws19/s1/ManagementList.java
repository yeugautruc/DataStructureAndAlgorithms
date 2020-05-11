package de.ostfalia.algo.ws19.s1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collector;

import de.ostfalia.algo.ws19.base.*;

public class ManagementList implements IManagement {
	private LinkedList<IMember> list = new LinkedList<IMember>();
	private int opNumber;
	private long lastActionTime;

	public ManagementList(String file) throws IOException {
		int tmpOperation = 0;
		File txtFile = new File(file);
		FileReader fr = new FileReader(txtFile);
		BufferedReader br = new BufferedReader(fr);
		String data = br.readLine();
		if (file != null) {
			long timeBefore = System.currentTimeMillis();
			while (data != null) {
				this.insert(new Member(data));
				tmpOperation++;
				data = br.readLine();
			}
			opNumber = ++tmpOperation;
			setLastActionTime(System.currentTimeMillis() - timeBefore);
		}
		br.close();
		fr.close();
	}

	public ManagementList(String[] data) throws IOException {
		int tmpOperation = 1;
		long timeBefore = System.currentTimeMillis();
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null && data[i].length() != 0) {
				this.insert(new Member(data[i]));
				tmpOperation++;
			}
		}
		opNumber = ++tmpOperation;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
	}

	public ManagementList() {
	}

	@Override
	public int size() {
		opNumber = 0;
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		return list.size();

	}

	@Override
	public void insert(IMember member) {
		opNumber = 1;
		list.add(member);
	}

	@Override
	public IMember search(long key) {
		opNumber = 0;
		for (IMember iMember : list) {
			opNumber++;
			if (iMember.getKey() == key)
				return iMember;
		}
		return null;
	}

	@Override
	public IMember search(String name, String firstName) {
		opNumber = 0;
		for (IMember iMember : list) {
			opNumber++;
			if (iMember.getName() == name && iMember.getFirstName() == firstName)
				return iMember;
		}
		return null;
	}

	@Override
	public int size(KindOfSport kindOfSport) {
		int output = 0;
		opNumber = 0;
		for (IMember iMember : list) {
			opNumber++;
			if (iMember.getKindOfSport() == kindOfSport)
				output++;
		}
		return output;
	}

	@Override
	public IMember[] discipline(KindOfSport kindOfSport) {
		int index = 0;
		opNumber = 0;
		IMember[] arr = new IMember[size(kindOfSport)];
		for (IMember iMember : list) {
			opNumber++;
			if (iMember.getKindOfSport() == kindOfSport)
				arr[index++] = iMember;
		}
		return arr;
	}

	@Override
	public IMember[] toArray() {
		IMember[] arr = new IMember[size()];
		int index = 0;
		opNumber = 0;
		for (IMember iMember : list) {
			opNumber++;
			arr[index++] = iMember;
		}
		return arr;
	}

	@Override
	public int numberOfOperations() {
		return opNumber;
	}

	public long getLastActionTime() {
		return lastActionTime;
	}

	public void setLastActionTime(long lastActionTime) {
		this.lastActionTime = lastActionTime;
	}
}