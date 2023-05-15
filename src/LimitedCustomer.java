public class LimitedCustomer extends Customer { //limited plan customer

    public LimitedCustomer() {
    }

    public LimitedCustomer(String iD, String name, String phone, String address, String plan) {
        super(iD, name, phone, address, plan);
    }

    

    public static int LimitedValue = 2;

    @Override
    public String getDetails (){
        return "ID: " + this.ID + "\nName: " + this.name + "\nphone: " + this.phone + "\naddress: " + this.address + "\nplan: " + this.plan;
    }

    
}