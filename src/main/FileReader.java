package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * The Class FileReader.
 * 
 * @author Gwindow
 */
public final class FileReader {
	public final ArrayList<String> readBarcodes(final String path) throws IOException {
		return readBarcodes(new File(path));
	}

	public final ArrayList<String> readBarcodes(final File file) throws IOException {
		// create a new list to hold strings
		final ArrayList<String> list = new ArrayList<String>();
		// create a fileinputstream
		final FileInputStream fstream = new FileInputStream(file);
		// Get the object of DataInputStream
		final DataInputStream in = new DataInputStream(fstream);
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String master = "";
		String strLine;
		// while the file has lines loop through and read each one
		while ((strLine = br.readLine()) != null) {
			master = master.concat(strLine);
		}
		in.close();
		final StringTokenizer tokenizer = new StringTokenizer(master, ",");
		while (tokenizer.hasMoreTokens()) {
			list.add(tokenizer.nextToken().trim());
		}
		return list;
	}
}
