import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class MySudokuBoard {

    private int[][] board;
    
    public MySudokuBoard(String filename) {
        board = new int[9][9];
        try {
            Scanner file = new Scanner(new File(filename));
            for (int r = 0; r < 9; r++) {
                String line = file.nextLine();
                for (int c = 0; c < 9; c++) {
                    char ch = line.charAt(c);
                    if (ch == '.') {
                        board[r][c] = 0;
                    } else {
                       board[r][c] = ch - '0';
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + filename);
        }
    }

    public String toString() {
      String result = "";
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] == 0) {
               result += "X ";   
            } else {
               result += board[r][c] + " ";
            }
         }
         result += "\n";
      }

      return result;
    }
    
    
    
    public boolean isValid() {
      return checkRows() && checkColumns() && checkMiniSquares() && allZero();
    }
    
    private boolen allZero() {
       for(int r = 0 ; r < 9; r++) {
         for(int c = 0 ; c < 9; c++) {
            if(board[r][c] != 0) {
               return true;
            }
         }
         return false;
       }
    }
    
    
    private boolean checkRows() {
      Set<Integer> duplicate = new HashSet<>();
      for(int c = 0; c < 9; c++){
         for(int r = 0; r < 9; r++){ //tracking for the role           
            if(duplicate.contains(board[c][r])) {
               return false;
            } else {
               duplicate.add(board[c][r]);
            }            
         }
      }
      return true;
    }
      
      
   private boolean checkColumns() { 
      Set<Integer> duplicate = new HashSet<>();
      for(int r = 0; r < 9; r++) {
         for(int c = 0; c < 9; c++) {
           if (duplicate.contains(board[r][c])) {
               return false;
            } else {
               duplicate.add(board[r][c]);
            }           
         }
      }
      return true;
   }
   
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   private boolean checkMiniSquares() {
      for(int a = 1; a <= 9; a++) {
         int[][] mini = miniSquare(a);
         Set<Integer> track1 = new HashSet<>();
         for(int r = 0; r < 3; r++) {
            for(int c = 0 ; c < 3; c++) {
               if(track1.contains(mini[r][c])) {
                  return false;
               } else {
                  track1.add(mini[r][c]);
               }
            }
         }
      }
      return true;
   }
 
   public boolean isSolved() {
      if(!isValid()) {
         return false;
      }
      for(int r = 0; r < 9; r++) {
         for(int c = 0; c < 9; c++) {
            if(board[r][c] == 0) {
               return false;
            }
         }
      }
      return true;
   }      
 
}
 
 /*
2 X X 1 X 5 X X 3 
X 5 4 X X X 7 1 X 
X 1 X 2 X 3 X 8 X 
6 X 2 8 X 7 3 X 4 
X X X X X X X X X 
1 X 5 3 X 9 8 X 6 
X 2 X 7 X 1 X 6 X 
 */