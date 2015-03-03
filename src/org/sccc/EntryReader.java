package org.sccc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EntryReader {

	private final File dataFile;
	private final int startingCategoriesColumn;

	public EntryReader(final File dataFile, final int startingCategoriesColumn) {
		this.dataFile = dataFile;
		this.startingCategoriesColumn = startingCategoriesColumn;
	}

	public List<Entry> read() throws IOException {
		List<Entry> entries = new LinkedList<>();
		try (
			FileReader fileReader = new FileReader(dataFile);
			BufferedReader reader = new BufferedReader(fileReader); ) {

			reader.readLine(); // skip line 1, the column headers
			while(reader.ready()) {
				String line = reader.readLine();
				String[] fields = line.split("\t");
				Entry entry = new Entry();
				for (String field : fields) {
					if (field.startsWith("\"")) {
						field = field.substring(1, field.length() - 1);
					}
					field = field.trim();
					entry.add(field);
				}
				entry.setCategories(entry.subList(startingCategoriesColumn, entry.size()));
				entries.add(entry);
			}
		}
		return entries;
	}
}
