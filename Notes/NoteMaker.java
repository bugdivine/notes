import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * NoteMaker application
 * Developed by Shubham Jain sj30798@gmail.com
 * @author shubham
 * 
 */
public class NoteMaker
{
	/**
	 * Main class NoteMaker app @author shubham
	 * Initializes n frames by reading n getFrameNums() function
	 * @param args
	 * No command line input required
	 */
	public static void main(String[] args) {
		framenum = getFrameNums();
		
		frames = new ArrayList<NoteFrame>();
		for (int i = 1; i <= framenum; i++){
			frame = new NoteFrame(i);
			frames.add(frame);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
	}
	
	/**
	 * Add delete action to Delete button
	 * Deletes the frame and resets if only one frame present
	 * @param index
	 * index of the deleted frame
	 */
	public static final void addDelAction(final int index)
	{
		delButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int num = index;
//				System.out.println("index = " + index);
				File file = new File(".NoteMaker/notes" + num);
				file.delete();
				frames.get(num - 1).dispose();
				for (int i = index + 1; i <= framenum; i++)
				{
					File oldFile = new File(".NoteMaker/notes" + i);
					File newFile = new File(".NoteMaker/notes" + (i - 1));
					oldFile.renameTo(newFile);
				}
				for (int i = index - 1; i < framenum; i++)
				{
					frames.get(i).num = frames.get(i).num - 1;
				}
				file = new File(".NoteMaker/notes" + framenum);
				file.delete();
				if (framenum != 1)
				{
					numFrameWrite(framenum - 1);
					framenum = framenum - 1;
				}
			}
		});
	}
	
	/**
	 * Add actions to the "Add" button
	 * Create a new frame and
	 * changes the number of frames in the file to earlier + 1
	 */
	public static final void addAddAction()
	{
		addButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				framenum = framenum + 1;
				frame = new NoteFrame(framenum);
				frames.add(frame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				file = new File(".NoteMaker/notes" + framenum);
			   	BufferedWriter output = null;
				try {
					output = new BufferedWriter(new FileWriter(file));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				numFrameWrite(framenum);
			}
		});
	}
	
	/**
	 * Change the content of file containing frame numbers to n
	 * @param n
	 * The number to print
	 */
	private static void numFrameWrite(int n)
	{
		file = new File(".NoteMaker/nums.txt");
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
          out.println(n);
          out.close();
	}
	
	/**
	 * Returns the number of frames previously saved in the file
	 * If file not present, creates a new file with 1 as the initial value
	 * @return
	 * Returns number of frames
	 */
	@SuppressWarnings("unused")
	private static int getFrameNums()
	{
		String fileName = ".NoteMaker/nums.txt";
		file = new File(fileName);
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e1) {
	         File newfile = new File(".NoteMaker");
	         newfile.mkdir();
		   	BufferedWriter output = null;
			try {
				output = new BufferedWriter(new FileWriter(file));
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			numFrameWrite(1);
			try {
				in = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
		String input = in.nextLine();
		in.close();
		int n = Integer.parseInt(input);
		return n;
	}
	
	/**
	 * Stores reference to all visible frame
	 */
	private static ArrayList<NoteFrame> frames;
	/**
	 * File object to avoid excess passing of parameters
	 */
	private static File file;
	/**
	 * Total numbers of frames
	 */
	private static int framenum;
	/**
	 * Add button adds an extra frame
	 */
	public static JButton addButton;
	/**
	 * Deletes the frame
	 */
	public static JButton delButton;
	/**
	 * Saves the content of frame for future retrieval
	 */
	public static JButton saveButton;
	/**
	 * NoteFrame to avoid excess passing of parameters among methods
	 */
	private static NoteFrame frame;
}