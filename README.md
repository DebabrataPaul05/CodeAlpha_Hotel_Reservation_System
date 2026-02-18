# CodeAlpha_Hotel_Reservation_System
Hotel Reservation System – Java Console Booking System

Hotel Reservation System (for an hotel named Hotel Viewbelle) is a Java-based console application designed to simulate a real-world hotel booking and cancellation system. The project demonstrates strong understanding of object-oriented programming, file handling, date and time APIs, and structured user interaction through the command line. It manages room reservations, billing, payment processing, and booking records using persistent file storage.

The application begins with a menu-driven interface that continuously runs inside a loop until the user chooses to exit. The user can create a new booking, cancel an existing booking, or terminate the program. This structure ensures smooth navigation and controlled execution flow.

When creating a new booking, the system collects the head guest’s name and the number of persons staying. It allows entry of additional guest names for proper record keeping. The user is then prompted to enter arrival and departure dates in YYYY-MM-DD format. Using Java’s LocalDate and ChronoUnit classes, the program calculates the total number of nights automatically, ensuring accurate stay duration without manual computation.

The system provides three room categories: Non-AC, AC, and Luxury. Each room type has a predefined nightly rate. Based on the user’s selection, the program calculates the total amount by multiplying the number of nights with the selected room rate. A room number is automatically generated using Java’s Random class, ensuring room allocation within a specified range.

Each booking is assigned a unique bill ID generated using a random five-digit number prefixed with “VB”. The program then displays a formatted bill that includes bill ID, guest details, room information, stay period, number of nights, and total amount. After billing, the user selects a payment mode, either Cash or UPI. If UPI is selected, the system displays a payment ID and confirms successful payment.

All booking details are stored in a text file named Viewbelle_Bookings.txt. The data is saved in comma-separated format, allowing persistent storage even after the program closes. The file handling mechanism ensures bookings can be retrieved later.

The cancellation feature allows users to enter their bill ID to fetch booking details from the file. If the booking exists and the user confirms cancellation, the record is removed from the file. The system calculates an 85 percent refund of the total paid amount and generates a cancellation receipt showing guest details, stay period, amount paid, and refund amount.

The project uses structured methods such as createBooking, cancelBooking, saveBookingToFile, fetchBookingFromFile, removeBookingFromFile, and parseBooking to maintain clean modular design. This separation of logic improves readability, maintainability, and scalability.

Overall, Hotel Viewbelle is a practical implementation of a hotel reservation system that demonstrates file management, billing logic, booking validation, and cancellation handling within a structured Java program.
