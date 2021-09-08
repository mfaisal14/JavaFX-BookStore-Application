package bookstoreapp;

public class Gold extends State {

    @Override
    public void makeGold(ownerCustomers c) {
    }

    @Override
    public void makeSilver(ownerCustomers c) {
        c.setState(new Silver());
    }

    @Override
    public String toString() {
        return "Gold";
    }
}

