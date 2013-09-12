package randomizer.main;

import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		initFolderStructure();
		
		new Graphics();
	}
	
	private static void initFolderStructure()
	{
		if (new File("lists").mkdir())
			System.out.println("Student lists folder missing...created!");
		
		if (new File("sounds").mkdir())
			System.out.println("Sounds folder missing...created!");
		
		if (new File("vocab").mkdir())
			System.out.println("Vocab folder missing...created!");
		
		if (new File("questions").mkdir())
			System.out.println("Question lists folder missing...created!");
	}
}