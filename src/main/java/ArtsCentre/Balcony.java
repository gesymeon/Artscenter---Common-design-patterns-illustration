
package ArtsCentre;

/**
 * @author George Simeonidis
 */
import java.io.Serializable;
import java.util.ArrayList;
public class Balcony implements Serializable{

   
	private static final long serialVersionUID = 1L;
	private ArrayList<Row> rows;
    private int number_of_rows;
    
 /**
 * Αρχικοποιεί έναν εξώστη με σειρές οι οποίες περιέχουν δέκα απλές θέσεις η καθεμία.
 * @author George Simeonidis
 * @param number_of_rows το πλήθος των σειρών του εξώστη.
 */
    //δεκα θεσεις βαζουμε σε καθε σειρα του εξωστη ανεξαρτητα απ το πληθος των σειρων by default.
    public Balcony(int number_of_rows){
    	this.number_of_rows = number_of_rows;
    	rows = new ArrayList<>();
        for (int i=0;i<number_of_rows;i++){
        	rows.set(i, new PlainRow(10,i+1) );
        	}
        }
    
 /**
 * Επιστρέφει το πλήθος των σειρών του εξώστη.
 * @author George Simeonidis
 * @return το πλήθος των σειρών.
 */
    public int getNumberOfRows(){return number_of_rows;}
    
  /**
 *Επιστρέφει τις σειρές του εξώστη.
 * @author George Simeonidis
 * @return οι σειρές του εξώστη.
 */
    public ArrayList<Row> getRows(){return rows;}
    
    
    
    
    
    
    
    
}
