package exceptionsPackage_mouse;

public class MazeReadingException extends Exception{

	public MazeReadingException(String fileName, int lineInvolved, String errorMessage) {
		System.out.println("Error at line " + lineInvolved + " in file \""+ fileName + "\"");
		
		System.out.println(errorMessage);
		
		
		
		
	}
	
	
	
	
	
}
