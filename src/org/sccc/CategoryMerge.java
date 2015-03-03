package org.sccc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CategoryMerge {

	private File data;
	private File entryFormat;
	private File output;
	private int maxCategories = 1;
	private int startingCategoryColumn = 0;
	private boolean appendOtherCategories = false;

	public CategoryMerge() {}

	public CategoryMerge withDataFilename(final String filename) {
		data = toInputFile(filename);
		return this;
	}

	public CategoryMerge withEntryFormatFilename(final String filename) {
		entryFormat = toInputFile(filename);
		return this;
	}

	public CategoryMerge withOutputFilename(final String filename) throws IOException {
		output = toOutputFile(filename);
		return this;
	}

	public CategoryMerge withMaxNumCategoriesPerEntry(final int maxCats) {
		maxCategories = maxCats;
		return this;
	}

	public CategoryMerge withStartingCategoryColumnIndex(final int startCol) {
		startingCategoryColumn = startCol - 1;
		return this;
	}

	public CategoryMerge withAppendingOtherCategories(final boolean append) {
		appendOtherCategories = append;
		return this;
	}

	public void run() throws IOException {
		EntryReader reader = new EntryReader(data, startingCategoryColumn);

		// reader will close stream
		List<Entry> entries = reader.read();

		String format = fileContents(entryFormat);
		EntryFormatter formatter = new EntryFormatter(format, appendOtherCategories);

		Map<String, List<Entry>> mapping = new EntryCategorizer().categorize(entries, maxCategories);

		// writer will close stream
		EntryWriter writer = new EntryWriter(output, formatter);
		writer.write(mapping);
	}

	private File toInputFile(final String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			throw new IllegalArgumentException("File doesn't exist: " + file.getAbsolutePath());
		}
		if (!file.isFile()) {
			throw new IllegalArgumentException("File isn't a file: " + file.getAbsolutePath());
		}
		if (!file.canRead()) {
			throw new IllegalArgumentException("File isn't readable: " + file.getAbsolutePath());
		}
		return file;
	}

	private File toOutputFile(final String filename) throws IOException {
		File file = new File(filename);
		file.createNewFile();
		if (!file.isFile()) {
			throw new IllegalArgumentException("File isn't a file: " + file.getAbsolutePath());
		}
		if (!file.canWrite()) {
			throw new IllegalArgumentException("File isn't readable: " + file.getAbsolutePath());
		}
		return file;
	}

	private String fileContents(final File file) throws IOException{
		StringBuilder contents = new StringBuilder();
		try (FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);) {
			
			while (reader.ready()) {
				contents.append(reader.readLine()).append("\n");
			}
		}
		return contents.toString();
	}

	/**
	 * Arg 0 data file
	 * Arg 1 max number of categories
	 * Arg 2 starting category column index (1-indexed)
	 * Arg 3 entry format file
	 * Arg 4 output file
	 * Arg 5 append other categories?
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length < 6) {
			// TODO print help
			System.exit(-1);
		}
		
		new CategoryMerge()
			.withDataFilename(args[0])
			.withMaxNumCategoriesPerEntry(Integer.parseInt(args[1]))
			.withStartingCategoryColumnIndex(Integer.parseInt(args[2]))
			.withEntryFormatFilename(args[3])
			.withOutputFilename(args[4])
			.withAppendingOtherCategories(Boolean.parseBoolean(args[5]))
			.run();
	}
}
