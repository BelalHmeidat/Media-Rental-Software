import java.util.ArrayList;
import java.util.Collections;

public class MediaRentalManager implements MediaRentalsInt{

    public MediaRentalManager() {
    }


    //Database
    ArrayList <Customer> customers = new ArrayList<Customer>();
    ArrayList <Media> mediaList = new ArrayList<Media>();

    @Override
    public Customer searchCustomer(String ID) {
        for (Customer exists : customers) {
            if (exists.ID.trim().equals(ID.trim())) {
                return exists;
            }
        }
        return null;
    }
    // private boolean checkIfCustomerObjExists(Object needsChecking) {
    //     for (Customer exists : customers) {
    //         if (exists.equals(needsChecking)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public Media checkIfMediaExists(String needsChecking) {
        for (Media exists : mediaList) {
            if (exists.code.trim().equalsIgnoreCase(needsChecking.trim())) {
                return exists;
            }
        }
        return null;
    }
    // private boolean checkIfGameObjExists(Object needsChecking) {
    //     for (Media exists : mediaList) {
    //         if (exists.equals(needsChecking)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    // private boolean checkIfMovieObjExists(Object needsChecking) {
    //     for (Media exist : mediaList) {
    //         if (exist.equals(needsChecking)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    // private boolean checkIfAlbumObjExists(Media needsChecking) {
    //     for (Media exist : mediaList) {
    //         if (exist.equals(needsChecking)) {
    //             return true; 
    //         }
    //     }
    //     return false;
    // }
   
    public void addCustomer(String name, String phone, String address, String plan) {
        String ID = generateRandomeID();
        // if (checkIfCustomerObjExists(new Customer(ID, name, phone, address, plan))) {
        //     System.out.println("Customer already exists");
        //     return;
        // } 
        name = name.replaceAll("[\\W&&\\S]", "");
        address = address.replaceAll("[\\W&&\\S]", "");
        if (plan.trim().equalsIgnoreCase("UNLIMITED")) {
            customers.add(new UnlimitedCustomer(ID, name.trim(), phone, address.trim(), plan.trim()));
        } 
        else {
            customers.add(new LimitedCustomer(ID, name.trim(), phone, address.trim(), plan.trim()));
        }
    }
        
    public void addGame(String title, int copies, double grams) {
        String code = generateRandomString('G');
        title = title.trim().replaceAll("[\\W&&\\S]", "");
        // if (checkIfGameObjExists(new Game(code.trim(), title, copies, grams))) {
        //     System.out.println("Game already exists");
        //     return;
        // }
        mediaList.add(new Game(code.trim(), title, copies, grams));
    
    }
    
    public void addMovie(String title, int copies, String rating) {
        String code = generateRandomString('M');
        // String code = "Belalaa";
        title = title.replaceAll("[\\W&&\\S]", "");
        // if (checkIfMovieObjExists(new Movie(code.trim(), title.trim(), copies, rating.trim()))) {
        //     System.out.println("Movie already exists");
        //     return;
        // } 
        mediaList.add(new Movie(code.trim(), title.trim(), copies, rating.trim()));
        
    }
    public void addAlbum (String title, int copies, String artist, String songs) {
        String code = generateRandomString('A');
        title = title.replaceAll("[\\W&&\\S]", "");
        artist = artist.replaceAll("[\\W&&\\S]", "");
        String [] songList = songs.split("\n");
        songs = "";
        for (String song : songList) {
            songs += song.trim() + ",";
        }
        // if (checkIfAlbumObjExists(new Album(code.trim(), title, copies, artist, songs))) {
        //     System.out.println("Album already exists");
        //     return;
        // }
        mediaList.add(new Album(code.trim(), title.trim(), copies, artist.trim(), songs.trim()));
    }

    public void setLimitedPlanLimit(int value) {
        LimitedCustomer.LimitedValue = value;
    }
    public String getAllCustomersInfo() {
        ArrayList<String> info = new ArrayList<>();
        for (Customer customer : customers) {
            info.add(customer.getInfo() + "\n");
        }
        Collections.sort(info);
        String allInfo = "";
        for (String s : info) {
            allInfo+= s.trim() + "\n";
        }
        return allInfo.replace(';', ' '); //friendly output
    }
    public String getAllMediaInfo() {
        Collections.sort(mediaList);
        ArrayList<String> info = new ArrayList<>();
        for (Media media : mediaList) {
            info.add(media.getDetails() + "\n");
        }
        String allInfo = "";
        for (String s : info) {
            allInfo+= s.trim().replaceAll(";", ", ") + "\n"; //friendly output
        }
        return allInfo; 
    }
    public boolean addToCart(String ID ,String code) {
        Customer customer = searchCustomer(ID);
        Media media = checkIfMediaExists(code);

        // if (customer == null) {
        //     // System.out.println("Customer does not exist");
        //     return false;
        // }
        // if (media == null) {
        //     // System.out.println("Media does not exist");
        //     return false;
        // }        
        for (Media m : customer.cart) {
            if (m.code.trim().equalsIgnoreCase(code.trim())) {
                // System.out.println("Media already in cart!");
                return false;
            }
        }
        for (Media m : customer.owned) {
            if (m.code.trim().equalsIgnoreCase(code.trim())) {
                // System.out.println("Media already owned!");
                return false;
            }
        }
        customer.cart.add(media);
        // System.out.println(media.title + " added to " + customer.name + "'s cart");
        return true;
                    
                
            
        
    }
    public boolean removeFromCart(String ID, String code) {
        Customer customer = searchCustomer(ID.trim());
        Media media = checkIfMediaExists(code.toUpperCase().trim());

        // if (customer == null) {
        //     System.out.println("Customer does not exist");
        //     return false;
        // }
        System.out.println(customer.cart.toString());
        if (customer.cart == null || customer.cart.size() == 0) {
            return false;
        }

        // if (media== null) {
        //     // System.out.println("Media does not exist");
        //     return false;
        // }

        customer.cart.remove(media);
        // System.out.println(media.title + " removed from " + customer.name + "'s cart!");
        return true;
    }
    public String processRequests() {
        String info = "";
        Collections.sort(customers);
        for (Customer customer : customers) {
            if (customer.plan.trim().equals("UNLIMITED")) {
                for (int i = 0; i < customer.cart.size(); i++) {//this type of loop needed to be able to remove items from cart
                    if (customer.cart.get(i).copies > 0) { 
                        customer.owned.add(customer.cart.get(i)); 
                        // System.out.println("\nSending " + customer.cart.get(i).title + " to " + customer.name); //prints a log message
                        info+= "Sending " + customer.cart.get(i).title + " to " + customer.name + "\n"; //full log is stored here
                        customer.cart.get(i).copies--; 
                        customer.cart.remove(customer.cart.get(i)); 
                        i--; //decrements the loop counter to make sure the loop doesn't skip an item
                    }
                    else {
                        info += "No copies of " + customer.cart.get(i).title + " left";
                        // System.out.println("No copies of " + customer.cart.get(i).title + " left"); //prints a log message if there are no copies left
                    }
                }
            }
            else { //if customer's plan is LIMITED
                for (int i = 0; i < customer.cart.size(); i++) { //this type of loop needed to be able to remove items from cart
                    if (customer.cart.get(i).copies > 0) { 
                        if (customer.owned.size() < LimitedCustomer.LimitedValue) { //if the customer's owned list is less than the limit
                            customer.owned.add(customer.cart.get(i)); 
                            // System.out.println("\nSending " + customer.cart.get(i).title + " to " + customer.name); //prints a log message
                            info+= "Sending " + customer.cart.get(i).title + " to " + customer.name + "\n"; //full log is stored here
                            customer.cart.get(i).copies--; 
                            customer.cart.remove(customer.cart.get(i)); 
                            i--; //decrements the loop counter to make sure the loop doesn't skip an item
                        }
                        else {
                            info += "Customer " + customer.name + " has reached the limit of " + LimitedCustomer.LimitedValue;
                            // System.out.println("Customer " + customer.name + " has reached the limit of " + LimitedCustomer.LimitedValue); //prints a log message if limit is reached
                            break; //breaks the loop if limit is reached
                        }
                    }
                    else {
                        info += "No copies of " + customer.cart.get(i).title + " left";
                        // System.out.println("No copies of " + customer.cart.get(i).title + " left"); //prints a log message if there are no copies left
                        break; //breaks the loop if there are no copies left
                    }
                }
            } 
        }
        return info; //returns the full log of all rented media
    }
    
    public boolean returnMedia(String ID, String code) {
        Customer customer = searchCustomer(ID);
        Media media = checkIfMediaExists(code);
        // if (customer == null) {
        //     System.out.println("Customer does not exist!");
        //     return false;
        // }
        if (customer.owned == null || customer.owned.size() == 0) {
            return false;
        }
        // if (media == null) {
        //     System.out.println("Media does not exist!");
        //     return false;
        // }
        customer.owned.remove(media); 
        // System.out.println("\nReturning " + media.title + " from " + customer.name); //prints a log message
        media.copies++; 
        
        return true;
    }
    public ArrayList<String> searchMedia(String code, String title, String rating, String artist, String songs) {
        /* Results include any media that has any of the entered fields
        Media doesn't have to match all fields
        */
        Collections.sort(mediaList);
        ArrayList<String> info = new ArrayList<String>();
        String [] songList = new String[1];
        if (songs != null){
            songList = songs.split("\n");

        }
        // if (code.isBlank()) {
        //     code = null;
        // }
        // if (title.isBlank()) {
        //     title = null;
        // }
        // if (rating.isBlank()) {
        //     rating = null;
        // }
        // if (artist.isBlank()) {
        //     artist = null;
        // }
        // if (songs.isBlank()) {
        //     songs = null;
        // }
        
        if (code == null && title == null && rating == null && artist == null && songs == null) {
            for(Media media : mediaList) {
                info.add(media.getDetails().trim().replaceAll(";", ", "));
            }
        }
        
        else {
            for (Media media : mediaList) { 
                if (code != null && media.code.trim().equalsIgnoreCase(code.trim())) {
                    info.add(media.getDetails().trim().replaceAll(";", ", "));
                }
                if (title != null && media.title.trim().equalsIgnoreCase(title.trim())) {
                    info.add(media.getDetails().trim().replaceAll(";", ", "));
                }
                if (rating != null && media instanceof Movie) {
                    if ( ((Movie)media).rating.trim().equalsIgnoreCase(rating.trim())) {
                        info.add(media.getDetails().trim().replaceAll(";", ", "));
                    }
                }
                if (artist != null && media instanceof Album) {
                    if ((((Album) media).artist.trim().equalsIgnoreCase(artist.trim()))) {
                        info.add(media.getDetails().trim().replaceAll("[;,]", " "));
                    }
                }
                if (songs != null && media instanceof Album) {
                    if (((Album)media).songs.trim().equalsIgnoreCase(songs.trim()) || hasSong(songList, ((Album)media).songList)) {
                        info.add(media.getDetails().trim().replaceAll(";", ", "));
                    }
                }
                
        
            }
        }
        return info;
    }

    private static boolean hasSong (String [] listOfSongsToSearchFor, String [] songsInDatabse) {
        /*A method to check if an album has a song or group of songs*/
        for (int i = 0; i < listOfSongsToSearchFor.length; i++) {
            for (int j = 0; j < songsInDatabse.length; j++) {
                if (songsInDatabse[j].trim().equalsIgnoreCase(listOfSongsToSearchFor[i].trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    //Method that generates a new alphanumeric (upper case) string of a given 7 length
    public String generateRandomString(char mediaIndicator) {
        int digits = 0000;
        for (;;){
            String newCode = mediaIndicator + (String.format("%04d" , digits));
            if (checkCode(newCode)) {
                return newCode;
            }
            else {
                digits++;
            }
        }
    }
    //Generates a new random 7 digits number
    private String generateRandomeID() {
        int ID = 0000000;
        for (;;) {
            String newID = (String.format("%07d" , ID));
            if (checkID(newID)) {
                return newID;
            }
            else{
                ID++;
            }
        }
    }
    private boolean checkID (String ID){
        for (Customer customer : customers) {
            if (customer.ID.trim().equals(ID.trim())) {
                return false;
            }
        }
        return true;
    }
    private boolean checkCode (String code) {
        for (Media m : mediaList){
            if (m.code.trim().equals(code.trim())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Boolean updateCustomer(String ID, String name, String phone, String address, String plan) {
        Customer customer = searchCustomer(ID);
        if (customer == null) {
            return false;
        }
        customer.name = name.trim();
        customer.phone = phone;
        customer.address = address.trim();
        customer.plan = plan;     
        return true;
    }

    @Override
    public Boolean updateAlbum (String code, String title, int copies, String artist, String songs) {
        Media media = checkIfMediaExists(code);
        if (media == null) {
            return false;
        }
        mediaList.remove(media);
        Album newAlbum = new Album(code, title, copies, artist, songs);
        mediaList.add(newAlbum);
        for (Customer customer : customers){
            if (customer.owned.contains(media)) {
                customer.owned.remove(media);
                customer.owned.add(newAlbum);
            }
            else if (customer.cart.contains(media)) {
                customer.cart.remove(media);
                customer.cart.add(newAlbum);
            }
        }
             
        return true;
    }

    @Override
    public Boolean updateMovie (String code, String title, int copies, String rating) {
        Media media = checkIfMediaExists(code);
        if (media == null) {
            return false;
        }
        mediaList.remove(media);
        Movie newMovie = new Movie(code, title, copies, rating);
        mediaList.add(newMovie);
        for (Customer customer : customers){
            if (customer.owned.contains(media)) {
                customer.owned.remove(media);
                customer.owned.add(newMovie);
            }
            else if (customer.cart.contains(media)) {
                customer.cart.remove(media);
                customer.cart.add(newMovie);
            }
        }
        
        return true;
    }

    @Override
    public Boolean updateGame (String code, String title, int copies, double grams) {
        Media media = checkIfMediaExists(code);
        if (media == null) {
            return false;
        }
        mediaList.remove(media);
        Game newGame = new Game(code, title, copies, grams);
        mediaList.add(newGame);
        for (Customer customer : customers){
            if (customer.owned.contains(media)) {
                customer.owned.remove(media);
                customer.owned.add(newGame);
            }
            else if (customer.cart.contains(media)) {
                customer.cart.remove(media);
                customer.cart.add(newGame);
            }
        }
        return true;
    }

    @Override
    public Boolean removeCustomer (String ID){
        Customer customer = searchCustomer(ID);
        if (customer == null){
            return false;
        }
        customers.remove(customer);
        return true;
    }

    @Override
    public Boolean removeMedia(String code) {
        Media media = checkIfMediaExists(code);
        if (media == null) {
            return false;
        }
        mediaList.remove(media);
        for (Customer customer : customers) {
            try{
                customer.owned.remove(media);
            }
            catch (NullPointerException e) {
                // System.out.println("No media to remove");
            }
            try{
                customer.cart.remove(media);

            }
            catch (NullPointerException e) {
                // System.out.println("No media to remove");
            }
        }
        return true;
    }
    
}
