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
class RoomAllocationService {

    // Stores all allocated room IDs
    private Set<String> allocatedRoomIds;

    // Stores assigned room IDs by room type
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store allocated ID
        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.setAvailability(roomType, available - 1);

        // Confirmation message
        System.out.println(
                "Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId
        );
    }

    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return roomType + "-" + count;
    }
}
System.out.println("\nRoom Allocation Processing");

RoomAllocationService allocationService = new RoomAllocationService();

// Recreate booking requests
Reservation r1 = new Reservation("Abhi", "Single");
Reservation r2 = new Reservation("Subha", "Single");
Reservation r3 = new Reservation("Vanmathi", "Suite");

// Allocate rooms
allocationService.allocateRoom(r1, inventory);
allocationService.allocateRoom(r2, inventory);
allocationService.allocateRoom(r3, inventory);
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}
import java.util.*;

class AddOnServiceManager {

    // reservationId -> list of services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services =
                reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (AddOnService s : services) {
            total += s.getPrice();
        }

        return total;
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services =
                reservationServices.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("Services for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " : " + s.getPrice());
        }

        System.out.println("Total Add-On Cost: " + calculateTotalServiceCost(reservationId));
    }
}
import java.util.*;

class BookingHistory {

    // Stores confirmed reservations in order
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getBookings() {
        return confirmedBookings;
    }
}
class BookingReportService {

    public void displayBookingReport(BookingHistory history) {

        System.out.println("\nBooking History Report");

        for (Reservation r : history.getBookings()) {

            System.out.println(
                    "Guest: " + r.getGuestName() +
                            ", Room Type: " + r.getRoomType()
            );
        }

        System.out.println("Total Bookings: " + history.getBookings().size());
    }
}
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}
class ReservationValidator {

    public void validateReservation(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type must be specified.");
        }

        // Validate inventory availability
        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No available rooms for type: " + roomType);
        }
    }
}
import java.util.*;

class CancellationService {

    // Stack to track released room IDs
    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancelReservation(
            String reservationId,
            String roomType,
            RoomInventory inventory,
            BookingHistory history
    ) {

        // Validate reservation exists
        boolean exists = false;

        for (Reservation r : history.getBookings()) {
            if (r.getRoomType().equals(roomType)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Cancellation failed: reservation not found.");
            return;
        }

        // Push released room ID to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        int available = inventory.getAvailability(roomType);
        inventory.setAvailability(roomType, available + 1);

        System.out.println("Reservation cancelled. Room released: " + reservationId);
    }
}
class PersistenceService {

    private static final String FILE_NAME = "inventory.dat";

    // Save inventory to file
    public void saveInventory(RoomInventory inventory) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(inventory.getAllAvailability());

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from file
    public Map<String, Integer> loadInventory() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (Map<String, Integer>) in.readObject();

        } catch (Exception e) {

            System.out.println("No valid inventory data found. Starting fresh.");
            return null;
        }
    }
}
public Map<String, Integer> getAllAvailability() {
    return availability;
}

