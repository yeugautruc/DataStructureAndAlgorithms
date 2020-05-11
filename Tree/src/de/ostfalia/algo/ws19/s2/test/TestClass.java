package de.ostfalia.algo.ws19.s2.test;

import java.io.IOException;

import de.ostfalia.algo.ws19.base.IManagement;
import de.ostfalia.algo.ws19.base.KindOfSport;
import de.ostfalia.algo.ws19.s2.Management;

public class TestClass {

	public static void main(String[] args) throws IOException {
		
		IManagement mTest = new Management("Materialien/Mitglieder10000.txt");
		System.out.println("Insert: number of Operation "+mTest.numberOfOperations());
		System.out.println("Insert LaufZeit: "+ mTest.getLastActionTime());
		mTest.search("Mueller","Bernd");
		
		System.out.println("Search: number of Operation "+mTest.numberOfOperations());
		System.out.println("Search LaufZeit: "+ mTest.getLastActionTime());
		KindOfSport sport = KindOfSport.valueOf("TANZEN");
		mTest.size(sport);
		System.out.println("Tranversieren 1, Size: number of Operation "+mTest.numberOfOperations());
		System.out.println("T1 LaufZeit: "+ mTest.getLastActionTime());
		mTest.discipline(sport);
		System.out.println("Tranversieren 2, Discipline: number of Operation "+mTest.numberOfOperations());
		System.out.println("T2 LaufZeit: "+ mTest.getLastActionTime());
	}

}
