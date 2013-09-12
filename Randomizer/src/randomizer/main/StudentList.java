package randomizer.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class StudentList implements List
{
	private ListType listType = ListType.STUDENT;
	private File file;
	private String name;
	
	private ArrayList<String> students;
	
	public StudentList(String f)
	{
		file = new File(f);
		name = file.getName();
		init();
	}
	
	private void init()
	{
		String fileContents = "";
		
		FileInputStream fis = null;
		StringBuffer s = new StringBuffer("");
		
		try
		{
			int cha;
			fis = new FileInputStream(file);
			
			while ((cha = fis.read()) != -1)
			{
				s.append((char) cha);
			}
			
			fis.close();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		fileContents += s.toString();
		
		if (fileContents == "")
		{
			
		}
		else
		{
			int numStudents = 0;
			
			for (int i = 0; i < fileContents.length(); i++)
			{
				if (fileContents.charAt(i) == '\n')
				{
					numStudents++;
				}
			}
			
			numStudents++;
			
			students = new ArrayList<String>(numStudents);
			
			for (int i = 0; i < numStudents; i++)
			{
				students.add("");
			}
			
			int k = 0;
			
			for (int i = 0; i < fileContents.length(); i++)
			{
				if (fileContents.charAt(i) != '\n')
				{
					students.set(k, students.get(k) + fileContents.charAt(i));
				}
				else
				{
					k++;
				}
			}
		}
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
	
	public void addStudent(String s)
	{
		students.add(s);
	}
	
	public void setStudent(int index, String name)
	{
		students.set(index, name);
	}
	
	public String getStudent(int index)
	{
		return students.get(index);
	}
	
	public void clearStudents()
	{
		students.clear();
	}
	
	public boolean isEmpty()
	{
		if (students.isEmpty())
		{
			return true;
		}
		
		return false;
	}

	public ArrayList<String> getStudents()
	{
		return students;
	}
}