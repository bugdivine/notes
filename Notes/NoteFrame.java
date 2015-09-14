import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

/**
 * NoteFrame
 * Part of NoteMaker app @author Shubham Jain sj30798@gmail.com
 * Adds frame to the app
 * @author shubham
 *
 */
@SuppressWarnings("serial")
final class NoteFrame extends JFrame
{
	/**
	 * Initializes each frame and assign title and size
	 * @param i
	 * The index of frame
	 */
	public NoteFrame(int i)
	{
		num = i;
		setTitle("Notes");
		setSize(WIDTH, HEIGHT);
		
//		System.out.println("Sello");
/*		Toolkit kit = Toolkit.getDefaultToolkit();
		java.net.URL imgURL = getClass().getResource("/Notes/Icons/Notes.png");
	   	Image img = kit.getImage(imgURL);
//		Image img = kit.getImage("Notes.png");
//		if (img == null)
//			System.out.println("Hello");
*/		ImageIcon img = null;
		try {
			img = new ImageIcon(getClass().getResource("/Icons/Notes.png"));
		} catch (Exception e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setIconImage(img.getImage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contentPane = getContentPane();
		
		addTextField();
	}
		
	/**
	 * Adds TextField to the contentPane
	 */
	private void addTextField()
	{
		text = new JTextArea();
		
		addTextContent();
		
		text.setBounds(0, 0, WIDTH, HEIGHT - 50);
		text.setBackground(textFieldColor);
		text.setFont(textFieldFont);
		scroll = new JScrollPane(text, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setLayout(new ScrollPaneLayout());

		scroll.setBounds(0,  0, 250, 200);

		JPanel bottomPanel = new JPanel();
		NoteMaker.addButton = new JButton("+");
		NoteMaker.delButton = new JButton("-");
		NoteMaker.saveButton = new JButton("Save");
				
		NoteMaker.addButton.setBackground(buttonColor);
		NoteMaker.delButton.setBackground(buttonColor);
		NoteMaker.saveButton.setBackground(buttonColor);

		addSaveAction();
		NoteMaker.addAddAction();
		NoteMaker.addDelAction(num);
		
		bottomPanel.setBackground(textFieldColor);
		bottomPanel.add(NoteMaker.addButton);
		bottomPanel.add(NoteMaker.saveButton);
		bottomPanel.add(NoteMaker.delButton);
		
		contentPane.add(bottomPanel, BorderLayout.PAGE_END);
		contentPane.add(scroll);
	}
	
	/**
	 * gets the content of each frame from a previously saved location and adds content to the text panel of frame
	 */
	@SuppressWarnings("unused")
	private void addTextContent()
	{
		inputFile = new File(".NoteMaker/notes" + num);
		Scanner in = null;
		try {
			in = new Scanner(inputFile);
		} catch (FileNotFoundException e1) {
	          BufferedWriter output = null;
			try {
				output = new BufferedWriter(new FileWriter(inputFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          try {
				in = new Scanner(inputFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		while (in.hasNextLine())
		{
			String line = in.nextLine();
			text.setText(text.getText() + line + "\n");
		}
		in.close();
	}
	
	/**
	 * Adds action to save button.
	 * Save the content of frame in a file. If file not already present then also creates the file  
	 */
	private void addSaveAction()
	{
		NoteMaker.saveButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				PrintWriter out = null;
		//		System.out.println("Hello " + num);
				try {
					out = new PrintWriter(".NoteMaker/notes" + num);
					@SuppressWarnings("resource")
					Scanner outin = new Scanner(text.getText());
					while (outin.hasNextLine())
					{
						String line = outin.nextLine();
						out.println(line);
					}
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					File file = new File(".NoteMaker/");
					file.mkdir();
					file = new File(".NoteMaker/notes" + num);
				   	try {
						@SuppressWarnings({ "unused", "resource" })
						BufferedWriter output = new BufferedWriter(new FileWriter(file));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						out = new PrintWriter(".NoteMaker/notes" + num);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					@SuppressWarnings("resource")
					Scanner outin = new Scanner(text.getText());
					while (outin.hasNextLine())
					{
						String line = outin.nextLine();
						out.println(line);
					}
					out.close();
				}
			}
		});
	}
	
	/**
	 * Index of frame
	 */
	public int num;
	/**
	 * stores name of a file to avoid passing file name between various methods again and again
	 */
	private File inputFile;
	/**
	 * ScrollPane to which textArea will be added
	 */
	public static JScrollPane scroll;
	/**
	 * TextArea object
	 */
	private JTextArea text;
	/**
	 * Width of frame
	 */
	private static int WIDTH = 250;
	/**
	 * Height of frame
	 */
	private static int HEIGHT = 250;
	/**
	 * Content Pane to be added to the frame
	 */
	public static Container contentPane;
	
	/**
	 * Background color for buttons
	 */
	private static Color buttonColor = new Color(173, 173, 17);

	/**
	 * Text Field color
	 */
	private static Color textFieldColor = new Color(245, 231, 130);
	
	/**
	 * Font used to write text in textfield
	 */
	private static Font textFieldFont = new Font("Sofia", Font.ITALIC, 20);
}
