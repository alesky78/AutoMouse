package it.spaghettisource.automouse.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.utils.Configuration;
import it.spaghettisource.automouse.utils.ImageIconFactory;


public class Swing extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5335453468072382991L;

	private DataBug dataBug;

	//DataBug works in millisecond, this constant help the conversion to milliseconds in seconds
	private static final int TIME_CONVERTER = 1000;
	private static final String PLAY = "PLAY";
	private static final String PAUSE = "PAUSE";

	//This label configured the agent time
	JLabel sleepTimeLabel;
	JLabel mouseMoveLabel;	

	//this button control the pause and the play of the sleep thread
	JButton pauseButton;
	JButton playButton;

	public Swing(DataBug dataBugInput){

		this.dataBug = dataBugInput;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//////////////////////////////////////////////
		//Create The iteration of the agent slider
		//////////////////////////////////////////////	
		JPanel sleepTimePanel = new JPanel();		
		sleepTimePanel.setLayout(new BoxLayout(sleepTimePanel, BoxLayout.PAGE_AXIS));		
		sleepTimePanel.setBorder(BorderFactory.createTitledBorder("agent sleep time interval"));		

		//Create the slider.
		JSlider slider = new JSlider(JSlider.HORIZONTAL,Configuration.getMinSleepTime()/TIME_CONVERTER, Configuration.getMaxSleepTime()/TIME_CONVERTER, Configuration.getDefaultSleepTime()/TIME_CONVERTER);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					int sleepTime = source.getValue();
					sleepTimeLabel.setText(sleepTime +  " seconds ");
					dataBug.setSleepTimeMilliseconds(sleepTime*TIME_CONVERTER);
				}
			}
		});

		slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		//Create the label that displays the sleep time.
		sleepTimeLabel = new JLabel();
		sleepTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		sleepTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sleepTimeLabel.setText(dataBug.getSleepTimeSeconds() + " seconds");


		sleepTimePanel.add(slider);
		sleepTimePanel.add(sleepTimeLabel);


		//////////////////////////////////////////////
		//Create The configuration of the mouse move
		//////////////////////////////////////////////	
		JPanel mouseMovePanel = new JPanel();		
		mouseMovePanel.setLayout(new BoxLayout(mouseMovePanel, BoxLayout.PAGE_AXIS));		
		mouseMovePanel.setBorder(BorderFactory.createTitledBorder("number of pixel to move"));		

		//Create the slider.
		JSlider mouseMoveSlider = new JSlider(JSlider.HORIZONTAL,Configuration.getMinPixelMove(), Configuration.getMaxPixelMove(), Configuration.getDefaultPixelMove());
		mouseMoveSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					int pixelToMove = source.getValue();
					mouseMoveLabel.setText(pixelToMove +  " pixel ");
					dataBug.setPixelToMove(pixelToMove);
				}
			}
		});

		mouseMoveSlider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		//Create the label that displays the pixel to move time.
		mouseMoveLabel = new JLabel();
		mouseMoveLabel.setHorizontalAlignment(JLabel.CENTER);
		mouseMoveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mouseMoveLabel.setText(dataBug.getPixelToMove() + " pixel");


		mouseMovePanel.add(mouseMoveSlider);
		mouseMovePanel.add(mouseMoveLabel);		


		//////////////////////////////////////////////
		//Create The play and pause agent button panel
		//////////////////////////////////////////////		
		JPanel buttonPanel = new JPanel();		
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("manage agent"));


		pauseButton = new JButton(ImageIconFactory.getForButton("/stop.png"));			
		pauseButton.setActionCommand(PAUSE);
		pauseButton.addActionListener(this);
		pauseButton.setEnabled(!dataBug.isPause());

		playButton = new JButton(ImageIconFactory.getForButton("/play.png"));			
		playButton.setActionCommand(PLAY);
		playButton.addActionListener(this);
		playButton.setEnabled(dataBug.isPause());


		//pause.setBorder(BorderFactory.createEmptyBorder());
		pauseButton.setContentAreaFilled(false);
		//play.setBorder(BorderFactory.createEmptyBorder());
		playButton.setContentAreaFilled(false);

		buttonPanel.add(pauseButton);
		buttonPanel.add(playButton);


		//////////////////////////////////////////////
		//Put all the panel togheter
		//////////////////////////////////////////////	
		add(sleepTimePanel);
		add(mouseMovePanel);				
		add(buttonPanel);

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	}


	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == PLAY) {
			dataBug.setPause(false);
			playButton.setEnabled(false);
			pauseButton.setEnabled(true);
		}else if (evt.getActionCommand() == PAUSE) {
			dataBug.setPause(true);
			playButton.setEnabled(true);
			pauseButton.setEnabled(false);
		}



	}


}
