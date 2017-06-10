import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


public class Sudoku extends JFrame implements ActionListener{
	public int maxRow = 9,maxCol = 9;
	
	private boolean[][] start = new boolean[maxRow][maxCol];
	private int [][] board = new int[maxRow][maxCol];
	private JLabel[][] label = new JLabel[9][9];
	private boolean[] test = new boolean[9];
	private JTextField theText;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private int row = -1,col = -1,key;
	public Sudoku(){
		setTitle ("Name Tester with Borders");
        setSize (WIDTH, HEIGHT);
        //addWindowListener (new WindowDestroyer());
        Container content = getContentPane ();
        content.setLayout (new GridLayout(2,1));
        
        JPanel top = new JPanel();
        theText = new JTextField(20);
        top.add(theText);
        
        JButton buttonRow = new JButton("row");
        buttonRow.addActionListener(this);
        top.add(buttonRow);
        
        JButton buttonCol = new JButton("col");
        buttonCol.addActionListener(this);
        top.add(buttonCol);
        
        JButton guess = new JButton("value");
        guess.addActionListener(this);
        top.add(guess);
        
        JButton reset = new JButton("RESET");
        reset.addActionListener(this);
        top.add(reset);
        
        JButton chance = new JButton("CHance");
        chance.addActionListener(this);
        top.add(chance);
        content.add(top,BorderLayout.NORTH);
        
        JPanel show = new JPanel();
        show.setLayout(new GridLayout(9,9));
        show.setBorder(new EtchedBorder(Color.GRAY, Color.BLACK));
        for(int i = 0; i <maxRow; i++)
       	 for(int j = 0; j <maxCol; j ++)
       	 {
       		 label[i][j] = new JLabel();
       		 label[i][j].setBorder(new EtchedBorder(Color.GRAY, Color.BLACK));
       		 show.add(label[i][j]);
       		 
       	 }
      
        content.add(show, BorderLayout.SOUTH);
		setting();
	}
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("row"))
		{
			row = Integer.parseInt(theText.getText());
			row--;
			if(!checkRowCol(row))
			{

				JOptionPane.showMessageDialog(theText,"reInput ROW");
				theText.setText("");
			}
			else
				theText.setText("");
		}
		else if (e.getActionCommand().equals("col"))
		{
			col = Integer.parseInt(theText.getText());
			col--;
			if(!checkRowCol(col))
			{
				JOptionPane.showMessageDialog(theText,"reInput Col");
				theText.setText("");
			}
			else
				theText.setText("");
		}
		else if (e.getActionCommand().equals("value"))
		{
			if(row == -1 || col == -1){
				JOptionPane.showMessageDialog(theText,"first input row and col ! ");
				theText.setText("");
			}
			else{
				key = Integer.parseInt(theText.getText());
				if(key < 1 || key > 9)
				{
					JOptionPane.showMessageDialog(theText, 
							"value must be over 0, under 10");
					theText.setText("");
				}
				else{
				if(addGuess(row, col, key) != 1){
					theText.setText("");
					JOptionPane.showMessageDialog(theText, "You can't input this value int this location!");
				}
				else
				{
					label[row][col].setText("           "+Integer.toString(key));
					theText.setText("");
					row = -1;
					col = -1;
				}
				}
			}
		}
		else if(e.getActionCommand().equals("RESET"))
		{
			reset();
			randomStage();
			JOptionPane.showMessageDialog(theText, "Resseting");
			theText.setText("");
		}
		else
		{
			if(row == -1 || col == -1)
				JOptionPane.showMessageDialog(theText, 
						"If you want chane, input location which you want to know");
			else{
				String chance = "";
				boolean[] a =
						getAllowedValues(row, col);
				chance = "You can input"; 
				for(int i = 0; i < 9; i++)
				{
					if(a[i])
					{
						chance += " "+ Integer.toString(i+1);
					}
				}
				JOptionPane.showMessageDialog(theText, 
						chance);
			}
		}
	}
	private boolean checkRowCol(int a)
	{
		if(a < 0 || a > 8)
			return false;
		else
			return true;
	}
	private void setting(){
		int i,j;
		for(i = 0 ; i < maxRow ; i++)
			for(j = 0; j < maxCol ; j++)
			{
				board[i][j] = 0;
				start[i][j] = false;
			}
	}
	private boolean[] getAllowedValues(int row,int col){
		for(int k = 0; k < 9; k++)
			this.test[k] = true;
		for(int i = 0; i < 9; i++){
			for(int k = 0; k < 9; k++){
				if(board[i][col] == k+1)
					this.test[k] = false;
				if(board[row][i] == k+1)
					this.test[k] = false;
			}
					
		}
		int countRow = 0;
		int countCol = 0;
		while(true){
			if(row >= countRow && row <= countRow+3 && col >=countCol && col <=countCol+3)
			{
				for(int i = countRow; i < countRow+3; i++)
				{
					for(int j = countCol; j < countCol+3;j++)
					{
						for(int k = 0; k < 9; k++){
							if(board[i][j] == k+1){
								test[k] = false;
								continue;
							}
						}
					}
				}
				break;
			}
			else{
				if(countRow == 6 && countCol != 6){
					countRow = 0;
					countCol += 3;
					continue;
				}
				if(countRow != 6){
					countRow += 3;
					continue;
				}
	
			}
		}
		return test;
	}
	public String toString(){
		String string="";
		int i,j;
		for(i = 0; i < maxRow; i++){
			for(j = 0; j < maxCol; j++){
				string += String.valueOf(board[i][j]);
			}
			string += '\n';
		}
		return string;
		
	}
	private void addInitial(int row,int col, int value)
	{
		row--;
		col--;
		start[row][col] = true;
		board[row][col] = value;
		label[row][col].setText("           "+Integer.toString(value));
	}
	public int addGuess(int row,int col,int value)
	{
		int tempValue;
	
		if(start[row][col] == true)
		{	
			return -1;
		}
		tempValue = board[row][col];
		board[row][col] = value;
		if(!checkPuzzle(row,col)){
			board[row][col] = tempValue;
			return -1;
		}
		else
		{
			return 1;
		}
	}
	private void resetTest(){
		int i;
		for(i = 0; i < 9; i++)
			test[i] = false;
	}
	private boolean checkPuzzle(int row,int col){
		int countRow,countCol,k;
		int i,j;
		for(i = 0; i < maxRow; i ++)
		{
			if(i != row && board[i][col] == board[row][col])
				return false;
		}
		for(j = 0; j < maxCol ; j++)
		{
			if(j != col && board[row][j] == board[row][col])
				return false;
		}
		resetTest();
		countRow = 0;
		countCol = 0;
		while(true){
			if(row >= countRow && row <= countRow+3 && col >=countCol && col <=countCol+3)
			{
				for(i = countRow; i < countRow+3; i++)
				{
					for(j = countCol; j < countCol+3;j++)
					{
						for(k = 0; k < 9; k++){
							if(board[i][j] == k+1 && test[k] != true){
								test[k] = true;
								continue;
							}
							if(board[i][j] == k+1 && test[k] == true)
								return false;
						}
					}
				}
				return true;
			}
			else{
				if(countRow == 6 && countCol != 6){
					countRow = 0;
					countCol += 3;
					continue;
				}
				if(countRow != 6){
					countRow += 3;
					continue;
				}
	
			}
		}
	}
		
	public int getValueIn(int row, int col){
		return board[row][col];
	}

	public boolean isFull()
	{
		int i,j;
		for(i = 0; i < maxRow; i++)
			for(j = 0; j < maxCol; j++)
			{
				if(board[i][j] == 0)
					return false;
			}
		return true;
	}
	public void reset()
	{
		int i, j;
		for(i = 0; i < maxRow; i++)
			for(j = 0; j< maxCol; j++)
			{
				start[i][j] = false;
				label[i][j].setText("");
				board[i][j] = 0;
			}
	}
	public void randomStage()
	{
		double map;
		int stage;
		map = Math.random() * 10;
		stage = (int)map;
		stage %= 3;
		if(stage == 0)
		{
			addInitial(1,3,7);
			addInitial(1,4,2);
			addInitial(1,5,9);
			addInitial(2,2,2);
			addInitial(2,4,8);
			addInitial(3,3,9);
			addInitial(3,6,4);
			addInitial(3,9,2);
			addInitial(4,2,8);
			addInitial(4,7,4);
			addInitial(4,8,2);
			addInitial(5,1,3);
			addInitial(5,4,1);
			addInitial(5,6,2);
			addInitial(5,9,8);
			addInitial(6,2,5);
			addInitial(6,3,6);
			addInitial(6,8,9);
			addInitial(7,1,1);
			addInitial(7,4,4);
			addInitial(7,6,6);
			addInitial(8,6,6);
			addInitial(8,8,7);
			addInitial(9,5,1);
			addInitial(9,6,9);
			addInitial(9,7,3);
			
		}
		else if(stage == 1)
		{
			addInitial(1,1 ,5);
			addInitial(1,2 ,8);
			addInitial(1,5 ,2);
			addInitial(1, 6,7);
			addInitial(1,7 ,6);
			addInitial(2,2 ,1);
			addInitial(2,4 ,4);
			addInitial(3,1 ,4);
			addInitial(3,7 ,1);
			addInitial(4,1 ,1);
			addInitial(4,3 ,5);
			addInitial(4,8 ,4);
			addInitial(5,2 ,9);
			addInitial(5,8 ,2);
			addInitial(6,2 ,4);
			addInitial(6,7 ,7);
			addInitial(6, 9,1);
			addInitial(7,3 ,6);
			addInitial(7,9 ,8);
			addInitial(8,6 ,9);
			addInitial(8,8 ,7);
			addInitial(9,3 ,1);
			addInitial(9,4 ,2);
			addInitial(9,5 ,5);
			addInitial(9,8 ,3);
			addInitial(9,9 ,9);
			
		}
		else{
			addInitial(1,1,4);
			addInitial(1,2,5);
			addInitial(1,6,7);
			addInitial(1,7,6);
			addInitial(1,9,3);
			addInitial(2,1,6);
			addInitial(2,6,3);
			addInitial(3,1,8);
			addInitial(3,6,4);
			addInitial(3,7,4);
			addInitial(4,4,3);
			addInitial(4,7,5);
			addInitial(5,2,3);
			addInitial(5,8,8);
			addInitial(6,3,7);
			addInitial(6,6,2);
			addInitial(7,3,6);
			addInitial(7,4,5);
			addInitial(7,9,7);
			addInitial(8,4,4);
			addInitial(8,9,2);
			addInitial(9,1,3);
			addInitial(9,3,1);
			addInitial(9,4,9);
			addInitial(9,8,5);
			addInitial(9,9,6);
			
			
		}
		
	}
	public static void main (String [] args)
    {
        Sudoku gui = new Sudoku();
        gui.randomStage();
        gui.setVisible (true);
    }
}
