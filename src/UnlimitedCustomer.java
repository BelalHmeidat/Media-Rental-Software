public class UnlimitedCustomer extends Customer { //unlimited plan customer

    public UnlimitedCustomer(String iD, String name, String phone, String address, String plan) {
        super(iD, name, phone, address, plan);
    }

    public UnlimitedCustomer() {
        super();
    }

    @Override
    public String getDetails (){
        return "ID: " + this.ID + "\nName: " + this.name + "\nphone: " + this.phone + "\naddress: " + this.address + "\nplan: " + this.plan;
    }

    
}