package librarysystem;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
	      EventQueue.invokeLater(() -> {
			  	LibrarySystem.getInstance().init();
	         });
	   }
}
