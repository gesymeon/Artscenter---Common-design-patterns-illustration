
package ArtsCentre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
* Φτιάχνει το γραφικό περιβάλλον που θα χρησιμοποιείται απο τον χρήστη.
* @author George Simeonidis
*/
public class UserGUI extends JFrame implements Serializable{
    
private EndUser user;
private JMenu file;

//παρουσιαζονται στοιχεια για καθε αιθουσα και κουμπια που οδηγουν στις ημερομηνιες προβολων αυτης που θα επιλεξουμε. 
//οι καταχωρησεις για καθε αιθουσα γινονται σε ενα πλεγμα το πολυ 5 στηλων και αποτελουνται απο μια ετικετα με τις πληροφοριες
//και ενα κουμπι για την επιλογη της αιθουσας.

//ο πολυχώρος έχει πάντα την πιο πρόσφατη μορφή μετά απο αλλαγές του διαχειριστή.
public static void main(String[] args)  {

    //ο πολυχώρος πρέπει να είναι κοινός και για το διαχειριστή και για τους χρήστες , επομένως τον θέτουμε static μεταβλητή του διαχειριστή.
    
    try {
     Administrator.loadWorkplace();
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null,"The art centre must have already been created by the administrator", null, JOptionPane.ERROR_MESSAGE, null);
        System.exit(0);
    } catch (ClassNotFoundException ex) {
       JOptionPane.showMessageDialog(null, "Missing data from art centre's class in .jar file", null, JOptionPane.ERROR_MESSAGE, null);
    }
    
     
 String name =JOptionPane.showInputDialog("Username : ");
               while (name.equals(""))
             name = JOptionPane.showInputDialog("It is mandatory...");
 
 
          Object[] options = {"Yes", "'No", "Cancel"};
 int result = JOptionPane.showOptionDialog(null, "Do you have an account;", "Basic question",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
      
        if (result == JOptionPane.CANCEL_OPTION) {
           System.exit(0);
        } else if (result == JOptionPane.YES_OPTION) {
             
           try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt")))
               {while (true)
                   {
                   
                   EndUser temp = (EndUser)in.readObject();
                   
                   if(temp.getName().equals(name))
                    {

                       UserGUI gui = new UserGUI(temp);  
                       break;
                    }
                   
                   }
                   }catch (EOFException ex3){JOptionPane.showMessageDialog(null, "Your username was not found", null, JOptionPane.INFORMATION_MESSAGE, null);}
                    catch (IOException ex4) {JOptionPane.showMessageDialog(null, "Input error while reading from data disk", null, JOptionPane.ERROR_MESSAGE, null);}
                    catch (ClassNotFoundException ex5){JOptionPane.showMessageDialog(null, "Missing data from .jar file about the application's classes", null, JOptionPane.ERROR_MESSAGE, null);}
              

        } else if (result == JOptionPane.NO_OPTION) {
          
            
            
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt")))
               {while (true)
                   {
                   
                   EndUser temp = (EndUser)in.readObject();
                   
                   if(temp.getName().equals(name))
                    {
                     JOptionPane.showMessageDialog(null, "This username is already being used!", null, JOptionPane.ERROR_MESSAGE, null);
                     System.exit(0);
                    }
                   
                   }
                   }catch (EOFException ex3){}
                    catch (IOException ex4) {JOptionPane.showMessageDialog(null, "Input error while reading from data disk", null, JOptionPane.ERROR_MESSAGE, null);}
                    catch (ClassNotFoundException ex5){JOptionPane.showMessageDialog(null, "Missing data from .jar file regarding the User class", null, JOptionPane.ERROR_MESSAGE, null);}
            
    
  String phone = JOptionPane.showInputDialog("Input your phone number : ");
  UserGUI gui = new UserGUI(new EndUser(name,phone));     
            
    }
  
}

/**
* Δημιουργεί και εμφανίζει τα παράθυρα και τα μενού του γραφικού περιβάλλοντος.
* @param user the current application's user
*/

    public UserGUI(EndUser user){
              
         this.user = user;
         
         setTitle("User GUI");
         setSize(1000, 2500);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
         JMenuBar menubar = new JMenuBar();
      
         file = new JMenu("Files");
         
         JMenuItem save = new JMenuItem("Save user data");
         
         file.add(save);
         
          save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
             
                try {
                       user.saveAccount();
                       
                    }catch (IOException ex){
              JOptionPane.showMessageDialog(null, "An error occurred while writing data to disk", null, JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });
         

         JPanel panel = new JPanel();
         panel.setLayout(new GridLayout(0, 5, 10, 5));
         for (int i = 0 ; i < Administrator.workplace.getRooms().size() ; i++)
         {
             
             Room currentRoom = Administrator.workplace.getRooms().get(i);
             
             URL imageURL;
             Icon icon;
             
             JButton button = new JButton();
             button.addActionListener(new ActionListener() {      
                 @Override
            public void actionPerformed(ActionEvent e) {           
                displaySchedule(currentRoom);              
            }
             });
                     
                    
             
             JPanel temp = new JPanel(new GridLayout(2,1,5,5));
             JLabel label = new JLabel("<html>"+Administrator.workplace.getRooms().get(i).toString()+"</html>", SwingConstants.CENTER);
             
             
             if (Administrator.workplace.getRooms().get(i).getFacilityType() == RoomType.Cinema)     
             { imageURL = getClass().getResource("images/cinema.gif");
                 if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                    button.setIcon(icon);
                                        }
             }
                 else if (Administrator.workplace.getRooms().get(i).getFacilityType()==RoomType.Theatre) 
                     { imageURL = getClass().getResource("images/theatre.gif");
                 if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                    button.setIcon(icon);
                                        }
                     }
             
         temp.add(label);             
         temp.add(button);

         panel.add(temp);
  
         }
         
         
         menubar.add(file);
         add(menubar,BorderLayout.NORTH);
         
          add(panel,BorderLayout.CENTER);       
          

         pack();
         setVisible(true);
     }
    
   
/**
* Εμφανίζει το πρόγραμμα του χώρου της παραμέτρου κατάλληλα παρουσιασμένο.
* @param room Ο χώρος του οποίου το πρόγραμμα θα εμφανιστεί.
*/
    private void displaySchedule(Room room)
    {
    
    JFrame frame = new JFrame(room.getName()+"'s schedule");
         frame.setSize(1000, 1500);
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
         HashMap<String,Schedule> schedules = (HashMap<String, Schedule>) room.getSchedules();
         
// το panel αυτο (outer) προστιθεται στο τελος στο frame , οταν θα περιεχει ολα τα υπολοιπα panels (BorderLayout)το καθενα απο τα οποια
// περιεχει την ημερομηνια(label-.NORTH) , το προγραμμα και τα θεαματα(panel σε gridlayout(0,2) με το προγραμμα να αποτελειται απο διαδοχικα labels 
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
             else button.setText("Empty");

               
             button.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {

                 if(entry.getShow()!=null)
                  displayRoom(entry);
               else JOptionPane.showMessageDialog(null, "No show has been registered", "Title",
                    JOptionPane.INFORMATION_MESSAGE);
                 }
        });
                     
              panel.add(button);
              panel.setBorder(BorderFactory.createLineBorder(Color.black));    
               
             }
             //στο κεντρο του temp μπορουμε να προσθεσουμε εικονες
             temp.add(panel,BorderLayout.SOUTH);
             
             temp.setBorder(BorderFactory.createLineBorder(Color.black));
         
             outer.add(temp);
             
         }
         
         frame.add(outer);
         frame.pack();
         frame.setVisible(true);
    }
   
/**
* Εμφανίζει την αίθουσα στην οποία θα παρουσιαστεί το θέαμα της καταχώρησης.
* @author George Simeonidis
* @param room Η αίθουσα στην οποία θα παρουσιαστεί το θέαμα.
*/
    private void displayRoom(Entry entry){
     
         JFrame frame = new JFrame(entry.getShow().getName()+"'s room");
         frame.setSize(1000, 1500);
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
         ArrayList<Row> rows = (ArrayList<Row>) entry.getRows();
       
//ολες οι σειρες εμφανιζουν το ιδιο πληθος θεσεων (μεγιστο των σειρων ) αλλα καποιες ειναι διαθεσιμες για κρατηση ("υπαρχουν") ενω αλλες οχι.
        GridLayout grid = new GridLayout(0,1,5,5);
        JPanel inner= new JPanel(grid);
        int maxSeats = entry.maximumSeats();
        grid.setColumns(maxSeats);
        for (Row row : rows) {
        JButton button;
            for (int k = 1; k <= maxSeats; k++) {
                  if(row.getSeat(k)!=null)
                  {
                      button = new JButton(row.getSeat(k).toString());
                      //πλεον ο αριθμος της θεσης δεν εξαρταται απο το k που ειναι μετρητης βρογχου κ αλλαζει σε καθε επαναληψη.
                      //δημιουργουμε την temp ωστε να αναγνωριζεται ως effectively final και να μπορει να γινει αναφορα σε αυτην 
                      //απο οποιαδηποτε inner class (στην περιπτωση μας την ανωνυμη εσωτερικη που δημιουργειται μεσω του actionListener
                      //του button)
                      Seat temp = row.getSeat(k);
                      if (temp instanceof LuxuriousSeat)
                          button.setBackground(Color.pink);
                      
                //εδω πρεπει να προστεθει ο actionListener για τις επιλογες του χρηστη
                    button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean succeed;
                int result;
                //μπορουμε να αναφερθουμε απλα ως button κ να χει σωστο αποτελεσμα , να το ελεγξω στο τελος.
                JButton button = (JButton)e.getSource();
                
                   if (user.getReservations().contains(temp))
                   {
                     result  = JOptionPane.showConfirmDialog(null, "Do you want to cancel your reservation;",
                    "Cancel reservation",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                      if (result==JOptionPane.YES_OPTION)
                      {  
                          user.cancelReservation(temp);
                          button.setBackground(Color.MAGENTA);                   
                      }   
                     return;
                       
                   }
                   //ελεγχει την περιπτωση η θεση να ειναι κρατημενη απο αλλον χρηστη 
                   else if (temp.isReserved()){
                       JOptionPane.showMessageDialog(null, "This seat is reserved", null, JOptionPane.ERROR_MESSAGE, null);
                       return;
                   }
                       
                 result  = JOptionPane.showConfirmDialog(null, "Do you want to reserve this seat;",
                    "Τίτλος",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                 
                 if (result == JOptionPane.YES_OPTION) {
                     
                     succeed = user.makeReservation(temp);
                     
                     if (succeed)
                     {   
         //η θεση της συγκεκριμενης σειρας στην συγκεκριμενη προβολη(Entry) πλεον θεωρειται καταχωρημενη στον χρηστη.
                    JOptionPane.showMessageDialog(null, "Η κράτηση καταχωρήθηκε", "Τίτλος",
                    JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Η θέση κοστίζει "+(temp.getPrice() + entry.getShow().getPrice())+" ευρώ.", "Τίτλος",
                    JOptionPane.INFORMATION_MESSAGE);
                         try {
                             Administrator.saveWorkplace();
                         } catch (IOException ex) {
                              JOptionPane.showMessageDialog(null, "Προέκυψε σφάλμα κατα την καταχώρηση της θέσης στο σύστημα", "Τίτλος",JOptionPane.ERROR_MESSAGE);
                         }
                     button.setBackground(Color.red);
                     }
                     else 
                         JOptionPane.showMessageDialog(null, "Προέκυψε κάποιο λάθος κατα την κράτηση της θέσης στο σύστημα", "Τίτλος",
                    JOptionPane.INFORMATION_MESSAGE);
 
            }
                  
                  
                  
            }
                    });
                            }
                  else {
                      button = new JButton();
                      button.addActionListener(new ActionListener(){               
                                   
                     public void  actionPerformed(ActionEvent e){
                     JOptionPane.showMessageDialog(null, "Η θέση δεν είναι διαθέσιμη", "Τίτλος",
                     JOptionPane.INFORMATION_MESSAGE);
                     
                     }                 
                              });
                       } 
                 inner.add(button);
                 }
    
        Icon icon;
        JLabel label = new JLabel();
        URL imageURL = getClass().getResource("images/cinema.jpg");;
                 if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                    label.setIcon(icon);
                          }
         frame.add(label , BorderLayout.NORTH);
         
         
         
         frame.add(inner,BorderLayout.CENTER);
         frame.pack();
         frame.setVisible(true);
    
    }
     
}
}