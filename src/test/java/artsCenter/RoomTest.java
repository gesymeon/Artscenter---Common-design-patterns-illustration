package artsCenter;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import artsCenter.Cinema;
import artsCenter.Room;
import artsCenter.RoomType;
import artsCenter.Row;
import artsCenter.Schedule;
import artsCenter.Show;
import artsCenter.Theater;

public class RoomTest {
    
    private Room cinema; 
    private Room theatre;
  
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            cinema = new Cinema("cinema",30);
            theatre = new Theater("theatre",30);
        }catch (ParseException ex) {
            System.out.println("parsing error occured");
        } catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    /**
     * Incremental positions based indexing
     * Check that make luxurious method can work for all rows and seats as expected
     * for all types of facilities
     */
    @Test
    public void testMakeLuxurious() {
        Room instance = cinema;
        
       for (int i=1; i <= instance.getRows().size() ;i++)
    	   for (int j=1; j <= instance.getRow(i).numberOfSeats; j++)
       {
       	assertEquals(instance.getRow(i).getSeat(j).isLuxurious() , false);
       	instance.makeLuxurious(i, j);
       	assertEquals(instance.getRow(i).getSeat(j).isLuxurious() , true);
       }
       
        instance = theatre;
       
        for (int i=1; i <= instance.getRows().size() ;i++)
     	   for (int j=1; j <= instance.getRow(i).numberOfSeats; j++)
        {
         assertEquals(instance.getRow(i).getSeat(j).isLuxurious() , false);
         instance.makeLuxurious(i, j);
         assertEquals(instance.getRow(i).getSeat(j).isLuxurious() , true);
        } 
       
}

    /**
     * Incremental positions based indexing
     * 
     */
    @Test
    public void testDeleteRow() {
    	Room instance = cinema;
    	int position = 1;
    	int seat = 1;
    	//We verify that the deleted row's data are lost
        instance.makeLuxurious(position, seat);
        assertEquals(instance.getRow(position).getSeat(seat).isLuxurious() , true);
        instance.deleteRow(position);
        assertEquals(instance.getRow(position).getSeat(seat).isLuxurious() , false);
        
        //we check that the rows' incremental indexing is preserved correctly 
        for (int i=1; i<=instance.getRows().size(); i++)
        	{	instance.deleteRow(i);
        		
        		for (int j=1; j<=instance.getRows().size(); j++)
        			assertEquals(instance.getRow(i).index,j);
        		
        	}
        
        
    }


    /**
     * Test of equals method, of class Room.
     */
    @Test
    public void testEquals() {
        
        Room instance = cinema;
        Room instanceSame = null;
        Room instanceDifferent = null;
		try {
			instanceSame = new Cinema("cinema",30);
			instanceDifferent = new Cinema("AnotherName",30);
		} catch (ParseException e) {
			fail("parsing error occured");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals(instance , instanceSame);
        assertNotSame(instance , instanceDifferent);       
    }

    /**
     * Verify that a schedule is not created for today's date automatically when we create a new facility.
     */
    @Test
    public void testGetSchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String today_string = sdf.format(today);
        Room instance = cinema;
        Schedule result = instance.getSchedule(today_string);
        assertNull(result);
    }

    
    /**
     * Add today's schedule into a facility.
     * @throws CloneNotSupportedException 
     */
    @Test
    public void testAddSchedule() throws CloneNotSupportedException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        String today_string = sdf.format(today);
        
        Room instance = cinema;
        boolean expResult = true;
        boolean result = instance.addSchedule(today_string);
        assertEquals(expResult, result);
        
        //verify that it was correctly added
        
        Schedule current = instance.getSchedule(today_string);
        assertNotNull(current);
        
        
    }

 
    /**
     * Test of addRow method, of class Room.
     */
    @Test
    public void testAddRow() {
        
        int capacity = 0;
        boolean lux = false;
        Room instance = null;
        instance.addRow(capacity, lux);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRow method, of class Room.
     */
    @Test
    public void testGetRow() {
        
        int position = 0;
        Room instance = null;
        Row expResult = null;
        Row result = instance.getRow(position);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addShow method, of class Room.
     * @throws CloneNotSupportedException 
     */
    @Test
    public void testAddShow() throws CloneNotSupportedException {
        
        Show show = null;
        String date = "";
        Room instance = null;
        boolean expResult = false;
        boolean result = instance.addShow(show, date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteShow method, of class Room.
     */
    @Test
    public void testDeleteShow() {
        
        String name = "";
        String date = "";
        Room instance = null;
        boolean expResult = false;
        boolean result = instance.deleteShow(name, date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveShow method, of class Room.
     */
    @Test
    public void testMoveShow() {
        
        String name = "";
        String date = "";
        int offset = 0;
        Room instance = null;
        boolean expResult = false;
        boolean result = instance.moveShow(name, date, offset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of swapShows method, of class Room.
     */
    @Test
    public void testSwapShows() {
       
        String name1 = "";
        String name2 = "";
        String date = "";
        Room instance = null;
        boolean expResult = false;
        boolean result = instance.swapShows(name1, name2, date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShow method, of class Room.
     */
    @Test
    public void testGetShow() {
        
        String name = "";
        Room instance = null;
        Show expResult = null;
        Show result = instance.getShow(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}