/*
 Burton Feng
 */

/*********************************************************/
/*                                                       */
/* An applet to demonstrate recursion and backtracking   */
/* ===================================================   */
/*                                                       */
/* V0.3   18-MAR-2007  P. Tellenbach   www.heimetli.ch   */
/*                                                       */
/*********************************************************/
import java.applet.* ;
import java.awt.* ;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Solves a sudoku puzzle by recursion and backtracking
 */
public class SimplifiedSudoku extends Applet implements Runnable
{
  /** The model */
  protected int model[][] ;
  
  /** The view */
  protected Button view[][] ;
  
  /** Creates the model and sets up the initial situation */
  protected void createModel() throws IOException
  {
    model = new int[16][16] ;
    
    // Clear all cells
    for( int row = 0; row < 16; row++ ) {
      for( int col = 0; col < 16; col++ ) {
        model[row][col] = 0 ;
      }
    }
    
   //To read the file
    String fileName = "inputFile";
    File file = new File(fileName);
    Scanner readLine = new Scanner(file);
    
    //Every time the a line is read from the file it is stored here.
    String line;
    
    // Create the initial situation by reading from the file
    while(readLine.hasNext()) {
      line = readLine.nextLine();
      
      if(line.length() == 5) {
        model[Integer.parseInt(line.substring(0, 1))][Integer.parseInt(line.substring(2, 3))] = Integer.parseInt(line.substring(4, 5));
      }
      if(line.length() == 6 && line.charAt(1) == ',' && line.charAt(3) == ',') {
        model[Integer.parseInt(line.substring(0, 1))][Integer.parseInt(line.substring(2, 3))] = Integer.parseInt(line.substring(4, 6));
      }
      if(line.length() == 6 && line.charAt(2) == ',' && line.charAt(4) == ',') {
        model[Integer.parseInt(line.substring(0, 2))][Integer.parseInt(line.substring(3, 4))] = Integer.parseInt(line.substring(5, 6));
      }
      if(line.length() == 6 && line.charAt(1) == ',' && line.charAt(4) == ',') {
        model[Integer.parseInt(line.substring(0, 1))][Integer.parseInt(line.substring(2, 4))] = Integer.parseInt(line.substring(5, 6));
      }
      if(line.length() == 7 && line.charAt(1) == ',' && line.charAt(4) == ',') {
        model[Integer.parseInt(line.substring(0, 1))][Integer.parseInt(line.substring(2, 4))] = Integer.parseInt(line.substring(5, 7));
      }
      if(line.length() == 7 && line.charAt(2) == ',' && line.charAt(4) == ',') {
        model[Integer.parseInt(line.substring(0, 2))][Integer.parseInt(line.substring(3, 4))] = Integer.parseInt(line.substring(5, 7));
      }
      if(line.length() == 7 && line.charAt(2) == ',' && line.charAt(5) == ',') {
        model[Integer.parseInt(line.substring(0, 2))][Integer.parseInt(line.substring(3, 5))] = Integer.parseInt(line.substring(6, 7));
      }
      if(line.length() == 8) {
        model[Integer.parseInt(line.substring(0, 2))][Integer.parseInt(line.substring(3, 5))] = Integer.parseInt(line.substring(6, 8));
      }
      
    }
    
    readLine.close();
    
  }
  
  /** Creates an empty view */
  protected void createView()
  {
    setLayout( new GridLayout(16,16) ) ;
    
    this.setSize(700,600);
    
    view = new Button[16][16] ;
    
    // Create an empty view
    for( int row = 0; row < 16; row++ ) {
      for( int col = 0; col < 16; col++ )
      {
        view[row][col]  = new Button() ;
        add( view[row][col] ) ;
      }
    }
  }
  
  // Upgrades the color of the button
  protected void setColor(int[][] model)
  {
    for( int row = 0; row < 16; row++ ) {
      for( int col = 0; col < 16; col++ ) {
        
        //Changes the color of the button
        switch (model[row][col]) {
          case 1: view[row][col].setBackground(new Color(255, 0, 0));
          break;
          case 2: view[row][col].setBackground(new Color(0, 255, 0));
          break;
          case 3: view[row][col].setBackground(new Color(65, 105, 255));
          break;
          case 4: view[row][col].setBackground(new Color(255, 255, 0));
          break;
          case 5: view[row][col].setBackground(new Color(0, 255, 255));
          break;
          case 6: view[row][col].setBackground(new Color(255, 0, 255));
          break;
          case 7: view[row][col].setBackground(new Color(160, 160, 160));
          break;
          case 8: view[row][col].setBackground(new Color(255, 128, 0));
          break;
          case 9: view[row][col].setBackground(new Color(128, 128, 0));
          break;
          case 10: view[row][col].setBackground(new Color(165, 42, 42));
          break;
          case 11: view[row][col].setBackground(new Color(152, 251, 152));
          break;
          case 12: view[row][col].setBackground(new Color(46, 139, 87));
          break;
          case 13: view[row][col].setBackground(new Color(221, 160, 221));
          break;
          case 14: view[row][col].setBackground(new Color(222, 184, 135));
          break;
          case 15: view[row][col].setBackground(new Color(154, 205, 50));
          break;
          case 16: view[row][col].setBackground(new Color(205, 92, 92));
          break;
          default: view[row][col].setBackground(new Color(255, 255, 255));
          break;
        }
      }
    }
    
  }
  
  /** Updates the view from the model */
  protected void updateView()
  {
    setColor(model);
    
    for( int row = 0; row < 16; row++ ) {
      for( int col = 0; col < 16; col++ ) {
        if( model[row][col] != 0 ) {
          view[row][col].setLabel( String.valueOf(model[row][col]) ) ;
        }
        else {
          view[row][col].setLabel( "" ) ;
        }
      }
    }
  }
  
  /** This method is called by the browser when the applet is loaded */
  public void init()
  {
    try {
      createModel() ;
      createView() ;
      updateView() ;
    }
    catch(IOException e) {
      System.out.println(e);
    }
  }
  
  /** Checks if num is an acceptable value for the given row */
  protected boolean checkRow( int row, int num )
  {
    for( int col = 0; col < 16; col++ ) {
      if( model[row][col] == num ) {
        return false ;
      }
    }
    
    return true ;
  }
  
  /** Checks if num is an acceptable value for the given column */
  protected boolean checkCol( int col, int num )
  {
    for( int row = 0; row < 16; row++ ) {
      if( model[row][col] == num ) {
        return false ;
      }
    }
    
    return true ;
  }
  
  /** Checks if num is an acceptable value for the box around row and col */
  protected boolean checkBox( int row, int col, int num )
  {
    row = (row / 4) * 4 ;
    col = (col / 4) * 4 ;
    
    for( int r = 0; r < 4; r++ ) {
      for( int c = 0; c < 4; c++ ) {
        if( model[row+r][col+c] == num ) {
          return false ;
        }
      }
    }
    
    return true ;
  }
  
  /** This method is called by the browser to start the applet */
  public void start() {
    // This statement will start the method 'run' to in a new thread
    (new Thread(this)).start() ;
  }
  
  /** The active part begins here */
  public void run() {
    try {
      // Let the observers see the initial position
      Thread.sleep( 100 ) ;
      
      // Start to solve the puzzle in the left upper corner
      solve( 0, 0 ) ;
    }
    catch( Exception e ) {
      System.out.println("oops something went wrong...");
    }
  }
  
  /** Recursive function to find a valid number for one single cell */
  public void solve( int row, int col ) throws Exception {
    // Throw an exception to stop the process if the puzzle is solved
    if( col > 15 ) {
      throw new Exception( "Solution found" ) ;
    }
    
    // If the cell is not empty, continue with the next cell
    if( model[row][col] != 0 ) {
      next( row, col ) ;
    }
    else {
      // Find a valid number for the empty cell
      for( int num = 1; num < 17; num++ ) {
        if( checkRow(row,num) && checkCol(col,num) && checkBox(row,col,num) ) {
          model[row][col] = num ;
          updateView() ;
          
          // Let the observer see it
          Thread.sleep( 00000 ) ;
          
          // Delegate work on the next cell to a recursive call
          next( row, col ) ;
        }
      }
      
      // No valid number was found, clean up and return to caller
      model[row][col] = 0 ;
      updateView() ;
    }
  }
  
  /** Calls solve for the next cell */
  /** It will go column by column instead or row by row */
  public void next( int row, int col ) throws Exception {
    if( row < 15 ) {
      solve( row + 1, col) ;
    }
    else {
      solve( 0, col + 1) ;
    }
  }
}