CategoryMerge v1.1
===================
This was originally witten during the Summer of 2008 by an intern for the Somerset (PA) County Chamber of Commerce. It worked for several years without maintenance. Unfortunately, in the years since, the original source code was lost.

This version is the attempt to recreate CategoryMerge v1.0 in order to fix a rather severe bug found in the program.


# Original Instructions #
## Screen 1 ##
Select a file to open. It must be a .txt file (tab delimited).

Access file --> .txt (tab delimited) file
1. In Access with the appropriate file and query/table open, go to "File" --> "Export..."
2. In the "Save as" box, select "Text Files"
3. Enter a name and click "Save All"
4. Make sure the button by "Delimited ..." is selected
5. Hit "Next"
6. Choose "Tab" as your delimiter
7. In the "Text Qualifier" box, choose "{none}"
8. Hit "Next" -->"Finished"-->"OK"

Excel file --> .txt (tab delimited) file
1. In Excel with the appropriate file open, go to "File" --> "Save As"
2. In the "Save as" box, select "Text (Tab delimited)"
3. Hit "Save" then "Yes"

Note: you can leave this program running and then come back after you have created the text file.

[Open File]

## Screen 2 ##
Enter maximum number of categories per member [     ]
Enter column number of first category [     ]
[] Add all categories of every member to end of entry

Create your member entry.
code	use
<< >>	around your categories
\\ //	around text that appears only if the previous and next categories are present
\a /a	around text that appears only if the previous category is present
\b /b	around text that appears only if the next category is present
Example:
	Before:
	<<1>>
	<<2>>
	<<3>>\\, //<<4>>
	\bFax: /b<<5>>

	After:
	John Doe Services
	Jane Doe, Consultant
	FAX: 999-999-9999

	M & N Computer Sales
	A Division of ABCD Electronics
	General Manager

[Large text area]

[OK]

## Screen 3 ##
[Save File]

## Screen 4 ##
File Saved
[Finish]