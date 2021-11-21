package normal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SequenceAlignment {

	public static int[][]OPT;
	public static int gapPenalty=30;
	public static String outputFileName="output.txt";
	

	//mapping character(ACGT) to index to use in alphas
	public static HashMap<Character,Integer> hm;
	
	//ACGT error values
	public static int[][] alpha=new int[][] {	{0,110,48,94},
												{110,0,118,48},
												{48,118,0,110},
												{94,48,110,0}
											};

	//filling the dynamic programming table OPT to generate a path to traverse back from
	public static void sequenceAligner(String a,String b) {
		int aLength=a.length();
		int bLength=b.length();
		int minValue;
		
		OPT=new int[aLength+1][bLength+1];
		
		//filling base values in OPT
		for(int i=0;i<=aLength;i++) {
			OPT[i][0]=i*gapPenalty;
		}
		
		for(int i=0;i<=bLength;i++) {
			OPT[0][i]=i*gapPenalty;
		}
		
		//filling the table
		for(int i=1;i<=aLength;i++) {
			for(int j=1;j<=bLength;j++) {
				minValue=Math.min(
						alpha[hm.get(a.charAt(i-1))][hm.get(b.charAt(j-1))]+OPT[i-1][j-1],
						gapPenalty+OPT[i-1][j]
						);
				OPT[i][j]=Math.min(minValue, gapPenalty+OPT[i][j-1]);
				System.out.print(OPT[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	
	
//	public static String[] inputStringGenerator(String fileLocation,int j,int k) {
//		StringBuilder a,b;
//		String baseA,baseB;
//		Queue<Integer> aBreaks=new LinkedList<>();
//		Queue<Integer> bBreaks=new LinkedList<>();
//		
//        File file = new File(fileLocation);
//        
//        BufferedReader br;
//		try {
//			br = new BufferedReader(new FileReader(file));
//			
//	        //read base A:
//	        baseA=br.readLine();
//	        
//	        //read positions to insert the other part of string
//	        for(int i=0;i<j;i++) {
//	        	aBreaks.add(Integer.parseInt(br.readLine()));
//	        }
//
//	        //read base B:
//	        baseB=br.readLine();
//
//	        //read positions to insert the other part of string
//	        for(int i=0;i<k;i++) {
//	        	bBreaks.add(Integer.parseInt(br.readLine()));
//	        }
//
//	        //close the reader of the file
//	        br.close();
//	        
//	        a=new StringBuilder(baseA);
//	        while(!aBreaks.isEmpty()) {
//	        	int startPos=aBreaks.poll();
//	        	a.insert(startPos+1,baseA);
//	        	baseA=a.toString();
//	        }
//	        System.out.println(a.toString());
//	        
//	        b=new StringBuilder(baseB);
//	        while(!bBreaks.isEmpty()) {
//	        	int startPos=bBreaks.poll();
//	        	b.insert(startPos+1,baseB);
//	        	baseB=b.toString();
//	        }
//	        System.out.println(b.toString());
//	        return new String[] {a.toString(),b.toString()};
//	        
//		} catch (Error | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return new String[] {"",""};
//        
//	}
	
	public static String[] getSequenceAlignment(String a,String b) {
		int i=a.length();
		int j=b.length();
		StringBuilder sequenceA=new StringBuilder();
		StringBuilder sequenceB=new StringBuilder();
		while(i>0&&j>0) {
//			System.out.println("Here with "+i+" and "+j);
			//we chose to pair them
			if(OPT[i][j]==OPT[i-1][j-1]+alpha[hm.get(a.charAt(i-1))][hm.get(b.charAt(j-1))]) {
//				System.out.println("We are matching "+a.charAt(i-1)+" with "+b.charAt(j-1));
				sequenceA.append(a.charAt(i-1));
				sequenceB.append(b.charAt(j-1));
				i--;
				j--;
			}
			else if(OPT[i][j]==OPT[i-1][j]+gapPenalty) {
				//match char at i in A with a gap in B
				sequenceA.append(a.charAt(i-1));
				sequenceB.append('_');
				i--;
			}
			else {
				//match char at j in B with a gap in A
				sequenceB.append(b.charAt(j-1));
				sequenceA.append('_');
				j--;
			}
		}
		while(i>0) {
			//make a gap in B
			sequenceA.append(a.charAt(i-1));
			sequenceB.append('_');
			i--;
		}
		while(j>0) {
			sequenceB.append(b.charAt(j-1));
			sequenceA.append('_');
			j--;
		}
		System.out.println("I: "+i+" j: "+j);
		return new String[] {sequenceA.reverse().toString(),sequenceB.reverse().toString()};
	}
//	
//	public static String firstFifty(String x) {
//		return x.length()<50?x:x.substring(0, 50);
//	}
//	
//	public static String lastFifty(String x) {
//		return x.length()<50?x:x.substring(x.length()-51);
//	}
//	
//	public static void writeToFile(String a,String b) {
//
//	      File myObj = new File(outputFileName);
//	      try {
//			if (myObj.createNewFile()) {
//			    System.out.println("File created: " + myObj.getName());
//			  } else {
//			    System.out.println("File already exists.");
//			  }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	      
//
//	    FileWriter myWriter;
//		try {
//			myWriter = new FileWriter(outputFileName);
//			myWriter.write("First 50:\n");
//			myWriter.write("A:\n");
//			myWriter.write(firstFifty(a));
//			myWriter.write("B:\n");
//			myWriter.write(firstFifty(b));
//			myWriter.write("Last 50:\n");
//			myWriter.write("A:\n");
//			myWriter.write(lastFifty(a));
//			myWriter.write("B:\n");
//			myWriter.write(lastFifty(b));
//			myWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
	public static void initialize() {
		hm=new HashMap<>();
		hm.put('A',0);
		hm.put('C',1);
		hm.put('G',2);
		hm.put('T',3);
	}
	
	public static void main(String[] args) {
		Commons.startTimer();
		initialize();
		// TODO Auto-generated method stub
		String inputFileLocation=args[0];
		int j=Integer.parseInt(args[1]);
		int k=Integer.parseInt(args[2]);
		String[] inputStrings=Commons.inputStringGenerator(inputFileLocation,j,k);
		sequenceAligner(inputStrings[0],inputStrings[1]);
		String[] answers=getSequenceAlignment(inputStrings[0],inputStrings[1]);
		Commons.writeToFile(answers[0],answers[1],outputFileName);
//		System.out.println(OPT[sequences[0].length()][sequences[1].length()]);
		System.out.println("A: "+answers[0]);
		System.out.println("B: "+answers[1]);
		System.out.println("Exec time:"+Commons.getExecutionTime()+" ms");
		System.out.println("Memory consumed:"+Commons.getMemoryEval()+" bits");
	}

}
