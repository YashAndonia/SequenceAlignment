package normal;
//pg 288 Algorithm Design
import java.util.ArrayList;
import java.util.HashMap;

public class SequenceAlignmentMemoryEfficient {
	public static int[][] OPT;
	//ACGT error values
	public static int[][] alpha=new int[][] {	{0,110,48,94},
												{110,0,118,48},
												{48,118,0,110},
												{94,48,110,0}
											};

	public static int gapPenalty=30;

	//mapping character(ACGT) to index to use in alphas
	public static HashMap<Character,Integer> hm;
	
	public static int[] memoryEfficientSequenceAligner(String a,String b) {
		int aLength=a.length();
		int bLength=b.length();
		int minValue;
		int[] ans=new int[aLength+1];
		OPT=new int[aLength+1][2];
		
		//base case setup:
		for(int i=0;i<=aLength;i++) {
			OPT[i][0]=i*gapPenalty;
		}
//		OPT[0][1]=gapPenalty;
		
		
		for(int j=1;j<=bLength;j++) {
			OPT[0][1]=j*gapPenalty;
			
			for(int i=1;i<=aLength;i++) {
				//cases of adding gap
				minValue=Math.min(OPT[i][0]+gapPenalty, OPT[i-1][1]+gapPenalty);

				//case of selecting pairing between the letters
				OPT[i][1]=Math.min(minValue, OPT[i-1][0]+alpha[hm.get(a.charAt(i-1))][hm.get(b.charAt(j-1))]);
			}
			
			//swap the columns
			for(int i=0;i<=aLength;i++) {
				OPT[i][0]=OPT[i][1];
				ans[i]=OPT[i][0];
//				System.out.print(OPT[i][0]+" ");
			}
			
		}
		return ans;
	}
	
	public static int getMinimizingIndex(int[]a,int[]b) {
		if(a.length!=b.length) {
			System.out.println("The columns are not of the same length");
			return -1;
		}
		else {
			int n=a.length;
			int minIdx=0;
			int minVal=Integer.MAX_VALUE;
			for(int i=0;i<n;i++) {
				if((a[i]+b[n-i-1])<minVal) {
					minVal=a[i]+b[n-i-1];
					minIdx=i;
				}
			}
			return minIdx;
		}
	}

	public static void divideAndConquerAlignment(String a,String b,ArrayList<Integer[]> ans) {
		System.out.println("here with "+a+" and "+b+" and ans:");
		for(Integer[]x:ans) {
			System.out.println(x[0]+" "+x[1]);
		}
		
		if(a.length()==1&&b.length()==1) {
			
		}
		
		int m=a.length();
		int n=b.length();
		StringBuilder sb;

		//handle case of doing sequence alignment of a,b in the void main itself
//		if(m<=2||n<=2) {
//			SequenceAlignment.sequenceAligner(a, b);
//		}
		int[] leftCombination=memoryEfficientSequenceAligner(a,b.substring(1,b.length()/2));
		sb=new StringBuilder(a);
		int[] rightReverseCombination=memoryEfficientSequenceAligner(sb.reverse().toString(),new StringBuilder(b.substring(b.length()/2)).reverse().toString());
		int q=getMinimizingIndex(leftCombination,rightReverseCombination);
		ans.add(new Integer[] {q,n/2});

		divideAndConquerAlignment(a.substring(0,q), b.substring(0,n/2), ans);
		divideAndConquerAlignment(a.substring(q,m), b.substring(n/2,n), ans);
	}

	public static String[] divideAndConquerAlignment2(String a,String b) {
		System.out.println("We are here with Strings "+a+" "+b);
		if(a.length()<2||b.length()<2) {
			SequenceAlignment.initialize();
			SequenceAlignment.sequenceAligner(a, b);
			return SequenceAlignment.getSequenceAlignment(a, b);			
		}
		//solve base case
//		if(a.length()==1&&b.length()==1) {
//			return new String[] {a,b};
//		}
//		if(a.length()==1&&b.length()==0) {
//			//gap in b
//			return new String[] {a,"_"};
//		}
//		if(a.length()==0&&b.length()==1) {
//			//gap in b
//			return new String[] {"_",b};
//		}
//		if(a.length()==0&&b.length()==0) {
//			//gap in b
//			return new String[] {"_","_"};
//		}
//		if(a.length()==0&&b.length()>1) {
//			StringBuilder sb=new StringBuilder();
//			for(int i=0;i<b.length();i++) {
//				sb.append("_");
//			}
//			return new String[] {sb.toString(),b};
//		}
//		if(b.length()==0&&a.length()>1) {
//			StringBuilder sb=new StringBuilder();
//			for(int i=0;i<a.length();i++) {
//				sb.append("_");
//			}
//			return new String[] {a,sb.toString()};
//		}
		
		int m=a.length();
		int n=b.length();
		StringBuilder sb;

		//handle case of doing sequence alignment of a,b in the void main itself
//		if(m<=2||n<=2) {
//			SequenceAlignment.sequenceAligner(a, b);
//		}
		int[] leftCombination=memoryEfficientSequenceAligner(a,b.substring(0,b.length()/2));
		sb=new StringBuilder(a);
		int[] rightReverseCombination=memoryEfficientSequenceAligner(sb.reverse().toString(),new StringBuilder(b.substring(b.length()/2)).reverse().toString());
		int q=getMinimizingIndex(leftCombination,rightReverseCombination);
		
		System.out.println("We have q as "+q);
		String[] leftAns=divideAndConquerAlignment2(a.substring(0,q), b.substring(0,n/2));
		String[] rightAns=divideAndConquerAlignment2(a.substring(q,m), b.substring(n/2,n));

		//returning appended terms [A,B]
		return new String[] {leftAns[0]+rightAns[0],leftAns[1]+rightAns[1]};
	

	}
	
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
		ArrayList<Integer[]> pathToAns=new ArrayList<>();
//		divideAndConquerAlignment(inputStrings[0], inputStrings[1], pathToAns);
		String[] answer=divideAndConquerAlignment2(inputStrings[0], inputStrings[1]);
		System.out.println("A: "+answer[0]);
		System.out.println("B: "+answer[1]);
//		for(Integer[] path:pathToAns) {
//			System.out.print(path[0]+" "+path[1]);
//			System.out.println("");
//		}
//		memoryEfficientSequenceAligner(inputStrings[0], inputStrings[1]);
//		String[] answers=divideAndConquerAlignment(inputStrings[0],inputStrings[1]);
//		System.out.println(getMinimizingIndex(new int[] {1,2,3,4},new int[] {4,7,2,9}));
		
		System.out.println("Time:"+Commons.getExecutionTime()+" ms");
		System.out.println("Memory:"+Commons.getMemoryEval());
	}

}
