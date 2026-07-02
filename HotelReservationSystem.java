import java.util.ArrayList;
import java.util.Scanner;

class Room {

    private final int roomNumber;
    private final String category;
    private final double price;
    private boolean booked;
    private String customerName;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.booked = false;
        this.customerName = "";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void bookRoom(String customerName) {
        this.booked = true;
        this.customerName = customerName;
    }

    public void cancelBooking() {
        this.booked = false;
        this.customerName = "";
    }

    public void displayRoom() {
        System.out.println("------------------------------------");
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Category    : " + category);
        System.out.println("Price       : ₹" + price);
        System.out.println("Status      : " + (booked ? "Booked" : "Available"));

        if (booked) {
            System.out.println("Customer    : " + customerName);
        }
    }
}

class Booking {

    private final String customerName;
    private final String mobileNumber;
    private final Room room;
    private boolean paymentDone;

    public Booking(String customerName, String mobileNumber, Room room) {
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.room = room;
        this.paymentDone = false;
    }

    public Room getRoom() {
        return room;
    }

    public void makePayment() {
        paymentDone = true;
        System.out.println("\nPayment Successful!");
        System.out.println("Amount Paid: ₹" + room.getPrice());
    }

    public void displayBooking() {
        System.out.println("\n============== Booking Details ===============");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Mobile Number : " + mobileNumber);
        System.out.println("Room Number   : " + room.getRoomNumber());
        System.out.println("Category      : " + room.getCategory());
        System.out.println("Room Price    : ₹" + room.getPrice());
        System.out.println("Payment Status: " + (paymentDone ? "Paid" : "Pending"));
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void initializeRooms() {

        rooms.add(new Room(401, "Standard", 2500));
        rooms.add(new Room(402, "Standard", 2500));
        rooms.add(new Room(403, "Standard", 2500));

        rooms.add(new Room(601, "Deluxe", 4500));
        rooms.add(new Room(602, "Deluxe", 4500));
        rooms.add(new Room(603, "Deluxe", 4500));

        rooms.add(new Room(701, "Suite", 7000));
        rooms.add(new Room(702, "Suite", 7000));
    }

    public static void viewRooms() {

        System.out.println("\n============== AVAILABLE ROOMS ================");

        for (Room room : rooms) {
            room.displayRoom();
        }
    }

    public static Room searchRoom(int roomNumber) {

        for (Room room : rooms) {
            if (room.getRoomNumber()==roomNumber) {
                return room;
            }
        }

        return null;
    }

    public static void bookRoom() {

        System.out.print("\nEnter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Room room = searchRoom(roomNo);

        if (room == null) {
            System.out.println("Room Not Found!");
            return;
        }

        if (room.isBooked()) {
            System.out.println("Room Already Booked!");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Mobile Number: ");
        String mobile = sc.nextLine();

        room.bookRoom(name);

        Booking booking = new Booking(name, mobile, room);
        bookings.add(booking);

        System.out.println("\nRoom Booked Successfully!");
        System.out.println("Room Price : ₹" + room.getPrice());

        System.out.print("Pay Now? (yes/no): ");
        String pay = sc.nextLine();

        if (pay.equalsIgnoreCase("yes")) {
            booking.makePayment();
        } else {
            System.out.println("Payment Pending.");
        }
    }

    public static void cancelBooking() {

        System.out.print("\nEnter Room Number to Cancel: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Room room = searchRoom(roomNo);

        if (room == null) {
            System.out.println("Room Not Found!");
            return;
        }

        if (!room.isBooked()) {
            System.out.println("Room is not booked.");
            return;
        }

        room.cancelBooking();

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getRoom().getRoomNumber() == roomNo) {
                bookings.remove(i);
                break;
            }
        }

        System.out.println("Booking Cancelled Successfully!");
    }

    public static void viewBookings() {

        if (bookings.isEmpty()) {
            System.out.println("\nNo Booking Found.");
            return;
        }

        for (Booking booking : bookings) {
            booking.displayBooking();
        }
    }

    public static void searchByCategory() {

        System.out.print("\nEnter Category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        boolean found = false;

        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category)) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Rooms Found.");
        }
    }

    public static void main(String[] args) {

        initializeRooms();

        while (true) {

            System.out.println("\n==================================");
            System.out.println(" HOTEL RESERVATION SYSTEM ");
            System.out.println("==================================");
            System.out.println("1. View All Rooms");
            System.out.println("2. Search Rooms by Category");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. View Booking Details");
            System.out.println("6. Exit");
            System.out.print("Enter Your Choice: ");

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    viewRooms();
                    break;

                case 2:
                    searchByCategory();
                    break;

                case 3:
                    bookRoom();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    viewBookings();
                    break;

                case 6:
                    System.out.println("\nThank You!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}