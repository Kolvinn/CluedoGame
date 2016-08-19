package Game;

import java.util.*;
import tiles.RoomTile;
import tiles.Tile;
import cards.CardDeck;
import jComponents.BoardObject;
import jComponents.Character;

/**
 * The board class holds the values of each location and player reference.
 * It mostly involves in dealing with getting and setting locations on the board
 * as well as most of the room functions. However the most important function
 * of the class is to generate and display the ASCII board.
 *
 * @author zhengzhon & Jeremy
 *
 */
public class Board {

	String boardString;
	//Map<Integer, Location> valueToLocation = new HashMap<Integer, Location>();
	//Map<String, Player> players = new HashMap<String, Player>();
	Set<BoardObject> boardObjects = new HashSet<BoardObject>();
	Tile[][] tiles = new Tile[24][25];

	public Board(Set<BoardObject> objects) {
		this.boardObjects=objects;
		boardString = BoardIndex.boardString;
		createBoard();
	}

	/**
	 * Searches the board to see if the given room name exists, if it does then
	 * it will return the list of coordinates corresponding to the roomTiles
	 *
	 * @param roomName
	 * @return
	 */
	public ArrayList<Coordinate> getRoomCoordinates(String roomName) {

		ArrayList<Coordinate> tiles = new ArrayList<Coordinate>();
		for(int y=0;y<25;y++){
			for(int x=0;x<24;x++){
				if(this.tiles[x][y] instanceof RoomTile && ((RoomTile) this.tiles[x][y]).roomName().equals(roomName)){
					tiles.add(new Coordinate(x,y));
				}
			}
		}
		return tiles;
	}


	/**
	 * Returns the location of storing boardObjects when inside a given room and null
	 * if the room does not exist
	 *
	 * @param roomName
	 */
	public Coordinate getRoomStorage(String roomName) {
		Set<Integer> xValues = new HashSet<Integer>(), yValues = new HashSet<Integer>();
		for(int y=0;y<25;y++){
			for(int x=0;x<24;x++){
				if(this.tiles[x][y] instanceof RoomTile && ((RoomTile) this.tiles[x][y]).roomName().equals(roomName)){
					xValues.add(x); yValues.add(y);
				}
			}
		}
		xValues.remove(Collections.max(xValues)); xValues.remove(Collections.min(xValues));
		yValues.remove(Collections.max(yValues)); yValues.remove(Collections.min(yValues));

		boolean found = false;
		for(int x: xValues){
			for(int y: yValues){
				for(BoardObject ob: boardObjects){
					//if there is at least one board object
					if(ob.getX()==x && ob.getY()==y)
						found = true;
				}
				if(!found)
					return new Coordinate(x,y);
				found =false;
			}
		}
		//invalid room
		return null;
	}
	/**
	 * Returns an instance of the entrance/exit to a room if one exists that is valid for the increment direction given
	 * @param roomName
	 * @param increment
	 * @return
	 */
	private ArrayList<Coordinate> roomExitCoordinates(String roomName){
		ArrayList<Coordinate> roomExits = new ArrayList<Coordinate>();
		for(Coordinate coo: getRoomCoordinates(roomName)){
			if(((RoomTile)tiles[coo.X][coo.Y]).isEntrance())
				roomExits.add(coo);
		}
		return roomExits;
	}
	/**
	 * Returns the coordinates of the opposing room with stairs attached to them.
	 * The method assumes the player is already in a room and able to take stairs
	 * @return
	 */
	public Coordinate getPassageCoordiantes(Character player){
		String roomName = ((RoomTile) tiles[player.getX()][player.getY()]).roomName();
		return getRoomStorage(opposingRoom(roomName));

	}
	/**
	 * Returns the name of the room connected to the given one via stairs, or null if 
	 * the given string is invalid
	 * @param currentRoom
	 * @return
	 */
	private String opposingRoom(String currentRoom){
		switch(currentRoom){
		case("Kitchen"):
			return "Study";
		case("Conservatory"):
			return "Lounge";
		case("Lounge"):
			return ("Conservatory");
		case("Study"):
			return "Kitchen";
		}
		return null;
	}

	/**
	 * Returns true if the move from the given current location to the destination location obeys
	 * the cluedo laws.
	 * @param current
	 * @param destination
	 * @return
	 */
	private boolean checkMove(Tile current, Tile destination){
		System.out.println(current.toString()+"  "+destination.toString());
		if(destination.isImpassable() || current instanceof RoomTile && destination instanceof RoomTile)
			return false;
		else if(destination instanceof RoomTile && !((RoomTile) destination).isEntrance())
			return false;
		return
			true;
	}


	/**
	 * Method that determines whether the move based on an x or y increment is viable based on the given players location.
	 * A move count is given for the player so that it will return moveCount-1 if the move is valid and return unchanged moveCount
	 * if its not.
	 * @param x
	 * @param y
	 * @param player
	 * @param moveCount
	 * @return
	 */
	public int movePlayer(int x, int y, Character player, int moveCount){
		//the destination coordinates
		int newX = player.getX()+x, newY =player.getY()+y;

		//invalid move because off the board

		if(withinBounds(newX,newY)){

			Tile current = tiles[player.getX()][player.getY()];
			Tile destination = tiles[newX][newY];

			//get a Room exit
			if(current instanceof RoomTile){
				for(Coordinate coo: roomExitCoordinates(((RoomTile) current).roomName())){
					if(checkMove(tiles[coo.X][coo.Y], tiles[coo.X+x][coo.Y+y])){
						System.out.println("found room exit");
						//found the room exit now can update board
						player.setCoordinate(new Coordinate(coo.X+x,coo.Y+y));
						return moveCount--;
					}
				}
			}
			else{				
				if(checkMove(current,destination)){
					if(destination instanceof RoomTile){
						player.setCoordinate(getRoomStorage(((RoomTile) destination).roomName()));
						return 0;
					}
					else{
						player.setCoordinate(new Coordinate(newX,newY));
						//int move = moveCount-1;
						return --moveCount;
					}
				}
			}
		}
		return moveCount;
	}

	/**
	 * Returns true if given x and y coordinates are within the board
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean withinBounds(int x, int y){
		if(x>23 || x<0 || y >24 || y <0){
			System.out.println("is false");
			return false;
		}
		return true;
	}

	/**
	 * Creates the board using the boardString values
	 *
	 */
	public void createBoard() {
		String[] strings = boardString.split(",");
		for (String s : strings) {

			// if the signal is for room, create a new Room because the parsed
			// roomString has a different code to
			// the rest of the stored locations
			if (s.charAt(0) == 'R') {
				createRoom(s.substring(1));
				//int location = Integer.parseInt(s.substring(room.name().length() + room.boardCode().length() + 1));
			} // else create a new location
			else {
				int location = Integer.parseInt(s.substring(4));
				switch (s.charAt(0)) {
				case ('S'):
					setTile(location, true);
					break;
				case ('W'):
					setTile(location, true);
					break;
				default:
					setTile(location, false);
				}
			}
		}
	}

	/**
	 * Creates a room location stored in the map of locations.
	 *
	 * @param boardCode
	 * @param location
	 * @return
	 */
	private void createRoom(String parsedRoom) {
		String roomName = "";
		int i = 0;
		boolean entrance = false;
		while (parsedRoom.length() > i) {
			entrance = false;
			if (parsedRoom.charAt(i) == ' ' || parsedRoom.charAt(i) == 'E' || parsedRoom.charAt(i) == '_') {
				if (parsedRoom.charAt(i) == 'E')
					entrance = true;
				break;
			}
			roomName += parsedRoom.charAt(i);
			i++;
		}
		String boardCode = parsedRoom.substring(roomName.length(), roomName.length() + 3);
		int location = Integer.parseInt(parsedRoom.substring(roomName.length() + 3));
		setRoomTile(location, roomName, entrance);
		//return new Room(boardCode, location, roomName, entrance);
	}

	private void setRoomTile(int location, String roomName, boolean entrance){
		if(location==0)
			tiles[0][0]= new RoomTile(false, roomName, entrance);
		else{
			int y = location/24;
			int x = location - (y*24);
			tiles[x][y]= new RoomTile(false, roomName, entrance);
		}
	}
	private void setTile(int location, boolean impassable){
		if(location==0)
			tiles[0][0]= new Tile(impassable);
		else{
			int y = location/24;
			int x = location - (y*24);
			tiles[x][y]= new Tile(impassable);
		}
	}


	/**
	 * This method returns a list of Strings that indicate what Actions the
	 * given player has at the current time. It is dependent on where the player
	 * is on the board and what previous actions the player might have taken,
	 * adhering to the cluedo rules.
	 *
	 * @param player
	 * @param actions
	 * @return
	 */
	public Set<String> availableActions(Character player, String lastAction) {

		Set<String> actionsList = new HashSet<String>();

		if(lastAction == "Accuse"){
			return actionsList;
		}
		//can always accuse or pass
		actionsList.add("Accuse");
		actionsList.add("Pass");
		// player is starting their turn
		if (lastAction == null) {
			if (tiles[player.getX()][player.getY()] instanceof RoomTile) {
				actionsList.add("Roll");
				actionsList.add("Suggest");
				if (((RoomTile) tiles[player.getX()][player.getY()]).hasStairs()){
					actionsList.add("Stairs");
				}
			} else
				actionsList.add("Roll");

		}
		else if (lastAction == "Stairs") {
			//end of turn if they have taken the stairs
			actionsList.removeAll(actionsList);
		}
		// player is currently in turn
		else {
			if (tiles[player.getX()][player.getY()] instanceof RoomTile) {
				if (lastAction.equals("Roll")) {
					actionsList.add("Suggest");
				}
			}

		}

		if (lastAction != null && lastAction.equals("Pass"))
			actionsList = new HashSet<String>();

		return actionsList;
	}


}