package observer;

import java.util.*;

public class ReservationManager {

    private final Map<String, Queue<Observer>> reservationMap = new HashMap<>();

    public void reserve(String isbn, Observer observer) {
        reservationMap
                .computeIfAbsent(isbn, k -> new LinkedList<>())
                .add(observer);
    }

    public void notifyNext(String isbn) {
        Queue<Observer> queue = reservationMap.get(isbn);
        if (queue != null && !queue.isEmpty()) {
            Observer observer = queue.poll();
            observer.update("Reserved book is now available: " + isbn);
        }
    }
}
