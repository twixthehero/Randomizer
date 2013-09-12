package randomizer.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Randomizer
{
	private StudentList studentList;
	private ArrayList<String> picked = new ArrayList<String>();
	private ArrayList<String> unpicked = new ArrayList<String>();
	
	private ArrayList<QuestionList> questionLists;
	private ArrayList<String> pickedq, unpickedq;
	
	//private ArrayList<VocabUnit> vocabUnits;
	//private ArrayList<VocabSlide> pickeds, unpickeds;
	JFrame frame = new JFrame();
	
	public Randomizer()
	{
		frame.setSize(416, 438);
	}
	
	public StudentList getStudentList()
	{
		return studentList;
	}
	
	public void setStudentList(StudentList sl)
	{
		studentList = sl;
		clearStudents();
		unpicked = studentList.getStudents();
	}
	
	public void clearStudents()
	{
		picked.clear();
		unpicked.clear();
	}
	
	public String pickNextStudent()
	{
		if (unpicked.isEmpty())
		{
			while (!picked.isEmpty())
			{
				unpicked.add(picked.remove(0));
			}
		}
		
		String s = unpicked.remove((int)(Math.random() * unpicked.size()));
		picked.add(s);
		return s;
	}
	
	public ArrayList<QuestionList> getQuestionLists()
	{
		return questionLists;
	}
	
	public void addQuestionList(QuestionList l)
	{
		questionLists.add(l);
	}
	
	public String pickNextQuestion()
	{
		if (unpickedq.isEmpty())
		{
			while (!pickedq.isEmpty())
			{
				unpickedq.add(pickedq.remove(0));
			}
		}
		
		String q = unpickedq.remove((int)(Math.random() * unpickedq.size()));
		pickedq.add(q);
		return q;
	}
	
	public void clearAllQuestions()
	{
		pickedq.clear();
		unpickedq.clear();
	}
	
	/*
	public ArrayList<VocabUnit> getVocabLists()
	{
		return vocabUnits;
	}
	
	public void addVocabUnit(VocabUnit v)
	{
		vocabUnits.add(v);
		
		for (int i = 0; i < v.getSlides().size(); i++)
		{
			unpickeds.add(v.getSlides().get(i));
		}
	}
	
	public VocabSlide pickNextSlide()
	{
		if (unpickeds.isEmpty())
		{
			while (!pickeds.isEmpty())
			{
				unpickeds.add(pickeds.remove(0));
			}
		}
		
		VocabSlide v = unpickeds.remove((int)(Math.random() * unpickeds.size()));
		pickeds.add(v);
		return v;
	}
	
	public void clearAllUnits()
	{
		pickeds.clear();
		unpickeds.clear();
	}
	*/
	
	public void pickVocab(String[] vocabUnits, JCheckBoxMenuItem[] units)
	{
		frame.setVisible(false);
		frame.getContentPane().removeAll();
		
		ArrayList<String> vocab = new ArrayList<String>();
		
		File path = new File(Randomizer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		int place = 0;
		
		for (int i = path.getPath().length() - 1; i >= 0; i--)
		{
			if (path.getPath().charAt(i) == File.separatorChar)
			{
				place = i;
				break;
			}
		}
		
		String p = path.getPath().substring(0, place).replaceAll("%20", " ") + File.separatorChar + "vocab";
		
		for (int i = 0; i < vocabUnits.length; i++)
		{
			if (units[i].isSelected())
			{
				File dir = new File(p + File.separatorChar + vocabUnits[i]);
				String[] temp = dir.list();
				
				for (int k = 0; k < temp.length; k++)
				{
					vocab.add(File.separatorChar + vocabUnits[i] + File.separatorChar + temp[k]);
				}
			}
		}
		
		int randV = (int)(Math.random() * vocab.size());
		
		displayVocab(p + vocab.get(randV));
	}
	
	public void displayVocab(String fileName)
	{
		ImageIcon icon = new ImageIcon(fileName);
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, 400, 400, null);
		g.dispose();
		
		img = Toolkit.getDefaultToolkit().createImage(bi.getSource());
		
		ImagePanel panel = new ImagePanel(img);
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	class ImagePanel extends JPanel
	{
		private static final long serialVersionUID = -7417382580954887715L;
		
		private Image img;
		
		public ImagePanel(String img)
		{
			this(new ImageIcon(img).getImage());
		}
		
		public ImagePanel(Image img)
		{
			this.img = img;
			
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(img, 0, 0, null);
		}
	}
}