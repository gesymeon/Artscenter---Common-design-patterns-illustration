
package ArtsCentre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Φτιάχνει το γραφικό περιβάλλον που θα χρησιμοποιείται απο τον διαχειριστή.
* @author George Simeonidis
*
*/
public class AdminGUI extends JFrame implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar;
    private JMenu rooms;
    private JMenu shows;
    private JMenu scheduleMenu;
    private JMenu file;
    
 
    public static void main(String[] args){
      
     //προσπαθεί να φορτώσει τον πολυχώρο απο τον δίσκο. Αν δεν υπάρχουν ήδη δεδομένα πολυχώρου στον δίσκο τότε δημιουργεί ένα αντικείμενο διαχειριστή με νέο πολυχώρο.
         Administrator admin = null;
        try {
            
             admin = new Administrator();
             
        } catch (IOException ex) {
            
             admin  = new Administrator(new Artspace());
           
        } catch (ClassNotFoundException ex) {
            
            JOptionPane.showMessageDialog(null, "Missing data from .jar file", null, JOptionPane.ERROR_MESSAGE, null);
        }
        finally{AdminGUI g = new AdminGUI(admin);}
        
          
    }
       
/**
* Δημιουργεί και εμφανίζει τα παράθυρα και τα μενού του γραφικού περιβάλλοντος.
* @author George Simeonidis
* @param admin Το αντικείμενο Administrator του οποίου ο πολυχώρος θα μπορεί να τροποποιηθεί.
*/
    
  public AdminGUI(Administrator admin){   
        
         URL imageURL;
         Icon icon;

         setTitle("Administrator GUI");
         setSize(1000, 1500);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         JLabel background=new JLabel();
         imageURL = getClass().getResource("images/admin.jpg");
                 if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                    background.setIcon(icon);
                                        }
         
         add(background,BorderLayout.CENTER);
         
                 
          menubar = new JMenuBar();
          file= new JMenu("Αρχεία");
          rooms = new JMenu("Αίθουσες");
          menubar.add(file);
          menubar.add(rooms);
         
         JMenu submenu = new JMenu("προσθήκη αίθουσας");
        
         JMenuItem cinema = new JMenuItem("κινηματογράφου");
         JMenuItem theatre = new JMenuItem("θεάτρου");
         JMenuItem deleteRoom = new JMenuItem("διαγραφή αίθουσας");
         
         JMenuItem save = new JMenuItem("Αποθήκευση αλλαγών στον δίσκo");
         
         JMenuItem addrow = new JMenuItem("Προσθήκη σειράς");
         JMenuItem deleterow = new JMenuItem("Διαγραφή σειράς");
         
         
         submenu.add(theatre);
         submenu.add(cinema);
         rooms.add(submenu);
         rooms.addSeparator();
         rooms.add(deleteRoom);
         rooms.addSeparator();
         rooms.add(addrow);
         rooms.add(deleterow);
         file.add(save);
         rooms.addSeparator();
         
         JMenu seats = new JMenu("Seat modification");
         JMenuItem  makeLux= new JMenuItem("convert to luxurious");
         seats.add(makeLux);
         rooms.add(seats);
         
         makeLux.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             try{
          String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του χώρου όπου θα γίνει η τροποποίηση",JOptionPane.QUESTION_MESSAGE);

          Integer row = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε τον αύξωντα αριθμό της σειράς όπου θα γίνει η τροποποίηση",JOptionPane.QUESTION_MESSAGE));
          
          Integer seat = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε τον αύξωντα αριθμό της θέσης που θα τροποποιηθεί",JOptionPane.QUESTION_MESSAGE));
          
          Room found = Administrator.workplace.existingRoom(roomName);
          
           if (found != null)
           { 
              found.makeLuxurious(row,seat); 
           }
           else 
                JOptionPane.showMessageDialog(null, "Δεν βρέθηκε χώρος με αυτό το όνομα", null, JOptionPane.ERROR_MESSAGE, null);
            }catch(NullPointerException ex){return;}
            }
            
        });
         
         
          save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             
                try {
                       Administrator.saveWorkplace();
                       
                    } catch (IOException ex){
              JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατα την εγγραφή του αρχείου στον δίσκο", null, JOptionPane.ERROR_MESSAGE, null);
                }

            }
        });
         
     //πρέπει να υπολοιηθούν έλεγχοι για το κατα πόσο οι τιμές που δίνονται απο τον χρηστη ειναι επιτρεπτές.
         cinema.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             String roomName;
             int roomCapacity;
          do
            {
            JOptionPane.showMessageDialog(null, "Το όνομα της αίθουσας πρέπει να μην είναι κενό και η χωρητικότητα μεγαλύτερη του μηδενός", null, JOptionPane.INFORMATION_MESSAGE, null);
            roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του κινηματογράφου",JOptionPane.PLAIN_MESSAGE);
            roomCapacity = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε την χωρητικότητά του",JOptionPane.PLAIN_MESSAGE));
            } while((roomName.trim().equals("") || roomCapacity<=0));
             
               
                try {
                   if(admin.addRoom(roomName, roomCapacity, RoomType.Cinema))
                   {
                       JOptionPane.showMessageDialog(null, "Η αίθουσα προστέθηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
                       
                       
                   }
                   else JOptionPane.showMessageDialog(null, "Αίθουσα με αυτό το όνομα υπάρχει ήδη", null, JOptionPane.INFORMATION_MESSAGE, null);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null,"Προέκυψε σφάλμα κατα την ανάγνωση των δεδομένων", null, JOptionPane.INFORMATION_MESSAGE, null);
                }

            }
        });
         
         theatre.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
             String roomName;
             int roomCapacity;
          do
            {
            JOptionPane.showMessageDialog(null, "Το όνομα της αίθουσας πρέπει να μην είναι κενό και η χωρητικότητα μεγαλύτερη του μηδενός", null, JOptionPane.INFORMATION_MESSAGE, null);
            roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του θεάτρου",JOptionPane.PLAIN_MESSAGE);
            roomCapacity = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε την χωρητικότητά του",JOptionPane.PLAIN_MESSAGE));
            } while((roomName.trim().equals("") || roomCapacity<=0));
               
                try {
                   if(admin.addRoom(roomName, roomCapacity, RoomType.Theatre))
                   {
                       JOptionPane.showMessageDialog(null, "Το θέατρο προστέθηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
                       
                   }
                   else JOptionPane.showMessageDialog(null, "Θέατρο με αυτό το όνομα υπάρχει ήδη", null, JOptionPane.INFORMATION_MESSAGE, null);
                   
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null,"Προέκυψε σφάλμα κατα την ανάγνωση των δεδομένων", null, JOptionPane.INFORMATION_MESSAGE, null);
                }

            }
        });
         
         deleteRoom.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
               String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του χώρου προς διαγραφή",JOptionPane.QUESTION_MESSAGE);
               
                   if(admin.deleteRoom(roomName))
                   {
                       JOptionPane.showMessageDialog(null, "Ο χώρος διαγράφηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
                       
                   }
                   else JOptionPane.showMessageDialog(null, "Δέν υπάρχει χώρος με αυτό το όνομα", null, JOptionPane.INFORMATION_MESSAGE, null);
                 }catch(NullPointerException ex){return;}
            }
        }); 
         
          deleterow.addActionListener(new ActionListener(){
           @Override
         public void actionPerformed(ActionEvent e) {
             try{
         String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του χώρου απ' όπου θα διαγραφεί η σειρά ",JOptionPane.QUESTION_MESSAGE);
         
         int position = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε τον αύξων αριθμό της σειράς που θέλεις να διαγραφεί",JOptionPane.QUESTION_MESSAGE));
         
         Room found = Administrator.workplace.existingRoom(roomName);
          
           if (found != null)
            found.deleteRow(position);
           else 
                JOptionPane.showMessageDialog(null, "Δεν βρέθηκε χώρος με αυτό το όνομα", null, JOptionPane.ERROR_MESSAGE, null);
           }catch(NullPointerException ex){return;}
            }
        });
         
         addrow.addActionListener(new ActionListener(){
           @Override
         public void actionPerformed(ActionEvent e) {
             try{
         String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του χώρου όπου θα γίνει η προσθήκη",JOptionPane.QUESTION_MESSAGE);
         
         int cap = Integer.parseInt(JOptionPane.showInputDialog( null,"Δώσε το πλήθος των θέσεων της σειράς", JOptionPane.QUESTION_MESSAGE));
         
         boolean luxurious = Boolean.parseBoolean(JOptionPane.showInputDialog( null,"Θα είναι πολυτελής; (απάντησε με true ή false)",JOptionPane.QUESTION_MESSAGE));
         
         Room found = Administrator.workplace.existingRoom(roomName);
          
           if (found != null)
            found.addRow(cap , luxurious);
           else 
                JOptionPane.showMessageDialog(null, "Δεν βρέθηκε χώρος με αυτό το όνομα", null, JOptionPane.ERROR_MESSAGE, null);
           }catch(NullPointerException ex){return;}
            }
             
        });
         
         
         createShowMenu();
         
         createScheduleMenu();
         
         
         setJMenuBar(menubar);
       
         
         pack();
         setVisible(true);
    }
   
            
/**
*Υλοποιεί το μενού και οτι αυτό περιεχεί με τίτλο "θεάματα".
*@author George Simeonidis
*/        
 private void createShowMenu(){

     shows = new JMenu("Θεάματα");
     JMenu movies = new JMenu("ταινίες");
     JMenu plays = new JMenu("θεατρικές παραστάσεις");
     
     shows.add(movies);
     shows.add(plays);
     shows.addSeparator();
     menubar.add(shows);
     
     JMenuItem addMovie = new JMenuItem ("Δημιουργία ταινίας");
     JMenuItem addPlay = new JMenuItem ("Δημιουργία θεατ. Παράστασης");       
     JMenuItem deleteShow = new JMenuItem ("Κατάργηση θεάματος");
     JMenuItem infoShow = new JMenuItem ("Πληροφορίες θεάματος");
     movies.add(addMovie);
     plays.add(addPlay);
     shows.add(deleteShow);
     shows.add(infoShow);
      addMovie.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { 
              addShow(e.getActionCommand());
            } }); 
     
     
     addPlay.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                addShow(e.getActionCommand());
            }
        }); 
     
     
     deleteShow.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                deleteShow();
            }
        }); 
     
    infoShow.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            
            infoShow();
            
        }
     }); 
    
   }
     
 
  private void infoShow(){
      
  String roomName;
  String showName; 
  try{
   do{
       showName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του θεάματος",JOptionPane.QUESTION_MESSAGE);
     }while(showName.trim().equals(""));
      
   do{
       roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του χώρου στον οποίο παρουσιάζεται το θέαμα",JOptionPane.QUESTION_MESSAGE);
     }while(roomName.trim().equals(""));
   
    Room temp = Administrator.workplace.existingRoom(roomName);
   if (temp!=null)
   {
     Show result = temp.getShow(showName);
      if (result!=null)
      { 
        JFrame newFrame = new JFrame();
        newFrame.setTitle("Πληροφορίες ταινίας");
         newFrame.setSize(1000, 1500);
         newFrame.setLocationRelativeTo(null);
         newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         JLabel details = new JLabel("<html>"+result.toString()+"</html>",SwingConstants.CENTER);
        newFrame.add(details,BorderLayout.CENTER);
        newFrame.pack();
        newFrame.setVisible(true);
      }else{JOptionPane.showMessageDialog(null, "Δεν υπάρχει θέαμα με αυτό το όνομα!", null, JOptionPane.ERROR_MESSAGE, null);return;}
   }
   else{JOptionPane.showMessageDialog(null, "Δεν υπάρχει χώρος με αυτό το όνομα!", null, JOptionPane.ERROR_MESSAGE, null);return;}
  }catch(NullPointerException ex){return;}
    
 }       
      
/**
* Καθορίζει την λειτουργικότητα των αντικειμένων μενού που αφορούν την προσθήκη θεάματος του μενού "θεάματα" με βάση την κατηγορία του εκάστωτε θεάματος προς εισαγωγή.
* @author George Simeonidis
*/      
    private void addShow(String actionCommand){
       
        PlayType genre ;
        String movieName;
        Room room; 
        Show show=null;
        String director;
        String description;
        String date=null; 
        String roomName=null;
        String temp;
        ArrayList<String> actors=new ArrayList<>();
        
        
    if (actionCommand.contains("θεατ"))
    {         
        try{
               movieName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα της παράστασης",JOptionPane.QUESTION_MESSAGE);
               
               do
               {
               temp = (String)JOptionPane.showInputDialog( null,"Δώσε διαδοχικά τα ονόματα των ηθοποιών(κενό για ολοκλήρωση εισαγωγής)",JOptionPane.QUESTION_MESSAGE);
               actors.add(temp);
               } while(!temp.trim().equals(""));
               
               director = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του σκηνοθέτη",JOptionPane.QUESTION_MESSAGE);
               description = (String)JOptionPane.showInputDialog( null,"Δώσε μια πραγματικά σύντομη περιγραφή",JOptionPane.QUESTION_MESSAGE);
               genre = PlayType.valueOf(JOptionPane.showInputDialog( null,"Δώσε το είδος της παράστασης",JOptionPane.QUESTION_MESSAGE));
               show = new Play(movieName , description, actors, director , genre);
               
                date  = (String)JOptionPane.showInputDialog( null,"Δώσε την ημερομηνία στην οποία θέλεις να ανέβει η παράσταση",JOptionPane.QUESTION_MESSAGE);
               
               roomName = (String)JOptionPane.showInputDialog( null,"Δώσε την αίθουσα στην οποία θέλεις να ανέβει η παράσταση",JOptionPane.QUESTION_MESSAGE);
             }catch(NullPointerException ex){ return;}
              catch(IllegalArgumentException ex2){JOptionPane.showMessageDialog(null, "Δεν υπάρχει τέτοιο είδος παράστασης!", null, JOptionPane.INFORMATION_MESSAGE, null);return;}
              
    }
    
   
    
    if (actionCommand.contains("ταιν"))
    {
   
        try{
               movieName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα της ταινίας",JOptionPane.QUESTION_MESSAGE);
               Double movieDuration = Double.parseDouble(JOptionPane.showInputDialog( null,"Δώσε την διάρκεια της",JOptionPane.QUESTION_MESSAGE));
                
               do
               {
               temp = (String)JOptionPane.showInputDialog( null,"Δώσε διαδοχικά τα ονόματα των ηθοποιών",JOptionPane.QUESTION_MESSAGE);
               actors.add(temp);
               } while(!temp.trim().equals(""));
               
               director = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του σκηνοθέτη",JOptionPane.QUESTION_MESSAGE);
               description = (String)JOptionPane.showInputDialog( null,"Δώσε μια πραγματικά σύντομη περιγραφή",JOptionPane.QUESTION_MESSAGE);
               show = new Movie(movieName , description, actors, director, movieDuration);
               
               date  = (String)JOptionPane.showInputDialog( null,"Δώσε την ημερομηνία στην οποία θέλεις να προβληθεί η ταινία",JOptionPane.QUESTION_MESSAGE);
               
               roomName = (String)JOptionPane.showInputDialog( null,"Δώσε την αίθουσα στην οποία θέλεις να προβληθεί η ταινία",JOptionPane.QUESTION_MESSAGE);
           }catch(NullPointerException | NumberFormatException ex){ return;}
               
                 
    }
                      room = Administrator.workplace.existingRoom(roomName);
                
                      if (room!=null)
                      {
                       if(room.addShow(show, date))
                       JOptionPane.showMessageDialog(null, "Η προσθήκη ολοκληρώθηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
                       else JOptionPane.showMessageDialog(null, "Το πρόγραμμα του χώρου είναι γεμάτο για την συγκεκριμένη ημερομηνία ή το θέαμα υπάρχει ήδη", null, JOptionPane.INFORMATION_MESSAGE, null);
                      }
                      else JOptionPane.showMessageDialog(null, "Η αίθουσα προς εισαγωγή δεν βρέθηκε", null, JOptionPane.INFORMATION_MESSAGE, null);
            
       
        
    }
    
/**
* Καθορίζει την λειτουργικότητα του αντικειμένου μενού που αφορά την διαγραφή ενός θεάματος του μενού "θεάματα".
* @author George Simeonidis
*/
    private void deleteShow(){
    
 try{     
    String showName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του θεάματος προς διαγραφή",JOptionPane.QUESTION_MESSAGE);
    
    String date = (String)JOptionPane.showInputDialog( null,"Δώσε την ημερομηνία προβολής",JOptionPane.QUESTION_MESSAGE);
      
    Room temp = Administrator.workplace.showBasedSearch(showName, date);
    
    
    if (temp!=null)
    { 
     temp.deleteShow(showName, date);     
    }
    else JOptionPane.showMessageDialog(null, "Δεν υπάρχει καταχώρηση του θεάματος στο πρόγραμμα της συγκεκριμένης ημερομηνίας", null, JOptionPane.INFORMATION_MESSAGE, null);
    
    }catch(NullPointerException ex){return;}
    }
    
    
/**
* Δημιουργεί , καθορίζει τις λειτουργίες και εμφανίζει το μενού "προγράμματα" και οτι αυτό περιέχει.
* @author George Simeonidis
*/
    public void createScheduleMenu(){
    
   scheduleMenu = new JMenu("Προγράμματα"); 
    
   JMenuItem addSchedule = new JMenuItem("προσθήκη προγράμματος");
   JMenuItem deleteSchedule = new JMenuItem("διαγραφή προγράμματος");
   JMenuItem changeSchedule = new JMenuItem("τροποποίηση προγράμματος");
   scheduleMenu.add(addSchedule);
   scheduleMenu.add(deleteSchedule);
   scheduleMenu.addSeparator();
   scheduleMenu.add(changeSchedule);
   menubar.add(scheduleMenu);     
        
      
    addSchedule.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
    String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα της αίθουσας όπου θα προστεθεί το πρόγραμμα",JOptionPane.QUESTION_MESSAGE);
    String date = (String)JOptionPane.showInputDialog( null,"Δώσε την ημερομηνία του προγράμματος",JOptionPane.QUESTION_MESSAGE);
               
     Room room = Administrator.workplace.existingRoom(roomName);
 
     if (room.addSchedule(date))
       JOptionPane.showMessageDialog(null, "Η προσθήκη του προγράμματος ολοκληρώθηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
     else JOptionPane.showMessageDialog(null, "Υπάρχει ήδη καταχωρημένο πρόγραμμα σε αυτή την ημερομηνία", null, JOptionPane.INFORMATION_MESSAGE, null);
         }catch(NullPointerException ex){return;}

            }}); 
   
    deleteSchedule.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
    String roomName = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα της αίθουσας απ'όπου θα διαγραφεί το πρόγραμμα",JOptionPane.QUESTION_MESSAGE);
    String date = (String)JOptionPane.showInputDialog( null,"Δώσε την ημερομηνία του προγράμματος προς διαγραφή",JOptionPane.QUESTION_MESSAGE);
               
     Room room = Administrator.workplace.existingRoom(roomName);
 
     if (room.deleteSchedule(date))
       JOptionPane.showMessageDialog(null, "Η διαγραφή του προγράμματος ολοκληρώθηκε με επιτυχία", null, JOptionPane.INFORMATION_MESSAGE, null);
     else JOptionPane.showMessageDialog(null, "Δεν υπήρχε εξ'αρχής καταχωρημένο πρόγραμμα σε αυτήν την ημερομηνία", null, JOptionPane.INFORMATION_MESSAGE, null);
          }catch(NullPointerException ex){return;}
 
            }}); 
   
   
   
   changeSchedule.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
   try{
String roomName = (String)JOptionPane.showInputDialog(null,"Δώσε το όνομα της αίθουσας της οποίας το πρόγραμμα θα τροποποιηθεί",JOptionPane.QUESTION_MESSAGE);
                      
Room room = Administrator.workplace.existingRoom(roomName);
 if(room==null)    
 JOptionPane.showMessageDialog(null, "Δεν υπάρχει χώρος με αυτό το όνομα", null, JOptionPane.INFORMATION_MESSAGE, null);
 else {
      displayConfigScreen(room);
      }
    }catch(NullPointerException ex){return;}

            }}); 
   

    }
        
/**
* Δημιουργεί το πλαίσιο (frame) του προγράμματος ενός χώρου και επιτρέπει την τροποποίηση του.
* @param room η αίθουσα της οποίας το πρόγραμμα θα τροποποιηθεί.
*/
 
 private void displayConfigScreen(Room room){
         JFrame newframe = new JFrame();
         
         newframe.setSize(1000, 1500);
         newframe.setLocationRelativeTo(null);
         newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
         
         HashMap<String,Schedule> schedules = (HashMap<String, Schedule>) room.getSchedules();
         
//το panel αυτο (outer) προστιθεται στο τελος στο frame, οταν θα περιεχει ολα τα υπολοιπα panels (BorderLayout)το καθενα απο τα οποια
//περιεχει την ημερομηνια(label-.NORTH) , το προγραμμα και τα θεαματα(panel σε gridlayout(0,2) με το προγραμμα να αποτελειται απο διαδοχικα labels 
// (durations) και τα θεαματα (buttons)) , βρισκοται στο .SOUTH των panels που προσθετονται στο outer).
         
         JPanel outer = new JPanel(new GridLayout(0,5,0,0));
         
         
         for (String date : schedules.keySet())
         {
             
             JPanel temp = new JPanel(new BorderLayout());
             JLabel label = new JLabel(date);
             label.setBorder(BorderFactory.createLineBorder(Color.black));
             temp.add(label,BorderLayout.NORTH);
             
             JPanel panel = new JPanel(new GridLayout(0,2,0,0));
             

             Schedule schedule = schedules.get(date);
             
             for (Entry entry : schedule.getEntries())
             {
             JLabel duration =  new JLabel("<html>"+entry.toString()+"<br>"+"</html>");  
             duration.setBorder(BorderFactory.createLineBorder(Color.black));
             panel.add(duration);
             
             
             JButton button = new JButton();
             if(entry.getShow()!=null)
            	 button.setText(entry.getShow().getName());
             else button.setText("Κενή");

               
             button.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
         
                 if(entry.getShow()!=null)
                 {
                  String showName = entry.getShow().getName();
           Object[] options = {"Αντιμετάθεση", "Μετακίνηση", "Άκυρο"};
 int result = JOptionPane.showOptionDialog(null, "Τι θέλετε να κάνετε με την συγκεκριμένη καταχώρηση", "Ερώτηση ενέργειας",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
      
        if (result == JOptionPane.CANCEL_OPTION) {
          newframe.dispose();
            
        } else if (result == JOptionPane.YES_OPTION) {
            
            String nameToSwap = (String)JOptionPane.showInputDialog( null,"Δώσε το όνομα του θεάματος με το οποίο θα γίνει η αντιμετάθεση",JOptionPane.QUESTION_MESSAGE);
            if(schedule.swapEntries(showName , nameToSwap))
            {
                //καλούμε ξανά την συνάρτηση για να ενημερωθεί το πρόγραμμα της αίθουσας της συγκεκριμένης ημερομηνίας με τις αλλαγές κ κλείνουμε το παλιό παράθυρο.
                newframe.dispose();
                displayConfigScreen(room);
            }
            else JOptionPane.showMessageDialog(null, "Το όνομα του θεάματος που δώθηκε δεν υπάρχει", null, JOptionPane.INFORMATION_MESSAGE, null);
               
                 }
                 
        else if (result == JOptionPane.NO_OPTION) {
            
           String temp = (String)JOptionPane.showInputDialog( null,"Πόσες θέσεις θα θέλατε να μετακινηθεί η εγγραφή;",JOptionPane.QUESTION_MESSAGE);
            int offset = Integer.parseInt(temp);
            
             if(schedule.moveEntry(e.getActionCommand(), offset))
              {
              //καλούμε ξανά την συνάρτηση για να ενημερωθεί το πρόγραμμα της αίθουσας της συγκεκριμένης ημερομηνίας με τις αλλαγές κ κλείνουμε το παλιό παράθυρο.
              newframe.dispose();
              displayConfigScreen(room);
              
              }
             else JOptionPane.showMessageDialog(null, "Το πλήθος των θέσεων δεν είναι εντός των επιτρεπτών ορίων \n ή υπάρχει ήδη θέαμα στην ζητούμενη καταχώρηση", null, JOptionPane.INFORMATION_MESSAGE, null);  
                 }
        
        
                 
                 }    
               else JOptionPane.showMessageDialog(null, "Δεν έχει καταχωρηθεί θέαμα", "Τίτλος",
                    JOptionPane.INFORMATION_MESSAGE);
              
            } });
               
              
              panel.add(button);
              
              panel.setBorder(BorderFactory.createLineBorder(Color.black));
                
             }
             
                
            Icon icon;
            URL imageURL = getClass().getResource("images/calendar.jpg");
            JLabel forimage = new JLabel();
                 if (imageURL!= null){
                    icon = new ImageIcon(imageURL);
                    forimage.setIcon(icon);
                                     }
             
                 temp.add(forimage,BorderLayout.CENTER);
                
             
             temp.add(panel,BorderLayout.SOUTH);
             
             temp.setBorder(BorderFactory.createLineBorder(Color.black));
         
             outer.add(temp);
             
         }
         
        newframe.add(outer);
        newframe.pack();
        newframe.setVisible(true);

    }
     
     
  
 
 }   
    
    
    
    
    
    
    
    



