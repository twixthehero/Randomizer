package randomizer.main;

import java.io.File;
import java.util.ArrayList;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio
{
	public static void pickSoundToPlay()
	{
		ArrayList<String> wavs = new ArrayList<String>();
		
		File path = new File(Audio.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		String percentsReplaced = path.toString();
		
		int place = 0;
		
		for (int i = percentsReplaced.length() - 1; i >= 0; i--)
		{
			if (percentsReplaced.charAt(i) == File.separatorChar)
			{
				place = i;
				break;
			}
		}
		
		String bleh = percentsReplaced.substring(0, place).replaceAll("%20", " ") + File.separatorChar + "sounds";
		
		File dir = new File(bleh);
		String[] soundfiles = dir.list();
		
		for (int i = 0; i < soundfiles.length; i++)
		{
			if (soundfiles[i].endsWith(".wav"))
			{
				wavs.add(soundfiles[i]);
			}
		}
		
		int s = (int)(Math.random() * wavs.size());
		
		playSound(bleh + File.separatorChar + wavs.get(s));
	}
	
	private static void playSound(String filename)
	{
		@SuppressWarnings("unused")
		JFXPanel p = new JFXPanel();
		
		Media media = new Media("file:///" + filename.replace('\\', '/'));
		MediaPlayer player = new MediaPlayer(media);
		player.play();
	}
}