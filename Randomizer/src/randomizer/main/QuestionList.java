package randomizer.main;

import java.io.File;
import java.util.ArrayList;

public class QuestionList implements List
{
	private ListType listType = ListType.STUDENT;
	private File file;
	private String name;
	
	private ArrayList<String> questions;
	
	public QuestionList(String f)
	{
		file = new File(f);
		questions = new ArrayList<String>();
		name = file.getName();
	}
	
	public ListType getListType()
	{
		return listType;
	}

	public File getFile()
	{
		return file;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addQuestion(String q)
	{
		questions.add(q);
	}
	
	public void setQuestion(int index, String question)
	{
		questions.set(index, question);
	}
	
	public String getQuestion(int index)
	{
		if (index < 0 || index >= questions.size())
		{
			return "Out of bounds.";
		}
		
		return questions.get(index);
	}
	
	public void clearQuestions()
	{
		questions.clear();
	}
	
	public boolean isEmpty()
	{
		if (questions.isEmpty())
		{
			return true;
		}
		
		return false;
	}
}