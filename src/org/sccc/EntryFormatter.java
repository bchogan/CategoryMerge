package org.sccc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Most of the logic in the class was adapted from the decompilation of a v1 jar.
 */
public class EntryFormatter {

	private static final int COL_TAG = 0;
	private static final int CONDITIONAL_TAG = 1;
	private static final int NO_TAG = 4;

	private final List<String> formats;
	private final boolean listOtherCategories;

	public EntryFormatter(final String sourceFormat, final boolean listOtherCategories) {
		this.formats = compile(sourceFormat.trim());
		this.listOtherCategories = listOtherCategories;
	}
	
	private List<String> compile(final String source) throws NumberFormatException {
		List<String> result = new ArrayList<>();
		int state = NO_TAG;
		int j = 0;
		for (int k = 0; k < source.length(); k++)
		{
			char m = source.charAt(k);
			switch (state)
			{
			case COL_TAG: 
				if ((m == '>') && (source.charAt(k + 1) == '>'))
				{
					state = NO_TAG;
					k++;
					result.add(source.substring(j, k + 1));
					j = k + 1;
				}
				break;
			case CONDITIONAL_TAG: 
				if (m == '/') {
					switch (source.charAt(k + 1))
					{
					case '/': 
					case 'a': 
					case 'b': 
						k++;
						state = NO_TAG;
						result.add(source.substring(j, k + 1));
						j = k + 1;
					}
				}
				break;
			default: 
				switch (m)
				{
				case '\n': 
					result.add(source.substring(j, k));
					result.add(source.substring(k, k + 1));
					j = k + 1;
					break;
				case '<': 
					if (source.charAt(k + 1) == '<')
					{
						state = 0;
						k++;
						if (j > 0) {
							result.add(source.substring(j, k - 1));
						}
						j = k - 1;
					}
					break;
				case '\\': 
					if (source.charAt(k + 1) == '\\')
					{
						state = CONDITIONAL_TAG;
						k++;
						if ((j > 0) || (k - 1 > j)) {
							result.add(source.substring(j, k - 1));
						}
						j = k - 1;
					}
					break;
				default: 
					state = NO_TAG;
				}
				break;
			}
		}
		result.add(source.substring(j, source.length()));
		if (!(result.get(result.size() - 1)).equals("\n")) {
			result.add("\n");
		}
		return result;
	}

	public String format(final Entry entry, final String currentCategory) {
		String result = "";
		int i = 0;
		int j = 0;
		for (int k = 0; k < formats.size(); k++) {
			String token = formats.get(k);
			if (token.startsWith("<<"))
			{
				int column = Integer.parseInt(token.substring(2, token.length() - 2)) - 1;
				if (column < entry.size())
				{
					entry.get(column).trim();
					if (entry.get(column).equals(""))
					{
						i = 0;
					}
					else
					{
						i = 1;
						result = result + entry.get(column);
						j = 1;
					}
				}
			}
			else
			{
				if (token.startsWith("\\\\"))
				{
					if (i != 0)
					{
						String secondaryToken = null;
						for (int m = k; m < formats.size(); m++) {
							if (formats.get(m).startsWith("<<")) {
								secondaryToken = formats.get(m);
								break;
							}
						}
						int column = Integer.parseInt(secondaryToken.substring(2, secondaryToken.length() - 2)) - 1;
						if (!entry.get(column).equals("")) {
							result = result + token.substring(2, token.length() - 2);
						}
					}
				}
				else if (token.startsWith("\\a"))
				{
					if (i != 0) {
						result = result + token.substring(2, token.length() - 2);
					}
				}
				else if (token.startsWith("\\b"))
				{
					String secondaryToken = null;
					for (int m = k; m < formats.size(); m++) {
						if (formats.get(m).startsWith("<<")) {
							secondaryToken = formats.get(m);
							break;
						}
					}
					int n = Integer.parseInt(secondaryToken.substring(2, secondaryToken.length() - 2));
					if ((n - 1 < entry.size()) && 
						(!entry.get(n - 1).equals(""))) {
						result = result + token.substring(2, token.length() - 2);
					}
				}
				else if ((token.equals("\n")) && (j != 0))
				{
					result = result + token;
					j = 0;
				}
				else if (i != 0)
				{
					result = result + token;
				}
			}
		}

		if (listOtherCategories) {
			if (!result.endsWith("\n")) {
				result = result + "\n";
			}

			List<String> otherCategories = new ArrayList<>(entry.getCategories());
			otherCategories.remove(currentCategory);
			String cats = Arrays.toString(otherCategories.toArray());
			result += cats.substring(1, cats.length() - 1) + "\n";
		}
		
		return result;
	}

}
