package org.sccc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EntryWriter {

	private final File file;
	private final EntryFormatter formatter;

	public EntryWriter(final File file, final EntryFormatter formatter) {
		this.file = file;
		this.formatter = formatter;
	}

	public void write(final Map<String, List<Entry>> mapping) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter); ) {

			for (Map.Entry<String, List<Entry>> pair : mapping.entrySet()) {
				writer.write(pair.getKey());
				writer.write("\n\n");

				for (Entry entry : pair.getValue()) {
					writer.write(formatter.format(entry, pair.getKey()));
					writer.write("\n");
				}
			}

			writer.flush();
		}
	}
}
