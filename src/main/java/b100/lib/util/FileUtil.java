package b100.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileUtil {
	
	public static void createNewFile(File file) {
		File parent = file.getAbsoluteFile().getParentFile();
		if(!parent.exists()) {
			if(!parent.mkdirs()) {
				throw new RuntimeException("Could not create directory: " + parent.getAbsolutePath());
			}
		}
		
		try {
			file.createNewFile();
		}catch (Exception e) {
			throw new RuntimeException("Creating file: " + file.getAbsolutePath(), e);
		}
	}
	
	public static BufferedReader newBufferedFileReader(File file) {
		try {
			return new BufferedReader(new FileReader(file));	
		}catch (Exception e) {
			throw new RuntimeException("Accessing file for read: " + file.getAbsolutePath(), e);
		}
	}
	
	public static FileWriter newFileWriter(File file) {
		try {
			return new FileWriter(file);	
		}catch (Exception e) {
			throw new RuntimeException("Accessing file for write: " + file.getAbsolutePath(), e);
		}
	}
	
	public static void readFile(File file, IOConsumer<BufferedReader> reader) {
		BufferedReader br = newBufferedFileReader(file);
		try {
			reader.accept(br);
		}catch (Exception e) {
			throw new RuntimeException("Reading file: " + file.getAbsolutePath(), e);
		}finally {
			try {
				br.close();
			}catch (Exception e) {}
		}
	}
	
	public static void writeToFile(File file, IOConsumer<FileWriter> writer) {
		FileWriter fw = newFileWriter(file);
		try {
			writer.accept(fw);
			fw.flush();
		}catch (Exception e) {
			throw new RuntimeException("Writing to file: " + file.getAbsolutePath(), e);
		}finally {
			try {
				fw.close();	
			}catch (Exception e) {}
		}
	}
	
	////////////////////////////////
	
	public static interface IOConsumer<E> {
		
		public void accept(E e) throws IOException;
		
	}
	
	////////////////////////////////
	
	/** Get the file name without the file extension. <br> Returns the full name if the file doesn't have an extension. */
	public static String getFilenameOnly(File file) {
		final String name = file.getName();
		
		final int dot = getFilenameSeperatorIndex(name);
		if(dot == -1) {
			return name;		
		}
		return name.substring(0, dot);
	}
	
	/** Returns a new File object with a different file name, but at the same path, and with the same extension. */
	public static File changeFileNameOnly(File file, String newName) {
		final File parent = file.getAbsoluteFile().getParentFile();
		final String name = file.getName();
		
		final int dot = getFilenameSeperatorIndex(name);
		if(dot == -1) {
			return new File(parent, newName);	
		}
		return new File(parent, newName + name.substring(dot));
	}
	
	/** Returns the index of the last dot, unless a space comes after it */
	public static int getFilenameSeperatorIndex(String filename) {
		int dot = filename.lastIndexOf('.');
		if(filename.lastIndexOf(' ') > dot) {
			return -1;
		}
		return dot;
	}
	
	/* Change the extension of the file, or if the file doesn't have one already, add one */
	public static File changeExtension(File file, String extension) {
		final File parent = file.getAbsoluteFile().getParentFile();
		
		String filename = file.getName();
		String newName = changeExtension(filename, extension);
		
		return new File(parent, newName);
	}

	/* Change the extension of the filename, or if the filename doesn't have one already, add one */
	public static String changeExtension(String filename, String extension) {
		int dot = getFilenameSeperatorIndex(filename);
		if(dot == -1) {
			filename = filename + "." + extension;
		}else {
			filename = filename.substring(0, dot + 1) + extension;
		}
		return filename;
	}
	
}
