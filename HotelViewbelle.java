import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Booking {
    String billId;
    String headName;
    int roomNo;
    String roomType;
    LocalDate arrival;
    LocalDate departure;
    long nights;
    double totalAmount;
    String paymentMode;
    String status;

    Booking(String billId) {
        this.billId = billId;
    }
}

public class HotelViewbelle {

    static final String BOOKING_FILE = "Viewbelle_Bookings.txt";
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) throws Exception {

        while (true) {
            System.out.println("\nHOTEL VIEWBELLE");
            System.out.println("1. New Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) createBooking();
            else if (choice == 2) cancelBooking();
            else break;
        }
    }

    static void createBooking() throws Exception {

        Booking booking = new Booking(generateBillId());

        System.out.print("Enter Head Guest Name: ");
        booking.headName = sc.nextLine();

        System.out.print("Enter Number of Persons: ");
        int persons = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i < persons; i++) {
            System.out.print("Enter Guest Name " + (i + 1) + ": ");
            sc.nextLine();
        }

        System.out.print("Enter Arrival Date (YYYY-MM-DD): ");
        booking.arrival = LocalDate.parse(sc.nextLine());

        System.out.print("Enter Departure Date (YYYY-MM-DD): ");
        booking.departure = LocalDate.parse(sc.nextLine());

        booking.nights = ChronoUnit.DAYS.between(booking.arrival, booking.departure);

        System.out.println("Select Room Type");
        System.out.println("1. NON AC");
        System.out.println("2. AC");
        System.out.println("3. LUXURY");
        System.out.print("Enter Choice: ");

        int roomChoice = sc.nextInt();
        sc.nextLine();

        double rate;
        if (roomChoice == 1) {
            booking.roomType = "NON AC";
            rate = 800;
        } else if (roomChoice == 2) {
            booking.roomType = "AC";
            rate = 1200;
        } else {
            booking.roomType = "LUXURY";
            rate = 2000;
        }

        booking.roomNo = random.nextInt(300) + 100;
        booking.totalAmount = rate * booking.nights;
        booking.status = "CONFIRMED";

        displayBill(booking);

        System.out.println("Payment Mode");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.print("Enter Choice: ");

        int payChoice = sc.nextInt();
        sc.nextLine();

        if (payChoice == 1) {
            booking.paymentMode = "CASH";
        } else {
            booking.paymentMode = "UPI";
            System.out.println("UPI ID: hotelviewbelle@upi");
            System.out.println("Payment Successful");
        }

        saveBookingToFile(booking);

        System.out.println("\nBooking Confirmed");
        System.out.println("Bill ID: " + booking.billId);
    }

    static void cancelBooking() throws Exception {

        System.out.print("Enter Bill ID: ");
        String billId = sc.nextLine();

        Booking booking = fetchBookingFromFile(billId);

        if (booking == null) {
            System.out.println("No booking found for the given Bill ID.");
            return;
        }

        System.out.print("Do you want to cancel your booking? (YES/NO): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("YES")) {
            System.out.println("Cancellation Aborted.");
            return;
        }

        removeBookingFromFile(billId);

        double refundAmount = booking.totalAmount * 0.85;

        System.out.println("\nCANCELLATION RECEIPT");
        System.out.println("--------------------------------------------------");
        System.out.println("Hotel Name     : HOTEL VIEWBELLE");
        System.out.println("Bill ID        : " + booking.billId);
        System.out.println("Guest Name     : " + booking.headName);
        System.out.println("Room Number    : " + booking.roomNo);
        System.out.println("Room Type      : " + booking.roomType);
        System.out.println("Stay Period    : " + booking.arrival + " to " + booking.departure);
        System.out.println("Amount Paid    : " + booking.totalAmount);
        System.out.println("Refund (85%)   : " + refundAmount);
        System.out.println("Booking Status : CANCELLED");
        System.out.println("--------------------------------------------------");
    }

    static void displayBill(Booking b) {

        System.out.println("\nHOTEL VIEWBELLE - BILL");
        System.out.println("--------------------------------------------------");
        System.out.println("Bill ID        : " + b.billId);
        System.out.println("Guest Name     : " + b.headName);
        System.out.println("Room Number    : " + b.roomNo);
        System.out.println("Room Type      : " + b.roomType);
        System.out.println("Arrival Date   : " + b.arrival);
        System.out.println("Departure Date : " + b.departure);
        System.out.println("No. of Nights  : " + b.nights);
        System.out.println("Total Amount   : " + b.totalAmount);
        System.out.println("--------------------------------------------------");
    }

    static void saveBookingToFile(Booking b) throws Exception {

        FileWriter fw = new FileWriter(BOOKING_FILE, true);
        fw.write(
                b.billId + "," +
                b.headName + "," +
                b.roomNo + "," +
                b.roomType + "," +
                b.arrival + "," +
                b.departure + "," +
                b.nights + "," +
                b.totalAmount + "," +
                b.paymentMode + "," +
                b.status + "\n"
        );
        fw.close();
    }

    static Booking fetchBookingFromFile(String billId) throws Exception {

        File file = new File(BOOKING_FILE);
        if (!file.exists()) return null;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.startsWith(billId + ",")) {
                br.close();
                return parseBooking(line);
            }
        }
        br.close();
        return null;
    }

    static void removeBookingFromFile(String billId) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE));
        List<String> remaining = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.startsWith(billId + ",")) {
                remaining.add(line);
            }
        }
        br.close();

        FileWriter fw = new FileWriter(BOOKING_FILE);
        for (String l : remaining) {
            fw.write(l + "\n");
        }
        fw.close();
    }

    static Booking parseBooking(String line) {

        String[] d = line.split(",");
        Booking b = new Booking(d[0]);
        b.headName = d[1];
        b.roomNo = Integer.parseInt(d[2]);
        b.roomType = d[3];
        b.arrival = LocalDate.parse(d[4]);
        b.departure = LocalDate.parse(d[5]);
        b.nights = Long.parseLong(d[6]);
        b.totalAmount = Double.parseDouble(d[7]);
        b.paymentMode = d[8];
        b.status = d[9];
        return b;
    }

    static String generateBillId() {
        return "VB" + (10000 + random.nextInt(90000));
    }
}
