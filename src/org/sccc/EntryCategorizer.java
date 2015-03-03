package org.sccc;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class EntryCategorizer {

	public EntryCategorizer() {}

	public SortedMap<String, List<Entry>> categorize(final List<Entry> entries, final int maxCategories) {
		SortedMap<String, List<Entry>> result = new TreeMap<>();
		for (Entry entry : entries) {
			int count = 0;
			for (String category : entry.getCategories()) {
				List<Entry> others = result.get(category);
				if (others == null) {
					others = new LinkedList<>();
					result.put(category, others);
				}
				others.add(entry);

				count++;
				if (count >= maxCategories) {
					break;
				}
			}
		}
		return result;
	}
}
