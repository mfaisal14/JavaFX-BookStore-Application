package bookstoreapp;

public abstract class State {
    public abstract void makeGold(ownerCustomers c);

    public abstract void makeSilver(ownerCustomers c);

    public abstract String toString();
}
