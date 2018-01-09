package it.spaghettisource.automouse.swing;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.utils.Configuration;
import it.spaghettisource.automouse.utils.ImageIconFactory;

public class Swing extends JPanel implements ChangeListener,ActionListener{

	private DataBug dataBug;

	//DataBug works in millisecond, this constant help the conversion to milliseconds in seconds
	private final static int TIME_CONVERTER = 1000;

	private final static String PLAY = "PLAY";
	private final static String PAUSE = "PAUSE";

	//This label show the actual configured sleep time
	JLabel sleepTimeLabel;

	//this button control the pause and the play of the sleep thread
	JButton pause,play;


	public Swing(DataBug dataBug){

		this.dataBug = dataBug;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Create the label.
		JLabel sliderLabel = new JLabel("sleep time interval", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//Create the slider.
		JSlider slider = new JSlider(JSlider.HORIZONTAL,Configuration.getMinSleepTime()/TIME_CONVERTER, Configuration.getMaxSleepTime()/TIME_CONVERTER, Configuration.getDefaultSleepTime()/TIME_CONVERTER);
		slider.addChangeListener(this);

		slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		slider.setFont(font);

		//Create the label that displays the sleeptime.
		sleepTimeLabel = new JLabel();
		sleepTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		sleepTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sleepTimeLabel.setText(Integer.toString(Configuration.getDefaultSleepTime()/TIME_CONVERTER) + " seconds");

		//Create The play and pause button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


		pause = new JButton(ImageIconFactory.getForButton("/stop.png"));			
		pause.setActionCommand(PAUSE);
		pause.addActionListener(this);
		pause.setEnabled(!dataBug.isPause());

		play = new JButton(ImageIconFactory.getForButton("/play.png"));			
		play.setActionCommand(PLAY);
		play.addActionListener(this);
		play.setEnabled(dataBug.isPause());


		//pause.setBorder(BorderFactory.createEmptyBorder());
		pause.setContentAreaFilled(false);
		//play.setBorder(BorderFactory.createEmptyBorder());
		play.setContentAreaFilled(false);

		buttonPanel.add(pause);
		buttonPanel.add(play);



		//Put everything together.
		add(sliderLabel);
		add(slider);
		add(sleepTimeLabel);
		add(new JSeparator());
		add(buttonPanel);

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	}


	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int sleepTime = (int)source.getValue();
			sleepTimeLabel.setText(sleepTime +  " seconds ");
			dataBug.setSleepTimeMilliseconds(sleepTime*TIME_CONVERTER);
		}

	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == PLAY) {
			dataBug.setPause(false);
			play.setEnabled(false);
			pause.setEnabled(true);
		}else if (evt.getActionCommand() == PAUSE) {
			dataBug.setPause(true);
			play.setEnabled(true);
			pause.setEnabled(false);
		}



	}


}
