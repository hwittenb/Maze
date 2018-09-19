/*
Group 8 - Harrison Wittenbrook, Kemi Araba-Owoyele, Romi Patel, Matt Sanders
Section 07
*/
package maze;
import java.util.*;
/**
 * Created by Tulin Kilinc 1/19/2017.
 * KSU Programming Principles II
 */

public class Maze {
	Scanner scan = new Scanner(System.in);

    private char direction;
    private int x;  // x position of the mouse
    private int y;  //y position of the mouse
    private boolean exitFound = false;
    private int[][] arrMaze;

    //constructor for Maze that takes an array for the maze
    public Maze(int[][] arrMaze) {
    	this.arrMaze = new int[arrMaze.length][arrMaze[0].length];
    	for(int row = 0; row<arrMaze.length; row++){
    		for(int column = 0; column<arrMaze[row].length; column++){
    			this.arrMaze[row][column] = arrMaze[row][column];
    		}
    	}
    	//sets the initial position to the bottom left of the maze
    	x = 0;
    	y = arrMaze.length - 1;
     }

    //Prints out the maze without solution
    public void displayMaze() {
    	for(int row = 0; row<arrMaze.length; row++){
    		for(int column = 0; column<arrMaze[row].length; column++){
    			if(arrMaze[row][column] == 0){
    				System.out.print("#");
    			}
    			else{
    				System.out.print(" ");
    			}
    		}
    		System.out.println("");
    	}

    }

    //displays the Maze with the path taken
    public void displayPath() {
    	for(int row = 0; row<arrMaze.length; row++){
    		for(int column = 0; column<arrMaze[row].length; column++){
    			if(arrMaze[row][column] == 0){
    				System.out.print("#");
    			}
    			//wherever the mouse is currently located an @ will be printed
    			else if(row==y && column==x){
    				System.out.print("@");
    			}
    			//a 3 will be used to represent a space leading to a dead end
    			else if(arrMaze[row][column] == 1 || arrMaze[row][column] == 3){
    				System.out.print(" ");
    			}
    			//a 2 will be used to represent a space traveled on
    			else if(arrMaze[row][column] == 2){
    				System.out.print("~");
    			}
    		}
    		System.out.println("");
    	}

    }


    public boolean takeStep() {
        //complete the code here
    	boolean complete = true;
    	do{
	    	System.out.println("Which direction would you like to go? (north, south, east, or west)");
	    	direction = scan.nextLine().toLowerCase().charAt(0);
	    	//sees which direction the user entered and goes that way
	    	switch(direction){
	    	case 'n':
	    		moveNorth();
	    		complete = true;
	    		break;
	    	case 's':
	    		moveSouth();
	    		complete = true;
	    		break;
	    	case 'e':
	    		moveEast();
	    		complete = true;
	    		break;
	    	case 'w':
	    		moveWest();
	    		complete = true;
	    		break;
	    	default:
	    		complete = false;
	    		System.out.println("Invalid entry");
	    	}
    	}
    	while(!complete);
    	displayPath();

        return isAnExit();
    }

    //decreases y by 1 if it is a valid space
    private void moveNorth() {
        //complete the code here
    	if(y-1>=0 && arrMaze[y-1][x] != 0){
    		arrMaze[y][x] = 2;
    		y--;
    	}

    }

    //increases y by 1 if it is a valid space
    private void moveSouth() {
        //complete the code here
    	if(y+1<arrMaze.length && arrMaze[y+1][x] != 0){
    		arrMaze[y][x] = 2;
    		y++;
    	}

    }

    //increases x by 1 if it is a valid space
    private void moveEast() {
        //complete the code here
    	if(x+1<arrMaze[0].length && arrMaze[y][x+1] != 0){
    		arrMaze[y][x] = 2;
    		x++;
    	}

    }

    //decreases x by 1 if it is a valid space
    private void moveWest() {
        //complete the code here
    	if(x-1>=0 && arrMaze[y][x-1] != 0){
    		arrMaze[y][x] = 2;
    		x--;
    	}

    }

    //checks if the mouse goes out of the array and is not at the beginning of the maze
    //if the mouse goes out of the array it is the exit
    private boolean isAnExit() {
        //complete the code here
    	if(x==0){
    		exitFound = false;
    	}
    	else if(y==0 || arrMaze.length-1==y || arrMaze[0].length-1==x){
    		exitFound = true;
    	}
        return exitFound;
    }

    //finds the path without stopping at every step
    public void findExit() {
	       //complete the code here
    	do{
    		int[] openPath = whichWay(1);
    		//if(openPath[0]<1 && openPath[1]<1 && openPath[2]<1 && openPath[3]<1)
    		//checks if north has the most open spaces
    		if(openPath[0]!=0 && openPath[0]>=openPath[1] && openPath[0]>=openPath[2] && openPath[0]>=openPath[3]){
    			arrMaze[y][x] = 2;
    			y--;
    		}
    		//checks if east has the most open spaces
    		else if(openPath[1]!=0 && openPath[1]>=openPath[2] && openPath[1]>=openPath[3]){
    			arrMaze[y][x] = 2;
    			x++;
    		}
    		//checks if south has the most open spaces
    		else if(openPath[2]!=0 && openPath[2]>=openPath[3]){
    			arrMaze[y][x] = 2;
    			y++;
    		}
    		//checks if west has the most open spaces
    		else if(openPath[3]!=0){
    			arrMaze[y][x] = 2;
    			x--;
    		}
    		//if there are no open spaces, it checks for previously checked spaces
    		else{
    			boolean free = false;
    			while(!free){
	    			int[] freePass = whichWay(1);
    				int[] lastPass = whichWay(2);
	    			//checks if north is free
    				if(freePass[0]!=0 && freePass[0]>=freePass[1] && freePass[0]>=freePass[2] && freePass[0]>=freePass[3]){
	    				arrMaze[y][x] = 2;
	    				y--;
	    				free = true;
	    			}
    				//checks if east is free
    				else if(freePass[1]!=0 && freePass[1]>=freePass[2] && freePass[1]>=freePass[3]){
    					arrMaze[y][x] = 2;
    					x++;
    					free = true;
    				}
    				//checks if south is free
    				else if(freePass[2]!=0 && freePass[2]>=freePass[3]){
    					arrMaze[y][x] = 2;
    					y++;
    					free = true;
    				}
    				//checks if west is free
    				else if(freePass[3]!=0){
    					arrMaze[y][x] = 2;
    					x--;
    					free = true;
    				}
    				//checks if north has the most previously traveled spaces
    				else if(lastPass[0]!=0 && lastPass[0]>=lastPass[1] && lastPass[0]>=lastPass[2] && lastPass[0]>=lastPass[3]){
	    				arrMaze[y][x] = 3;
	    				y--;
	    			}
	    			//checks if east has the most previously traveled spaces
	    			else if(lastPass[1]!=0 && lastPass[1]>=lastPass[2] && lastPass[1]>=lastPass[3]){
	    				arrMaze[y][x] = 3;
	    				x++;
	    			}
	    			//checks if south has the most previously traveled spaces
	    			else if(lastPass[2]!=0 && lastPass[2]>=lastPass[3]){
	    				arrMaze[y][x] = 3;
	    				y++;
	    			}
	    			//checks if west is greater than 0
	    			else if(lastPass[3]!=0){
	    				arrMaze[y][x] = 3;
	    				x--;
	    			}
    			}
    		}
    	}
    	while(!isAnExit());
    	displayPath();

    }
    
    private int[] whichWay(int i){
    	//0=north, 1=east, 2=south, 3=west
    	int[] direction = new int[4];
    	
    	//checks north
    	if (y - 1 >= 0 && arrMaze[y - 1][x] == i){
    		int tempx = x;
    		int tempy = y;
    		while(tempy-1>=0 && arrMaze[tempy-1][tempx] == i){
    			tempy--;
    			direction[0]++;
    		}
    	}
    	
    	//checks east
    	if (x + 1 < arrMaze[0].length && arrMaze[y][x + 1] == i){
    		int tempx = x;
    		int tempy = y;
    		while(tempx+1<arrMaze[0].length && arrMaze[tempy][tempx+1] == i){
    			tempx++;
    			direction[1]++;
    		}
    	}
    	
    	//checks south
    	if (y + 1 < arrMaze.length && arrMaze[y + 1][x] == i){
    		int tempx = x;
    		int tempy = y;
    		while(tempy+1<arrMaze.length && arrMaze[tempy+1][tempx] == i){
    			tempy++;
    			direction[2]++;
    		}
    	}
    	
    	//checks west
    	if (x - 1 >= 0 && arrMaze[y][x - 1] == i){
    		int tempx = x;
    		int tempy = y;
    		while(tempx-1>=0 && arrMaze[tempy][tempx-1] == i){
    			tempx--;
    			direction[3]++;
    		}
    	}
    	
    	return direction;
    }
}