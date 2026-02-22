package observer;

import model.Patron;

public class PatronNotifier implements Observer {

    private final Patron patron;

    public PatronNotifier(Patron patron) {
        this.patron = patron;
    }

    @Override
    public void update(String message) {
        System.out.println("📢 Notify " + patron.getName() + ": " + message);
    }
}