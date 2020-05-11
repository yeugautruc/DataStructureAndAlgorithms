package de.ostfalia.algo.ws19.s4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import de.ostfalia.algo.ws19.base.IManagement;
import de.ostfalia.algo.ws19.base.KindOfSport;

public class TestZeit {
	public static String fileName = "Materialien/Mitglieder10000.txt";
	public static String searchFile = "Materialien/Schluessel10000_5000.txt";

	public static void main(String[] args) throws IOException {
		IManagement list, map, tree;
		list = new ManagementList(fileName);
		tree = new ManagementTree(fileName);
		map = new ManagementMap(fileName);
		list.search(160113121927l);
		System.out.println("List search 1 key: " + list.getLastActionTime() + " ms");
		tree.search(160113121927l);
		System.out.println("Tree search 1 key: " + tree.getLastActionTime() + " ms");
		map.search(160113121927l);
		System.out.println("Map search 1 key: " + map.getLastActionTime() + " ms");
		int actionTimeList = 0;
		int actionTimeTree = 0;
		int actionTimeMap = 0;
		FileReader fr = new FileReader(searchFile);
		BufferedReader br = new BufferedReader(fr);
		String data;
		while ((data = br.readLine()) != null) {
			list.search(Long.parseLong(data));
			actionTimeList += list.getLastActionTime();
			data = br.readLine();
		}
		br.close();
		fr.close();
		System.out.println("List 10000 key search: " + actionTimeList + " ms");
		fr = new FileReader(searchFile);
		br = new BufferedReader(fr);
		while ((data = br.readLine()) != null) {
			tree.search(Long.parseLong(data));
			actionTimeTree += tree.getLastActionTime();
			data = br.readLine();
		}
		br.close();
		fr.close();
		System.out.println("Tree 10000 key search: " + actionTimeTree + " ms");
		fr = new FileReader(searchFile);
		br = new BufferedReader(fr);
		while ((data = br.readLine()) != null) {
			tree.search(Long.parseLong(data));
			actionTimeMap += map.getLastActionTime();
			data = br.readLine();
		}
		br.close();
		fr.close();
		System.out.println("Map 10000 key search: " + actionTimeMap + " ms");
		int traverTimeList = 0;
		int traverTimeTree = 0;
		int traverTimeMap = 0;
		list.discipline(KindOfSport.FUSSBALL);
		System.out.println("Traversieren List: " + list.getLastActionTime() + " ms");
		tree.discipline(KindOfSport.FUSSBALL);
		System.out.println("Traversieren Tree: " + tree.getLastActionTime() + " ms");
		map.discipline(KindOfSport.FUSSBALL);
		System.out.println("Traversieren Map: " + map.getLastActionTime() + " ms");
	}
}
