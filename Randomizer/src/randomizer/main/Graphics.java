package randomizer.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Graphics extends JFrame
{
	private static final long serialVersionUID = -2449476833862848995L;
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 600;
	
	private Menu menu;
	private Randomizer randomizer;
	
	private JPanel components;
	
	private Font font;
	
	JTextField pickedStudent;
	private JButton student;
	
	JTextField pickedQuestion;
	private JButton question;
	
	private JButton vocab;
	
	private JLabel loadedStudentList, loadedQuestionLists;
	
	private JTextArea loadedQuestionListsArea;
	private JScrollPane loadedQuestionListsAreaScrollPane;
	
	private String[] vocabUnits;
	
	public Graphics()
	{
		//Vocab Unit handling
		File file = new File(Graphics.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString());
		
		int place = 0;
		
		for (int i = file.getPath().length() - 1; i >= 0; i--)
		{
			if (file.getPath().charAt(i) == File.separatorChar)
			{
				place = i;
				break;
			}
		}
		
		File f = new File(file.getPath().substring(0, place).replaceAll("%20", " ") + File.separatorChar + "vocab");
		
		if (!f.exists())
			f.mkdir();
		
		Comparator<File> byAlpha = new byAlphaComparator();
		
		File[] arrayOfDirs = f.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File file, String s)
			{
				return new File(file.getAbsolutePath() + File.separatorChar + s).isDirectory();
			}
		});
		
		Arrays.sort(arrayOfDirs, byAlpha);
		
		vocabUnits = new String[arrayOfDirs.length];
		
		for (int i = 0; i < arrayOfDirs.length; i++)
		{
			vocabUnits[i] = arrayOfDirs[i].getName();
		}
		
		randomizer = new Randomizer();
		menu = new Menu(randomizer, this, vocabUnits);
		
		//initialize panel to hold components
		components = new JPanel();
		//components.setLayout(new GridLayout(9, 1));
		
		//default font for all components in main GUI
		font = new Font("SansSerif", Font.PLAIN, 24);
		
		pickedStudent = new JTextField(25);
		pickedStudent.setFont(font);
		pickedStudent.setForeground(new Color(255, 0, 0));
		pickedStudent.setBackground(new Color(0, 255, 0));
		pickedStudent.setHorizontalAlignment(JTextField.CENTER);
		pickedStudent.setEditable(false);
		
		student = new JButton("Random Student");
		student.setFont(font);
		student.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (randomizer.getStudentList() == null)
				{
					JOptionPane.showMessageDialog(null, "Please load a student list.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					pickedStudent.setText(randomizer.pickNextStudent());
					
					Audio.pickSoundToPlay();
				}
			}
		});
		
		pickedQuestion = new JTextField(25);
		pickedQuestion.setFont(font);
		pickedQuestion.setForeground(new Color(0, 0, 255));
		pickedQuestion.setBackground(new Color(255, 0, 0));
		pickedQuestion.setHorizontalAlignment(JTextField.CENTER);
		pickedQuestion.setEditable(false);
		
		question = new JButton("Random Question");
		question.setFont(font);
		question.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (randomizer.getQuestionLists() == null)
				{
					JOptionPane.showMessageDialog(null, "Please load a question set.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					pickedQuestion.setText(randomizer.pickNextQuestion());
					
					Audio.pickSoundToPlay();
				}
			}
		});
		
		vocab = new JButton("Random Vocab");
		vocab.setFont(font);
		vocab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (allUnchecked())
				{
					JOptionPane.showMessageDialog(null, "Please check at least one vocab unit.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					randomizer.pickVocab(vocabUnits, menu.getCheckboxes());
					
					Audio.pickSoundToPlay();
				}
			}
		});
		
		//currently loaded student list
		loadedStudentList = new JLabel("Student List: No list loaded");
		loadedStudentList.setFont(font);
		loadedStudentList.setHorizontalAlignment(JLabel.CENTER);
		
		//question lists label
		loadedQuestionLists = new JLabel("Question List(s):");
		loadedQuestionLists.setFont(font);
		loadedQuestionLists.setHorizontalAlignment(JLabel.CENTER);
		
		//text area containing currently loaded question lists
		loadedQuestionListsArea = new JTextArea(10, 10);
		loadedQuestionListsArea.setEditable(false);
		
		//scroll pane used to scroll through the currently loaded question lists
		loadedQuestionListsAreaScrollPane = new JScrollPane(loadedQuestionListsArea);
		loadedQuestionListsAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		loadedQuestionListsAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		components.setLayout(new GridLayout(8, 1));
		
		//main GUI initialization
		components.add(pickedStudent);
		components.add(student);
		components.add(pickedQuestion);
		components.add(question);
		components.add(vocab);
		components.add(loadedStudentList);
		components.add(loadedQuestionLists);
		components.add(loadedQuestionListsAreaScrollPane);
		
		add(components);
		
		//set menu bar
		setJMenuBar(menu.createMenu());
		
		//main GUI options
		setSize(WIDTH, HEIGHT);
		setTitle("Randomizer 9002");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class byAlphaComparator implements Comparator<File>
	{
	    // Comparator interface requires defining compare method.
	    public int compare(File filea, File fileb)
	    {
	        //... Sort directories before files,
	        //    otherwise alphabetical ignoring case.
	        if (filea.isDirectory() && !fileb.isDirectory())
	        {
	            return -1;
	        }
	        else if (!filea.isDirectory() && fileb.isDirectory())
	        {
	            return 1;
	        }
	        else
	        {
	            return filea.getName().compareToIgnoreCase(fileb.getName());
	        }
	    }
	}
	
	public JLabel getLoadedStudentList()
	{
		return loadedStudentList;
	}
	
	public void setLoadedStudentList(JLabel label)
	{
		loadedStudentList = label;
	}
	
	public JTextArea getLoadedQuestionListArea()
	{
		return loadedQuestionListsArea;
	}
	
	public boolean allUnchecked() /** finished **/
	{
		JCheckBoxMenuItem[] boxes = menu.getCheckboxes();
		
		for (int i = 0; i < boxes.length; i++)
		{
			if (boxes[i].isSelected())
			{
				return false;
			}
		}
		
		return true;
	}
}