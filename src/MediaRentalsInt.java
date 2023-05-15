import java.util.ArrayList;

public interface MediaRentalsInt {
    
    public abstract void addCustomer(String name, String phone, String address, String plan);
    public abstract void addGame(String title, int copies, double grams);
    public abstract void addAlbum (String title, int copies, String artist, String songs);
    public abstract void addMovie(String title, int copies, String rating);
    public abstract void setLimitedPlanLimit(int value);
    public abstract String getAllCustomersInfo();
    public abstract String getAllMediaInfo();
    public abstract boolean addToCart(String ID,String code);
    public abstract boolean removeFromCart(String ID, String code);
    public abstract String processRequests();
    public abstract boolean returnMedia(String ID,String code);
    public abstract ArrayList<String> searchMedia(String code, String title,String rating, String artist,String songs);
    public abstract Customer searchCustomer(String ID);
    public Media checkIfMediaExists(String needsChecking);
    public abstract Boolean updateCustomer(String ID, String name, String phone, String address, String plan);
    public abstract Boolean updateAlbum(String code, String title, int copies, String artist, String songs);
    public abstract Boolean updateGame(String code, String title, int copies, double grams);
    public abstract Boolean updateMovie(String code, String title, int copies, String rating);
    public abstract Boolean removeCustomer(String ID);
    public abstract Boolean removeMedia(String code);
}