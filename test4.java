public class test4 {
	static class Node
	{
	    char first_coef;
	    Node next;
	}
	
	static Node populate(char data) {
		Node newNode = new Node();
		newNode.first_coef = data;
		newNode.next = null;
		return newNode;
	}
	
	static Node stringToLinkList(String inText, Node head) {
		
		head = populate(inText.charAt(0));
		Node curr = head;

		int loopPass = 1;
		
		while (loopPass < inText.length()) {
			curr.next = populate(inText.charAt(loopPass));
			curr = curr.next;
			loopPass++;

		}
		return head;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder build = new StringBuilder();
		String inText = "5.6 3 4 1 8.3 0";
		String formula = "x^ + x + ";
		//5.6x^3+4x+8.3

//		for (String x : inText.split(" 1")){
//			build.append(x);
//		}
//		String str = build.toString();
//		System.out.println(str);
		
		Node head = null;
	    Node tail = null;
		head = stringToLinkList(inText, head);
		tail = stringToLinkList(formula, tail);
	    Node curr = head;
	    Node cur1 = tail;

	    while (curr != null && cur1 != null) {
	    	
	    	if (!Character.isWhitespace(curr.first_coef)) {
	    		System.out.print(curr.first_coef);
	    		curr = curr.next;
		    	if (Character.isWhitespace(curr.first_coef)) {
		    		System.out.print(cur1.first_coef);
		    		cur1 = cur1.next;
		    	}
	    	}
	    	else {
	    		System.out.print(cur1.first_coef);
	    		cur1 = cur1.next;
			    System.out.print(curr.first_coef);
			    curr = curr.next;
	    	}
	    }		
	}
}
