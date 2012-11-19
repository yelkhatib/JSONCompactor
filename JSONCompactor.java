import java.io.*;
import java.util.regex.Pattern;
import java.lang.System;

public class JSONCompactor {
	public static void comapctJSONFile(String inputFilename, String outputFilename) {
		try {
			FileInputStream fis = new FileInputStream(inputFilename);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFilename, false));

			String	line="", carryToNextLine="";
			int numberOfLines=0;
			long sizeOfOriginal=0, sizeOfCompacted=0;

			System.out.println("Started compacting...");
			long startTime = System.currentTimeMillis();

			while((line = in.readLine()) != null) {
				numberOfLines++;
				sizeOfOriginal += line.length();
				if (line.length()==1) {
					carryToNextLine = line.replaceAll("\\s","");
				} else {
					line = line.replaceAll(":\\s\"",":\"");
					line = line.replaceAll(",\\s",",");
					line = line.replaceAll("\\s,",",");
					line = line.replaceAll("\\s\\],\\s\\[\\s","\\],\\[");
					line = line.replaceAll("\\s\\[","\\[");
					line = line.replaceAll("\\s\\]","\\]");
					line = line.replaceAll("\\[\\s","\\[");
					line = line.replaceAll("\\]\\s","\\]");
					line = line.replaceAll("\\s\\{","\\{");
					line = line.replaceAll("\\s\\}","\\}");
					line = line.replaceAll("\\{\\s","\\{");
					line = line.replaceAll("\\}\\s","\\}");
					
					sizeOfCompacted += (carryToNextLine+line).length();
					out.write(carryToNextLine+line);
					carryToNextLine = "";
				}
			}
			in.close();
			out.close();

			System.out.printf("Compacted %d lines in %d seconds.\tCompression ratio: %.2f%%\n", numberOfLines, (System.currentTimeMillis()-startTime)/1000, 100-(float)100*sizeOfCompacted/sizeOfOriginal);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException\n"+e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException\n"+e.getMessage());
		}
	}


	public static void main(String args[]) {
		if ((args.length<2)||(args[0]=="--help")) {
			System.err.println("Usage:");
	        System.err.println("\tArgument 1 = input JSON file");
	        System.err.println("\tArgument 2 = output JSON file (will be overwritten)");
	        System.exit(1);
		}
		comapctJSONFile(args[0], args[1]);
	}
}