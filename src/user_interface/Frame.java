package user_interface;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame extends JFrame {


	private static final long serialVersionUID = -4178107840321853677L;
	JTextPane input = new JTextPane();
	JScrollPane scl1 = new JScrollPane(input);
	JTextPane output = new JTextPane();
	JScrollPane scl2 = new JScrollPane(output);

	JLabel label1 = new JLabel("Input");
	JLabel label2 = new JLabel("Onput");

	JButton button1 = new JButton(" Run ");
	JButton button2 = new JButton("Clear");

	public void GUI() {

		this.setLayout(new GridBagLayout());

		add(label1, new GBC(0, 0).setFill(GBC.NONE).setFill(GBC.HORIZONTAL)
				.setInsets(20, 20, 0, 10));
		add(scl1, new GBC(0, 1, 2, 3).setFill(GBC.BOTH).setWeight(0.5, 0.5)
				.setInsets(0, 20, 20, 10));

		add(button1, new GBC(3, 1).setInsets(30, 10, 10, 20));
		add(button2, new GBC(3, 2).setInsets(0, 10, 10, 20));

		add(label2, new GBC(0, 4).setFill(GBC.NONE).setFill(GBC.HORIZONTAL)
				.setInsets(10, 20, 0, 10));
		add(scl2, new GBC(0, 5, 3, 4).setFill(GBC.BOTH).setWeight(1, 1)
				.setInsets(0, 20, 20, 10));

		this.setVisible(true);
		this.setSize(750, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Chinese Word Segmentation");

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String text = input.getText();
				Run seg = new Run(text);
				String result = null;
				try {
					result = seg.getresult();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				output.setText(result);

			}
		});

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				input.setText(null);
				output.setText(null);
			}
		});

	}

	public static void main(String[] args) {

		Font vFont = new Font("Dialog", Font.PLAIN, 18);
		UIManager.put("TextPane.font", vFont);
		UIManager.put("Label.font", vFont);
		UIManager.put("Button.font", vFont);

		Frame test = new Frame();
		test.GUI();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}
}
