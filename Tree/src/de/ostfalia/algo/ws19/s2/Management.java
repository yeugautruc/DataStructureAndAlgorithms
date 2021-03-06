package de.ostfalia.algo.ws19.s2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import de.ostfalia.algo.ws19.base.*;
import de.ostfalia.algo.ws19.s2.Node;

public class Management implements IManagement {
	private Node header;
	private int opNumber;
	private long lastActionTime;

	public Management(String file) throws IOException {
		int tmpOperation = 1;
		File txtFile = new File(file);
		FileReader fr = new FileReader(txtFile);
		BufferedReader br = new BufferedReader(fr);
		String data = br.readLine();
		if (file != null) {
			long timeBefore = System.currentTimeMillis();
			while (data != null) {
				this.insert(new Member(data));
				tmpOperation+=opNumber;
				data = br.readLine();
			}
			 opNumber = ++tmpOperation;
			setLastActionTime(System.currentTimeMillis() - timeBefore);
		}
		br.close();
		fr.close();
	}

	public Management(String[] data) throws IOException {
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

	public Management() {
	}

	@Override
	public int size() {
		Node temp = header;
		opNumber = 0;
		int output = 0;
		while (temp != null) {
			temp = temp.getNext();
			output++;
			opNumber++;
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		return output;
	}

	@Override
    public void insert(IMember member) {
        opNumber=1;
        Node temp = header;
        if (header == null) {
            header = new Node(member, temp);
        } else if(temp.getMember()!=null&&header.getMember().getKey()>member.getKey()) {
            header = new Node(member, temp);
            opNumber++;
        }

        else {
            while (temp.getNext() != null && temp.getNext().getMember() != null
                    && temp.getNext().getMember().getKey() < member.getKey()) {
                temp = temp.getNext();
                opNumber++;
            }
            temp.setNext(new Node(member, temp.getNext()));
            opNumber++;
        }
    }

	@Override
	public IMember search(long key) {
		long timeBefore = System.currentTimeMillis();
		Node temp = header;
		opNumber = 0;
		while (temp != null && temp.getMember().getKey() != key) {
			temp = temp.getNext();
			opNumber++;
		}
		opNumber = (opNumber != 0) ? opNumber : 1;
		if (temp == null) {
			setLastActionTime(System.currentTimeMillis() - timeBefore);
			return null;
		} else {
			setLastActionTime(System.currentTimeMillis() - timeBefore);
			return temp.getMember();
		}
	}

	@Override
	public IMember search(String name, String firstName) {
		long timeBefore = System.currentTimeMillis();
		Node temp = header;
		opNumber = 0;
		while (temp != null
				&& !(temp.getMember().getName().equals(name) && temp.getMember().getFirstName().equals(firstName))) {
			temp = temp.getNext();
			opNumber++;
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		if (temp == null) {
			setLastActionTime(System.currentTimeMillis() - timeBefore);
			return null;
		} else {
			setLastActionTime(System.currentTimeMillis() - timeBefore);
			return temp.getMember();
		}
	}

	@Override
	public int size(KindOfSport kindOfSport) {
		long timeBefore = System.currentTimeMillis();
		int output = 0;
		opNumber = 0;
		Node temp = header;
		while (temp != null) {
			opNumber++;
			if (temp.getMember().getKindOfSport() == kindOfSport) {
				output++;
			}
			temp = temp.getNext();
		}
		opNumber = (opNumber != 0) ? opNumber : 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return output;
	}

	@Override
	public IMember[] discipline(KindOfSport kindOfSport) {
		long timeBefore = System.currentTimeMillis();
		Node tmp = header;
		IMember[] arr = new IMember[size(kindOfSport)];
		int stage = 0;
		opNumber = 0;
		while (tmp != null) {
			if (tmp.getMember().getKindOfSport() == kindOfSport) {
				arr[stage++] = tmp.getMember();
			}
			tmp = tmp.getNext();
			opNumber++;
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
		setLastActionTime(System.currentTimeMillis() - timeBefore);
		return arr;
	}

	@Override
	public IMember[] toArray() {
		Node temp = header;
		IMember[] arr = new IMember[size()];
		int index = 0;
		opNumber = 0;
		while (temp != null) {
			arr[index++] = temp.getMember();
			temp = temp.getNext();
			opNumber++;
		}
		opNumber = (opNumber != 0) ? opNumber++ : 1;
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