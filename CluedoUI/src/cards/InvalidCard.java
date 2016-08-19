package cards;

/**
 * The exception thrown when an error relating to cards arises
 *
 * @author zhengzhon & Jeremy
 *
 */
public class InvalidCard extends Exception {
	public InvalidCard(String s){
		super(s);
	}
}
