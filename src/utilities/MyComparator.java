package utilities;

import java.util.Comparator;

import entities.Agenda;

public class MyComparator implements Comparator<Agenda> {

	@Override
	public int compare(Agenda p1, Agenda p2) {
		return p1.getName().toUpperCase().compareTo(p2.getName().toUpperCase());
	}
}
