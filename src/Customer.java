import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    String ID;
    String name;
    String phone;
    String address;
    String plan; //1 Unlimited, 0 Limited


    public Customer(String ID, String name, String phone, String address, String plan) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.plan = plan;
    }



    public Customer() {
    }

    
    
    ArrayList<Media> cart = new ArrayList<Media>();
    ArrayList<Media> owned = new ArrayList<Media>();
    
    
    
    public String getInfo() {
        return this.ID + "; " + this.name + "; " + this.phone + "; " + this.address + "; "  + this.plan;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        
        return other.ID.equals(ID) && other.name.equals(name) && other.phone.equals(phone) && other.address.equals(address) && other.plan.equals(plan);
    }
    @Override
    public int compareTo(Customer other) { //used for sorting customers by name when proccing requests
        return this.name.compareTo(other.name);
    }

    public String getDetails (){
        String cart = "";
        for (Media m : this.cart) {
            cart += m.title + "\n";
        }
        String owned = "";
        for (Media m : this.owned) {
            owned += m.title + "\n";
        }
        return "ID: " + this.ID + "\nName: " + this.name + "\nphone: " + this.phone + "\naddress: " + this.address + "\nCart: " + cart + "\nOwned: " + owned + "\nplan: " + this.plan;
    }
    
}