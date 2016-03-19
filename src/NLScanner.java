import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class NLScanner{
	Scanner scanner;
	Queue<String> q = new LinkedList<String>();
	public NLScanner(InputStream stream){
		scanner = new Scanner(stream);
	}
	
	public void useDelimiter(String pattern){
		scanner.useDelimiter(pattern);
	}
	public String next(){
		while(q.isEmpty()){
			
			String next = scanner.next();
//			System.out.println("before next"+next+"!!");
			next = next.replaceAll("\\r?\\n", " /n ");
//			System.out.println("after next"+next+"!!");

			String[] arr = next.split(" ");
			for(String s :arr){
				if(!s.trim().isEmpty())
					q.add(s);
			}
		}
		String result = q.remove();
//		System.out.println("result b4 "+result);
		if(result.equals("/n") == false && result.contains("/n")){
//			System.out.println("in here");
			result = result.replaceAll("/n", "");
		}
		result = result.equals("/n") ? "\n" : result;
//		System.out.println("final res: "+result+"..");
		return  result;
	}
}