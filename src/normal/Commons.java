package normal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Commons {
	public static long startTime,endTime;

	public static void startTimer() {
		startTime=System.currentTimeMillis();
	}
	
	//time to perform operation in miliseconds
	public static long getExecutionTime() {
		endTime=System.currentTimeMillis();
		return endTime-startTime;
	}
	
	//memory consumed by this instance in megaBytes
	public static long getMemoryEval() {
		Runtime instance=Runtime.getRuntime();
		return (instance.totalMemory()-instance.freeMemory());
	}
	
	public static String[] inputStringGenerator(String fileLocation,int j,int k) {
		StringBuilder a,b;
		String baseA,baseB;
		Queue<Integer> aBreaks=new LinkedList<>();
		Queue<Integer> bBreaks=new LinkedList<>();
		
        File file = new File(fileLocation);
        
        BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			
	        //read base A:
	        baseA=br.readLine();
	        
	        //read positions to insert the other part of string
	        for(int i=0;i<j;i++) {
	        	aBreaks.add(Integer.parseInt(br.readLine()));
	        }

	        //read base B:
	        baseB=br.readLine();

	        //read positions to insert the other part of string
	        for(int i=0;i<k;i++) {
	        	bBreaks.add(Integer.parseInt(br.readLine()));
	        }

	        //close the reader of the file
	        br.close();
	        
	        a=new StringBuilder(baseA);
	        while(!aBreaks.isEmpty()) {
	        	int startPos=aBreaks.poll();
	        	a.insert(startPos+1,baseA);
	        	baseA=a.toString();
	        }
	        System.out.println(a.toString());
	        
	        b=new StringBuilder(baseB);
	        while(!bBreaks.isEmpty()) {
	        	int startPos=bBreaks.poll();
	        	b.insert(startPos+1,baseB);
	        	baseB=b.toString();
	        }
	        System.out.println(b.toString());
	        return new String[] {a.toString(),b.toString()};
	        
		} catch (Error | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String[] {"",""};
        
	}
	

	public static String firstFifty(String x) {
		return x.length()<50?x:x.substring(0, 50);
	}
	
	public static String lastFifty(String x) {
		return x.length()<50?x:x.substring(x.length()-51);
	}
	
	public static void writeToFile(String a,String b,long executionTime,long executionMemory,String outputFileName) {

	      File myObj = new File(outputFileName);
	      try {
			if (myObj.createNewFile()) {
			    System.out.println("File created: " + myObj.getName());
			  } else {
//			    System.out.println("File already exists.");
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	    FileWriter myWriter;
		try {
			myWriter = new FileWriter(outputFileName);
//			myWriter.write("First 50:\n");
//			myWriter.write("A:\n");
			myWriter.write(firstFifty(a));
			myWriter.write(" ");
			myWriter.write(firstFifty(b));
			myWriter.write("\n");
//			myWriter.write("A:\n");
			myWriter.write(lastFifty(a));
			myWriter.write(" ");
			myWriter.write(lastFifty(b));
			myWriter.write("\n");
			myWriter.write(String.valueOf(executionTime));
			myWriter.write("\n");
			myWriter.write(String.valueOf(executionMemory));
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
