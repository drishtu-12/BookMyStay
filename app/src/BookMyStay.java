public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("System initialized successfully.");
        System.out.println("Application: Book My Stay App v1.0");

    }
}
public class UseCase2HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Room Initialization\n");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Single Room Details
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println();

        // Double Room Details
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println();

        // Suite Room Details
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);

        // Create search service
        SearchService search = new SearchService(inventory);

// Perform room search
        search.displayAvailableRooms(single, "Single Room");
        search.displayAvailableRooms(doubleRoom, "Double Room");
        search.displayAvailableRooms(suite, "Suite Room");
    }
}

/**
 * Abstract class representing a generic Room
 */
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    // Constructor
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    // Display room details
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * Single Room class
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * Double Room class
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * Suite Room class
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}
//usecases
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room room, String roomType) {

        int available = inventory.getAvailability(roomType);

        // Show only available rooms
        if (available > 0) {
            System.out.println(roomType + ":");
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + available);
            System.out.println();
        }
    }
}
import java.util.LinkedList;
import java.util.Queue;
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
System.out.println("\nBooking Request Queue");

// Initialize queue
BookingRequestQueue bookingQueue = new BookingRequestQueue();

// Create booking requests
Reservation r1 = new Reservation("Abhi", "Single");
Reservation r2 = new Reservation("Subha", "Double");
Reservation r3 = new Reservation("Vanmathi", "Suite");

// Add requests
bookingQueue.addRequest(r1);
bookingQueue.addRequest(r2);
bookingQueue.addRequest(r3);

// Process requests in FIFO order
while (bookingQueue.hasPendingRequests()) {

Reservation request = bookingQueue.getNextRequest();

    System.out.println(
        request.getGuestName() +
        " requested " +
        request.getRoomType() +
        " Room"
        );
        }
