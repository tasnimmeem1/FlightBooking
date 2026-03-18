import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int parseCustomerId(String input) {
        input = input.trim().toUpperCase();

        if (input.startsWith("C")) {
            input = input.substring(1);
        }

        return Integer.parseInt(input);
    }

    private static String parseBookingId(String input) {
        input = input.trim().toUpperCase();

        if (!input.startsWith("B")) {
            input = "B" + input;
        }

        return input;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightReservationSystem system = new FlightReservationSystem();

        int choice;

        do {
            System.out.println("\n=== Flight Reservation Management System ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Book Flight");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Search Customer by ID");
            System.out.println("5. Search Flights by Destination");
            System.out.println("6. Search Booking by Booking ID");
            System.out.println("7. Show All Customers");
            System.out.println("8. Show Available Flights");
            System.out.println("9. Show All Bookings");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Customer Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter Customer Email: ");
                        String email = scanner.nextLine();

                        system.addCustomer(name, email);
                        break;

                    case 2:
                        System.out.print("Enter Destination (e.g., Dallas, Miami): ");
                        String destination = scanner.nextLine();

                        ArrayList<Flight> matchedFlights = system.getFlightsByDestination(destination);

                        if (matchedFlights.isEmpty()) {
                            System.out.println("No flights found for this destination.");
                            break;
                        }

                        System.out.println("\nAvailable Flights:");
                        for (Flight flight : matchedFlights) {
                            System.out.println(flight);
                        }

                        System.out.print("Enter Flight ID (e.g., F101): ");
                        String inputFlightId = scanner.nextLine().trim().toUpperCase();

                        Flight selectedFlight = null;
                        for (Flight flight : matchedFlights) {
                            if (flight.getFlightId().equalsIgnoreCase(inputFlightId)) {
                                selectedFlight = flight;
                                break;
                            }
                        }

                        if (selectedFlight == null) {
                            System.out.println("Invalid Flight ID.");
                            break;
                        }

                        System.out.print("Enter Customer Name: ");
                        String customerName = scanner.nextLine();

                        Customer customer = system.findCustomerByName(customerName);

                        if (customer == null) {
                            System.out.println("Customer not found. Creating new customer...");
                            System.out.print("Enter Customer Email: ");
                            String newEmail = scanner.nextLine();

                            system.addCustomer(customerName, newEmail);
                            customer = system.findCustomerByName(customerName);
                        }

                        System.out.print("Enter Flight Date (YYYY-MM-DD): ");
                        String flightDate = scanner.nextLine().trim();

                        System.out.print("Enter Class (Economy/Business/Premium): ");
                        String seatClass = scanner.nextLine().trim();

                        String preferredSeat = null;
                        if (seatClass.equalsIgnoreCase("Business") || seatClass.equalsIgnoreCase("Premium")) {
                            System.out.print("Enter Preferred Seat: ");
                            preferredSeat = scanner.nextLine();
                        }

                        system.bookFlight(customer.getCustomerId(), selectedFlight.getFlightId(), flightDate, seatClass, preferredSeat);
                        break;

                    case 3:
                        System.out.print("Enter Booking ID (e.g., B1000 or 1000): ");
                        String cancelBookingId = parseBookingId(scanner.nextLine());
                        system.cancelBooking(cancelBookingId);
                        break;

                    case 4:
                        System.out.print("Enter Customer ID (e.g., 100 or C100): ");
                        int searchCustomerId = parseCustomerId(scanner.nextLine());
                        system.searchCustomer(searchCustomerId);
                        break;

                    case 5:
                        System.out.print("Enter Destination (e.g., Dallas, Miami): ");
                        String searchDestination = scanner.nextLine();
                        system.searchFlightsByDestination(searchDestination);
                        break;

                    case 6:
                        System.out.print("Enter Booking ID (e.g., B1000 or 1000): ");
                        String searchBookingId = parseBookingId(scanner.nextLine());
                        system.searchBookingById(searchBookingId);
                        break;

                    case 7:
                        system.showAllCustomers();
                        break;

                    case 8:
                        system.showAvailableFlights();
                        break;

                    case 9:
                        system.showAllBookings();
                        break;

                    case 0:
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                choice = -1;
            }

        } while (choice != 0);

        scanner.close();
    }
}