/*
 * Originally written by Marquette professor Dr. Richard J. Povinelli, 2012. Modified by Ben Mol, 2018.
 */

package graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Provides a text based game board. A two dimensional array is used to represent the screen. Changes to the screen are
 * done by changing the values in the using setCharAtLocation and calling repaint.
 */
public class TextBoard extends Thread {

  /** The default title on the JFrame */
  private static final String DEFAULT_TITLE     = "TextBoard";

  /** The character used to initialize the board and the screen. */
  private static final char   DEFAULT_CHAR      = ' ';

  /** The default font size. */
  private static final int    DEFAULT_FONT_SIZE = 12;

  /** The frame used for the user interface */
  private JFrame              frame;

  /** The board pane used to display the board. */
  private JTextArea           textArea;

  /** Contains the characters to be placed on the screen. */
  private char[][]            board;

  /** The font size for the board. This will effect the size of the board. */
  private int                 fontSize          = DEFAULT_FONT_SIZE;

  /** The number of rows in the board. */
  private int                 rows;

  /** The number of columns in the board. */
  private int                 columns;

  /**
   * Create the TextBoard. Checks to make sure all the parameters are positive. Initializes the board and graphics
   * components.
   * 
   * @param rows The number of rows of board.
   * @param columns The number of columns of board.
   * @param fontSize The font size for the board.
   */
  public TextBoard(int rows, int columns, int fontSize) {

    if (fontSize <= 0) {
      this.fontSize = DEFAULT_FONT_SIZE;
    } // if
    else {
      this.fontSize = fontSize;
    } // else

    board = new char[rows][columns];
    this.rows = rows;
    this.columns = columns;
    setBoard(DEFAULT_CHAR);
    initComponents();

  } // TextBoard

  /**
   * Adds a KeyListener to the textBoard.
   * 
   * @param keyListener Listener for key events on the TextBoard
   */
  public void addKeyListener(KeyListener keyListener) {
    textArea.addKeyListener(keyListener);
  } // addKeyListener

  /**
   * Converts a char into something displayable. All non-visible characters are converted into spaces.
   * 
   * @param c The character to insure is displayable.
   * @return The displayable character.
   */
  private char convertCharToDisplayable(char c) {
    c = (char) (c % 256);
    if (Character.isISOControl(c) || Character.isWhitespace(c) || Character.isSpaceChar(c)) {
      c = ' ';
    } // if
    return c;
  } // convertText

  /**
   * Converts the board into a string. This allows the board to be displayed as a matrix of characters.
   * 
   * @return The html formated board.
   */
  private String createText() {
    String text = "";
    for (int row = 0; row < rows; row++) {
      text += new String(board[row]) + '\n';
    } // for

    text += new String(board[rows - 1]);

    return text;
  } // createText

  /**
   * Initializes the graphical interface. A non-resizable frame is created. The size is determined by painting the
   * default board on a JTextPane. The board is managed as html, which allows the board matrix to be
   * correctly displayed.
   * 
   */
  private void initComponents() {
    String text; // The string version of the board.
    Dimension minimumSize; // The minimum size of the textArea.

    frame = new JFrame(DEFAULT_TITLE);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(40, 40, 0, 0); // The zero sizes are adjusted by pack below.

    textArea = new JTextArea();
    textArea.setFont(new Font("monospaced", Font.PLAIN, fontSize));
    textArea.setEditable(false);
    textArea.setDoubleBuffered(true);

    text = createText();
    textArea.setText(text);
    minimumSize = textArea.getMinimumSize();
    textArea.setMinimumSize(minimumSize);

    frame.add(textArea);
    frame.pack();
  } // initComponents

  /**
   * Repaints the textArea with the most recent board.
   */
  public void repaint() {
    String text; // The html version of the board.

    text = createText();
    textArea.setText(text);
    textArea.repaint();
  }

  /**
   * Used to display the board and run the thread.
   */
  @Override public void run() {
    try {
      frame.setVisible(true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  } // run

  /**
   * Initializes the board to the given character.
   * 
   * @param c The character used to initialize the matrix.
   */
  public void setBoard(char c) {
    c = convertCharToDisplayable(c);
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        board[row][column] = c;
      } // for
    } // for
  } // setText

  /**
   * Sets the character at the given row and column. Makes sure the row and column don't extend outside the board.
   * 
   * @param row The row at which to set the character.
   * @param column The column at which to set the character.
   * @param c The character to set.
   * @return Boolean - True if row and column were in bounds, false otherwise
   */
  public boolean setCharAtLocation(int row, int column, char c) {
    c = convertCharToDisplayable(c);
    if (row < rows && row >= 0 && column < columns && column >= 0) {
      board[row][column] = c;
      return true;
    }
    else {
    	return false;
    }
  } // setText
  
  /**
   * Sets the string starting at the given row and column. Makes sure the row and column don't extend outside the board.
   * 
   * @param row The row at which to start the string
   * @param column The column at which to start the string
   * @param c The character to set.
   * @return Boolean - True if row and column were in bounds and the string fit in bounds, false otherwise
   */
  public boolean setStringAtLocation(int row, int column, String str) {
    boolean success = true;
    for (int ii = 0; ii < str.length(); ii++)
    {
    	success = setCharAtLocation(row, column + ii, str.charAt(ii));
    }
    return success;
  } // setText

  /**
   * Sets the title of the TextBoard.
   * 
   * @param title The title to displayed in the frame's boarder.
   */
  public void setTitle(String title) {
    frame.setTitle(title);
  } // setTitle
  
  /**
   * Clears the board of all characters, setting them all to ' '
   */
  public void clearBoard() {
	  setBoard(' ');
  }
  
  /**
   * Get the number of rows on the board
   * @return rows
   */
  public int getNumRows() {
	  return rows;
  }
  
  /**
   * Get the number of columns on the board
   * @return columns
   */
  public int getNumColumns() {
	  return columns;
	 
  }
}

// TextBoard