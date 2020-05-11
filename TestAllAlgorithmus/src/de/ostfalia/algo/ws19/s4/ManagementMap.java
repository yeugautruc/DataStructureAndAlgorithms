package de.ostfalia.algo.ws19.s4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import de.ostfalia.algo.ws19.base.*;

public class ManagementMap implements IManagement {
	private HashMap<Long, IMember> map = new HashMap<>();
	private int opNumber;
	private long lastActionTime;

	public ManagementMap(String file) throws IOException {
		int tmpOperation = 1;
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

	public ManagementMap(String[] data) throws IOException {
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

	public ManagementMap() {
	}

	@Override
	public int size() {
		long timeBefore = System.currentTimeMillis();
		opNumber = 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return map.size();
	}

	@Override
	public void insert(IMember member) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 1;
		map.put(member.getKey(), member);
		setLastActionTime(System.currentTimeMillis() - timeBefore);
	}

	@Override
	public IMember search(long key) {
		long timeBefore = System.currentTimeMillis();
		opNumber = 1;
		IMember temp = map.get(key);
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return temp;
	}

	@Override
	public IMember search(String name, String firstName) {
		opNumber = 0;
		long timeBefore = System.currentTimeMillis();
		for (Entry<Long, IMember> entry : map.entrySet()) {
			opNumber++;
			if (entry.getValue().getName().equals(name) && entry.getValue().getFirstName().equals(firstName)) {
				return entry.getValue();
			}
		}
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return null;
	}

	@Override
	public int size(KindOfSport kindOfSport) {
		long timeBefore = System.currentTimeMillis();
		int output = 0;
		opNumber = 0;
		for (Entry<Long, IMember> entry : map.entrySet()) {
			opNumber++;
			if (entry.getValue().getKindOfSport() == kindOfSport) {
				output++;
			}
		}
		opNumber = (opNumber != 0) ? opNumber : 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return output;
	}

	@Override
	public IMember[] discipline(KindOfSport kindOfSport) {
		IMember[] arr = new IMember[size(kindOfSport)];
		opNumber = 0;
		int index = 0;
		long timeBefore = System.currentTimeMillis();
		for (Entry<Long, IMember> entry : map.entrySet()) {
			opNumber++;
			if (entry.getValue().getKindOfSport() == kindOfSport) {
				arr[index++] = entry.getValue();
			}
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return arr;
	}

	@Override
	public IMember[] toArray() {
		IMember[] arr = new IMember[size()];
		int index = 0;
		opNumber = 0;
		long timeBefore = System.currentTimeMillis();
		for (Entry<Long, IMember> entry : map.entrySet()) {
			opNumber++;
			arr[index++] = entry.getValue();
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
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