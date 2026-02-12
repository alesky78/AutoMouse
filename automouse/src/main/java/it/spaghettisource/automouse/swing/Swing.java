package it.spaghettisource.automouse.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

import it.spaghettisource.automouse.agent.DataBug;
import it.spaghettisource.automouse.utils.Configuration;
import it.spaghettisource.automouse.utils.ImageIconFactory;
import it.spaghettisource.automouse.utils.TimeSlot;


public class Swing extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5335453468072382991L;

	private DataBug dataBug;

	//DataBug works in millisecond, this constant help the conversion to milliseconds in seconds
	private static final int TIME_CONVERTER = 1000;
	private static final String PLAY = "PLAY";
	private static final String PAUSE = "PAUSE";
	private static final String ADD_SLOT = "ADD_SLOT";
	private static final String CLEAR_SLOTS = "CLEAR_SLOTS";

	//This label configured the agent time
	private JLabel sleepTimeLabel;
	private JLabel mouseMoveLabel;

	//this button control the pause and the play of the sleep thread
	private JButton pauseButton;
	private JButton playButton;

	// Multy-stop UI components
	private JLabel autoStopLabel;
	private JTextField startField;
	private JTextField endField;
	private JButton addSlotButton;
	private JButton clearSlotsButton;
	private JPanel autoStopPanel;

	public Swing(DataBug dataBugInput){

		this.dataBug = dataBugInput;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		//////////////////////////////////////////////
		// --- Auto stop panel ---
		//////////////////////////////////////////////
		autoStopPanel = new JPanel();
		autoStopPanel.setLayout(new BoxLayout(autoStopPanel, BoxLayout.Y_AXIS));
		autoStopPanel.setBorder(BorderFactory.createTitledBorder("auto stop slots"));

		autoStopLabel = new JLabel();
		autoStopLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel autoStopLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		autoStopLabelPanel.add(autoStopLabel);
		autoStopPanel.add(autoStopLabelPanel);
		updateAutoStopLabel();

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(new JLabel("Start (HH:MM):"));
		startField = new JTextField(5);
		inputPanel.add(startField);
		inputPanel.add(new JLabel("End (HH:MM):"));
		endField = new JTextField(5);
		inputPanel.add(endField);
		addSlotButton = new JButton("Add");
		addSlotButton.setActionCommand(ADD_SLOT);
		addSlotButton.addActionListener(this);
		inputPanel.add(addSlotButton);
		clearSlotsButton = new JButton("Clear");
		clearSlotsButton.setActionCommand(CLEAR_SLOTS);
		clearSlotsButton.addActionListener(this);
		inputPanel.add(clearSlotsButton);
		autoStopPanel.add(inputPanel);

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
		add(autoStopPanel);
		add(sleepTimePanel);
		add(mouseMovePanel);				
		add(buttonPanel);

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	}


	public void actionPerformed(ActionEvent evt) {
		String cmd = evt.getActionCommand();
		if (PLAY.equals(cmd)) {
			dataBug.setPauseManual(false);
			playButton.setEnabled(false);
			pauseButton.setEnabled(true);

		} else if (PAUSE.equals(cmd)) {
			dataBug.setPauseManual(true);
			playButton.setEnabled(true);
			pauseButton.setEnabled(false);

		} else if (ADD_SLOT.equals(cmd)) {
			String startStr = startField.getText().trim();
			String endStr = endField.getText().trim();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime start, end;
			try {
				start = LocalTime.parse(startStr, fmt);
				end = LocalTime.parse(endStr, fmt);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:MM.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!start.isBefore(end)) {
				JOptionPane.showMessageDialog(this, "Invalid slot: start must be before end.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			TimeSlot newSlot = new TimeSlot(start, end);
			List<TimeSlot> slots = new ArrayList<>(dataBug.getAutoStopSlots());
			for (TimeSlot slot : slots) {
				if (overlaps(newSlot, slot)) {
					JOptionPane.showMessageDialog(this, "Invalid slot: overlaps an existing slot.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			slots.add(newSlot);
			slots.sort(null);
			dataBug.setAutoStopSlots(slots);
			updateAutoStopLabel();
			startField.setText("");
			endField.setText("");

		} else if (CLEAR_SLOTS.equals(cmd)) {
			dataBug.setAutoStopSlots(Collections.emptyList());
			updateAutoStopLabel();
		}
	}

	private void updateAutoStopLabel() {
        List<TimeSlot> slots = dataBug.getAutoStopSlots();
        if (slots.isEmpty()) {
            autoStopLabel.setText("configured slots: (none)");
        } else {
            StringBuilder sb = new StringBuilder("configured slots: ");
            for (int i = 0; i < slots.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(slots.get(i).toString());
            }
            autoStopLabel.setText(sb.toString());
        }
    }
    private boolean overlaps(TimeSlot a, TimeSlot b) {
        return a.getStart().isBefore(b.getEnd()) && b.getStart().isBefore(a.getEnd());
    }
}
