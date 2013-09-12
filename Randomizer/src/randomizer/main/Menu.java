package randomizer.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class Menu implements ActionListener
{
	Graphics g;
	Randomizer r;
	
	JMenuBar menuBar;
	JMenu file;
	JMenuItem[] fileItems = new JMenuItem[4];
	JMenuItem newstudentlist, newquestionlist, loadstudentlist, loadquestionlist, loadvocabunit;
	JMenu edit;
	JMenuItem[] editItems = new JMenuItem[2];
	JMenu vocabUs;
	JCheckBoxMenuItem[] vocabItems;
	JMenu view;
	JMenuItem[] viewItems = new JMenuItem[1];
	JMenu about;
	JMenuItem aboutItem1;
	
	private String[] vocabUnits;
	
	private int command; //0 for new student list, 1 for new question list
	
	public Menu(Randomizer rand, Graphics graph, String[] vu)
	{
		r = rand;
		g = graph;
		vocabItems = new JCheckBoxMenuItem[vu.length];
		vocabUnits = vu;
	}
	
	public JMenuBar createMenu()
	{
		menuBar = new JMenuBar();
		
		//FILE
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		
		fileItems[0] = new JMenu("Create");
		fileItems[0].setMnemonic(KeyEvent.VK_C);
		newstudentlist = new JMenuItem("New Student List", KeyEvent.VK_S);
		newstudentlist.addActionListener(this);
		fileItems[0].add(newstudentlist);
		newquestionlist = new JMenuItem("New Question List", KeyEvent.VK_Q);
		newquestionlist.addActionListener(this);
		fileItems[0].add(newquestionlist);
		
		file.add(fileItems[0]);
		
		fileItems[1] = new JMenu("Load");
		fileItems[1].setMnemonic(KeyEvent.VK_L);
		loadstudentlist = new JMenuItem("Student List", KeyEvent.VK_S);
		loadstudentlist.addActionListener(this);
		fileItems[1].add(loadstudentlist);
		loadquestionlist = new JMenuItem("Question Set", KeyEvent.VK_Q);
		loadquestionlist.addActionListener(this);
		fileItems[1].add(loadquestionlist);
		loadvocabunit = new JMenuItem("Vocab Unit", KeyEvent.VK_V);
		loadvocabunit.addActionListener(this);
		fileItems[1].add(loadvocabunit);
		
		file.add(fileItems[1]);
		
		fileItems[2] = new JMenuItem("Clear Questions", KeyEvent.VK_Q);
		fileItems[2].addActionListener(this);
		file.add(fileItems[2]);
		
		fileItems[3] = new JMenuItem("Exit", KeyEvent.VK_E);
		fileItems[3].addActionListener(this);
		file.add(fileItems[3]);
		
		//EDIT
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		menuBar.add(edit);
		
		editItems[0] = new JMenuItem("Edit Student List", KeyEvent.VK_S);
		editItems[0].addActionListener(this);
		edit.add(editItems[0]);
		editItems[1] = new JMenuItem("Edit Question List", KeyEvent.VK_Q);
		editItems[1].addActionListener(this);
		edit.add(editItems[1]);
		
		//VOCAB
		vocabUs = new JMenu("Vocab Units");
		vocabUs.setMnemonic(KeyEvent.VK_U);
		menuBar.add(vocabUs);
		
		for (int i = 0; i < vocabItems.length; i++)
		{
			vocabItems[i] = new JCheckBoxMenuItem(vocabUnits[i]);
			vocabUs.add(vocabItems[i]);
		}
		
		//VIEW
		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		menuBar.add(view);
		
		viewItems[0] = new JMenuItem("Data Location", KeyEvent.VK_L);
		viewItems[0].addActionListener(this);
		view.add(viewItems[0]);
		
		about = new JMenu("About");
		about.setMnemonic(KeyEvent.VK_A);
		menuBar.add(about);
		
		aboutItem1 = new JMenuItem("About", KeyEvent.VK_A);
		aboutItem1.addActionListener(this);
		about.add(aboutItem1);
		
		return menuBar;
	}
	
	public JCheckBoxMenuItem[] getCheckboxes()
	{
		return vocabItems;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		JMenuItem source = (JMenuItem)(arg0.getSource());
		
		if (source.equals(newstudentlist))
		{
			addNewStudentList(); //done
		}
		else if (source.equals(newquestionlist))
		{
			addNewQuestionList(); //done
		}
		else if (source.equals(loadstudentlist))
		{
			loadStudentList();
		}
		/*
		else if (source.equals(loadquestionlist))
		{
			loadQuestionList();
		}
		else if (source.equals(loadvocabunit))
		{
			loadVocabUnit();
		}
		*/
		else if (source.equals(fileItems[2]))
		{
			g.getLoadedQuestionListArea().setText("");
			
			if (!r.getQuestionLists().isEmpty())
			{
				r.getQuestionLists().clear();
			}
			else
			{
				
			}
			
			r.clearAllQuestions();
			
			g.pickedQuestion.setText("");
		}
		else if (source.equals(fileItems[3]))
		{
			System.exit(0);
		}
		else if (source.equals(editItems[0]))
		{
			editStudentList(); //done
		}
		else if (source.equals(editItems[1]))
		{
			editQuestionList(); //finished, NOT VERIFIED
		}
		else if (source.equals(viewItems[0]))
		{
			JFrame f = new JFrame();
			f.setSize(100, 100);
			f.setLayout(new GridLayout(1, 2));
			f.setTitle("Password");
			
			final JPasswordField j = new JPasswordField(10);
			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					char[] input = j.getPassword();
					
					if (input.length >= 0)
					{
						char[] pass = {'a', 'd', 'm', 'i', 'n'};
						boolean c = true;
						
						for (int i = 0; i < pass.length; i++)
						{
							try
							{
								if (input[i] == pass[i])
								{
									
								}
								else
								{
									c = false;
									JOptionPane.showMessageDialog(null, "Incorrect password.\nTry again.", "Error!", JOptionPane.ERROR_MESSAGE);
									break;
								}
							}
							catch (NullPointerException n)
							{
								c = false;
								JOptionPane.showMessageDialog(null, "Incorrect password.\nTry again.", "Error!", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
						
						if (c)
						{
							JOptionPane.showMessageDialog(null, getDataLocation().getAbsolutePath().toString().replaceAll("%20", " "), "Data Location", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else
					{
						
					}
				}
			});
			
			f.add(j);
			f.add(ok);
			
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setVisible(true);
		}
		else if (source.equals(aboutItem1))
		{
			JOptionPane.showMessageDialog(null, "Randomizer 9002\nVersion 3.0\nCopyright Pending (c) 2012\nMax Wright", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			
		}
	}

	public Menu getMenu()
	{
		return this;
	}

	/*******************************
	 * Menu Methods
	 * ****************************/
	public void addNewStudentList()
	{
		final JFrame frame = new JFrame();
		frame.setSize(300, 250);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel listLabel = new JLabel("Student List Name:");
		
		final JTextField listTextField = new JTextField();
		
		JLabel textAreaLabel = new JLabel("Enter each student name on a seperate line.", SwingConstants.LEFT);
		final JTextArea textArea = new JTextArea(7, 20);
		
		JScrollPane scroller = new JScrollPane(textArea);
		textArea.setLineWrap(true);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JButton finish = new JButton("Finish");
		NewList nl = new NewList(frame, listTextField, textArea);
		finish.addActionListener(nl);
		
		panel.add(listLabel);
		panel.add(listTextField);
		panel.add(textAreaLabel);
		panel.add(scroller);
		panel.add(finish);
		frame.add(panel);
		frame.setVisible(true);
		
		command = 0;
	}

	public void addNewQuestionList()
	{
		JFrame frame = new JFrame();
		frame.setSize(300, 250);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel questionLabel = new JLabel("Question Set Name:");
		
		JTextField questionTextField = new JTextField();
		
		JLabel textAreaLabel = new JLabel("Enter each question on a seperate line.", SwingConstants.LEFT);
		final JTextArea textArea = new JTextArea(7, 20);
		
		JScrollPane scroller = new JScrollPane(textArea);
		textArea.setLineWrap(true);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JButton finish = new JButton("Finish");
		NewList nl = new NewList(frame, questionTextField, textArea);
		finish.addActionListener(nl);
		
		panel.add(questionLabel);
		panel.add(questionTextField);
		panel.add(textAreaLabel);
		panel.add(scroller);
		panel.add(finish);
		frame.add(panel);
		frame.setVisible(true);
		
		command = 1;
	}
	
	public void loadStudentList()
	{
		JFileChooser fc = new JFileChooser();
		
		File path = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		String percentsReplaced = path.toString().replaceAll("%20", " ");
		
		int place = 0;
		
		for (int i = percentsReplaced.length() - 1; i >= 0; i--)
		{
			if (percentsReplaced.charAt(i) == File.separatorChar)
			{
				place = i;
				break;
			}
		}
		
		String bleh = percentsReplaced.substring(0, place).replaceAll("%20", " ") + File.separatorChar + "lists";
		fc.setCurrentDirectory(new File(bleh));
		
		int returnValue = fc.showOpenDialog(fc);
		
		File file = null;
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
			
			try
			{
				r.setStudentList(new StudentList(file.getAbsolutePath()));
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		g.getLoadedStudentList().setText("Student List: " + file.getName());
	}
	
	public void loadVocabUnit()
	{
		
	}
	
	public void editStudentList()
	{
		JFileChooser fc = new JFileChooser();
		
		File path = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String bleh = (path.toString()).replaceAll("%20", " ") + File.separatorChar + "lists";
		fc.setCurrentDirectory(new File(bleh));
		
		String fileContents = "";
		
		int returnValue = fc.showOpenDialog(fc);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			final File chosenFile = fc.getSelectedFile();
			
			FileInputStream fis = null;
			StringBuffer s = new StringBuffer("");
			
			try
			{
				int cha;
				fis = new FileInputStream(chosenFile);
				
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
			
			final JFrame frame = new JFrame();
			frame.setSize(300, 250);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			final JTextArea listTextArea = new JTextArea(7, 20);
			listTextArea.setText(fileContents);
			
			JScrollPane scroller = new JScrollPane(listTextArea);
			listTextArea.setLineWrap(true);
			scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			JButton finish = new JButton("Finish");
			finish.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String newEditedStudentList = listTextArea.getText();
					char[] sL = newEditedStudentList.toCharArray();
					
					File newList = new File(chosenFile.toString());
					
					try
					{
						PrintWriter fos = new PrintWriter(newList);
						
						for (int i = 0; i < sL.length; i++)
						{
							if (sL[i] != '\n')
							{
								fos.write(sL[i]);
							}
							else
							{
								fos.println();
							}
						}
						
						fos.close();
								
						frame.dispose();
						
						reloadStudentList(newList);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			});
			
			panel.add(scroller);
			panel.add(finish);
			frame.add(panel);
			frame.setVisible(true);
		}
		else
		{
			
		}
	}
	
	public void editQuestionList()
	{
		JFileChooser fc = new JFileChooser();
		
		File path = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String bleh = (path.toString()).replaceAll("%20", " ") + File.separatorChar + "questions";
		fc.setCurrentDirectory(new File(bleh));
		
		String fileContents = "";
		
		int returnValue = fc.showOpenDialog(fc);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			final File chosenFile = fc.getSelectedFile();
			
			FileInputStream fis = null;
			StringBuffer s = new StringBuffer("");
			
			try
			{
				int cha;
				fis = new FileInputStream(chosenFile);
				
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
			
			final JFrame frame = new JFrame();
			frame.setSize(300, 250);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			final JTextArea listTextArea = new JTextArea(7, 20);
			listTextArea.setText(fileContents);
			
			JScrollPane scroller = new JScrollPane(listTextArea);
			listTextArea.setLineWrap(true);
			scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			JButton finish = new JButton("Finish");
			finish.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String newEditedQuestionList = listTextArea.getText();
					char[] sL = newEditedQuestionList.toCharArray();
					
					File newList = new File(chosenFile.toString());
					
					try
					{
						PrintWriter fos = new PrintWriter(newList);
						
						for (int i = 0; i < sL.length; i++)
						{
							if (sL[i] != '\n')
							{
								fos.write(sL[i]);
							}
							else
							{
								fos.println();
							}
						}
						
						fos.close();
								
						frame.dispose();
						
						reloadAllQuestionLists();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			});
			
			panel.add(scroller);
			panel.add(finish);
			frame.add(panel);
			frame.setVisible(true);
		}
		else
		{
			
		}
	}
	
	public void reloadStudentList(File file)
	{
		FileInputStream fis = null;
		StringBuffer s = new StringBuffer("");
		
		String fileContents = "";
		
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
			
			r.getStudentList().clearStudents();
			
			for (int i = 0; i < numStudents; i++)
			{
				r.getStudentList().addStudent("");
			}
			
			int k = 0;
			
			for (int i = 0; i < fileContents.length(); i++)
			{
				if (fileContents.charAt(i) != '\n')
				{
					r.getStudentList().setStudent(k, r.getStudentList().getStudent(k) + fileContents.charAt(i));
				}
				else
				{
					k++;
				}
			}
		}
		
		JLabel label = g.getLoadedStudentList();
		label.setText("Student List: " + file.getName());
		g.setLoadedStudentList(label);
	}
	
	public void reloadAllQuestionLists()
	{
		if (r.getQuestionLists() == null)
		{
			return;
		}
		
		FileInputStream fis = null;
		StringBuffer s = new StringBuffer("");
		
		String filesContents = "";
		
		int numLists = 0;
		
		for (int i = 0; i < r.getQuestionLists().size(); i++)
		{
			numLists++;
		}
		
		int[] numQuestionInEachList = new int[r.getQuestionLists().size()];
		
		for (int i = 0; i < r.getQuestionLists().size(); i++)
		{
			int cha;
			
			try
			{
				fis = new FileInputStream(r.getQuestionLists().get(i).getFile());
			
				g.getLoadedQuestionListArea().setText(g.getLoadedQuestionListArea().getText() + "\n" + r.getQuestionLists().get(i).getName());
		
				while ((cha = fis.read()) != -1)
				{
					if (cha == '\n')
					{
						numQuestionInEachList[i]++;
					}
					else
					{
					
					}
				}
		
				fis.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			for (int i = 0; i < numLists; i++)
			{
				int cha;
				fis = new FileInputStream(r.getQuestionLists().get(i).getFile());
			
				while ((cha = fis.read()) != -1)
				{
					s.append((char) cha);
				}
			
				fis.close();
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		filesContents += s.toString();
		
		if (filesContents == "")
		{
			
		}
		else
		{
			r.getQuestionLists().clear();
			
			for (int j = 0; j < numQuestionInEachList.length; j++)
			{
				for (int i = 0; i < numQuestionInEachList[j]; i++)
				{
					r.getQuestionLists().get(j).addQuestion("");
				}
			}
			
			int k = 0;
			int j = 0;
			
			for (int i = 0; i < filesContents.length(); i++)
			{
				if (filesContents.charAt(i) != '\n')
				{
					r.getQuestionLists().get(k).setQuestion(j, r.getQuestionLists().get(k).getQuestion(j) + filesContents.charAt(i));
					
					if (numQuestionInEachList[k] == j)
					{
						j = 0;
					}
					else
					{
						j++;
					}
				}
				else
				{
					k++;
				}
			}
		}
	}
	/*
	private void reloadVocabUnits() // MUST FINISH!!!
	{
		if (r.getVocabLists() == null)
		{
			return;
		}
		else
		{
			r.getVocabLists().clear();
		}
	}
	*/
	/*******************************
	 * Classes
	 ******************************/
	private class NewList implements ActionListener
	{
		private JFrame fr;
		private JTextField tf;
		private JTextArea ta;
		
		public NewList(JFrame frame, JTextField f, JTextArea t)
		{
			fr = frame;
			tf = f;
			ta = t;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if (command == 0)
			{
				String newStudentList = ta.getText();
				char[] sL = newStudentList.toCharArray();
			
				File path = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				
				String percentsReplaced = path.toString().replaceAll("%20", " ");
				
				int place = 0;
				
				for (int i = percentsReplaced.length() - 1; i >= 0; i--)
				{
					if (percentsReplaced.charAt(i) == File.separatorChar)
					{
						place = i;
						break;
					}
				}
				
				String bleh = percentsReplaced.substring(0, place).replaceAll("%20", " ") + File.separatorChar + "lists";
				String newFilePath = bleh + File.separatorChar + tf.getText() + ".lst";
				
				File newList = new File(newFilePath);
				
				try
				{
					if (!newList.createNewFile())
					{
						int selectedValue = JOptionPane.showConfirmDialog(null, "The file name given already exist. Would you like to replace it with the new list?", "", JOptionPane.YES_NO_OPTION);
						
						if (selectedValue == JOptionPane.OK_OPTION)
						{
							newList.delete();
							
							try
							{
								newList.createNewFile();
								PrintWriter fos = new PrintWriter(newList);
								
								for (int i = 0; i < sL.length; i++)
								{
									if (sL[i] != '\n')
									{
										fos.write(sL[i]);
									}
									else
									{
										fos.println();
									}
								}
								
								fos.close();
							}
							catch (IOException e2)
							{
								e2.printStackTrace();
							}
							
							fr.dispose();
						}
						else
						{
								
						}
					}
					else
					{
						PrintWriter fos = new PrintWriter(newList);
						
						for (int i = 0; i < sL.length; i++)
						{
							if (sL[i] != '\n')
							{
								fos.write(sL[i]);
							}
							else
							{
								fos.println();
							}
						}
						
						fos.close();
						
						fr.dispose();
					}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			else if (command == 1)
			{
				String newQuestionList = ta.getText();
				char[] sL = newQuestionList.toCharArray();
			
				File path = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				
				String percentsReplaced = path.toString().replaceAll("%20", " ");
				
				int place = 0;
				
				for (int i = percentsReplaced.length() - 1; i >= 0; i--)
				{
					if (percentsReplaced.charAt(i) == File.separatorChar)
					{
						place = i;
						break;
					}
				}
				
				String bleh = percentsReplaced.substring(0, place).replaceAll("%20", " ") + File.separatorChar + "questions";
				String newFilePath = bleh + File.separatorChar + tf.getText() + ".qlst";
				
				File newList = new File(newFilePath);
				
				try
				{
					//if (!newList.createNewFile())
					//{
						int selectedValue = JOptionPane.showConfirmDialog(null, "The file name given already exist. Would you like to replace it with the new list?", "", JOptionPane.YES_NO_OPTION);
						
						if (selectedValue == JOptionPane.OK_OPTION)
						{
							newList.delete();
							
							try
							{
								newList.createNewFile();
								PrintWriter fos = new PrintWriter(newList);
								
								for (int i = 0; i < sL.length; i++)
								{
									if (sL[i] != '\n')
									{
										fos.write(sL[i]);
									}
									else
									{
										fos.println();
									}
								}
								
								fos.close();
							}
							catch (IOException e2)
							{
								e2.printStackTrace();
							}
							
							fr.dispose();
						}
						else
						{
								
						}
					//}
					//else
					//{
						PrintWriter fos = new PrintWriter(newList);
						
						for (int i = 0; i < sL.length; i++)
						{
							if (sL[i] != '\n')
							{
								fos.write(sL[i]);
							}
							else
							{
								fos.println();
							}
						}
						
						fos.close();
						
						fr.dispose();
					//}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				
			}
		}
	}

	/************************
	 * Misc Methods
	 ************************/
	public File getDataLocation()
	{
		return new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	}
}