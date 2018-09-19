package maze;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] maze = {
				{0,0,0,0},
				{1,0,1,1},
				{1,0,1,0},
				{1,1,1,0},
				{1,1,0,0},
				{1,0,0,0}};
		Maze test = new Maze(maze);
		test.findExit();
		}
	}


