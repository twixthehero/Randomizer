package randomizer.main;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class VocabUnit
{
	private File unitLocation;
	private String name;
	private ArrayList<VocabSlide> slides;
	
	public VocabUnit(File file)
	{
		unitLocation = file;
		name = unitLocation.getName();
		initSlides();
	}
	
	public String getName()
	{
		return name;
	}
	
	private void initSlides()
	{
		if (unitLocation.listFiles() == null)
		{
			return;
		}
		else
		{
			File[] slides = unitLocation.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File arg0, String arg1)
				{
					return arg1.contains("");
				}
			});
			
			for (int i = 0; i < slides.length; i++)
			{
				
			}
		}
	}
	
	public ArrayList<VocabSlide> getSlides()
	{
		return slides;
	}
}