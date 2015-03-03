package org.sccc;

import java.util.ArrayList;
import java.util.List;

public class Entry extends ArrayList<String> {

	private static final long serialVersionUID = 1L;

	private List<String> categories;
	
	public Entry() {
		super();
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(final List<String> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Entry[fields: " + super.toString() + ", categories: " + categories + "]";
	}
}
