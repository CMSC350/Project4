import java.util.*;

public class test6 {
	public static void main(String[] args) {
		String word = "5.6 3 4 1 8.3 0";
		
		String[] str = word.split(" ");

		ArrayList<String> strList = new ArrayList<String>();
		
		for (int x = 0; x < str.length; x++) {
			strList.add(str[x]);
		}
	
		strList.remove("1");
		strList.remove("0");

		Iterator<String> iterate = strList.iterator();
		
		while (iterate.hasNext()) {
			System.out.print(iterate.next() + "x^" + iterate.next() + " + " + iterate.next() + "x + " + iterate.next());
		}
	}
}
