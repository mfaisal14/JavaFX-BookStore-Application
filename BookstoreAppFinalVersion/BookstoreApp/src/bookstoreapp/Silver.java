package bookstoreapp;


public class Silver extends State {

    @Override
    public void makeGold(ownerCustomers c) {
        c.setState(new Gold());
    }

    @Override
    public void makeSilver(ownerCustomers c) {
    }

    @Override
    public String toString() {
        return "Silver";
    }
}
    

