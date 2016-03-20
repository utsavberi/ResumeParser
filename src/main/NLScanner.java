package main;
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
	public boolean hasNext(){
		return scanner.hasNext();
	}
	public String next(){
		while(q.isEmpty()){
			
			String next = scanner.next();
			next = next.replaceAll("\\r?\\n", " /n ");
			String[] arr = next.split(" ");
			for(String s :arr){
				if(!s.trim().isEmpty())
					q.add(s);
			}
		}
		String result = q.remove();
		if(result.equals("/n") == false && result.contains("/n")){
			result = result.replaceAll("/n", "");
		}
		result = result.equals("/n") ? "\n" : result;
		return  result;
	}
}