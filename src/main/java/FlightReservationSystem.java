import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class FlightReservationSystem {

    private MyHashMap<CustomerKey, Customer> customers = new MyHashMap<>();
    private MyHashMap<FlightKey, Flight> flights = new MyHashMap<>();
    private MyHashMap<BookingKey, Booking> bookings = new MyHashMap<>();

    private static int customerCounter = 100;
    private static int bookingCounter = 1000;
    private Random random = new Random();

    public FlightReservationSystem() {
        preloadFlights();
    }

    private void preloadFlights() {
        flights.put(new FlightKey("F101"), new Flight("F101", "Delta", "New York", "Dallas", 40));
        flights.put(new FlightKey("F102"), new Flight("F102", "United", "New York", "Miami", 40));
        flights.put(new FlightKey("F103"), new Flight("F103", "JetBlue", "New York", "Los Angeles", 40));
        flights.put(new FlightKey("F104"), new Flight("F104", "American Airlines", "New York", "Chicago", 40));
        flights.put(new FlightKey("F105"), new Flight("F105", "Southwest", "New York", "Atlanta", 40));
        flights.put(new FlightKey("F106"), new Flight("F106", "Delta", "New York", "Houston", 40));
    }

    public void addCustomer(String name, String email) {
        int customerId = generateCustomerId();
        CustomerKey key = new CustomerKey(customerId);
        Customer customer = new Customer(customerId, name, email);

        customers.put(key, customer);

        System.out.println("Customer added successfully.");
        System.out.println("Generated Customer ID: " + key);
    }

    private int generateCustomerId() {
        return customerCounter++;
    }

    private String generateBookingId() {
        return "B" + bookingCounter++;
    }

    public void showAvailableFlights() {
        System.out.println("\nAvailable Flights:");
        ArrayList<Flight> allFlights = flights.values();

        for (Flight flight : allFlights) {
            System.out.println(flight);
        }
    }

    public ArrayList<Flight> getFlightsByDestination(String destination) {
        destination = destination.toLowerCase().trim();

        ArrayList<Flight> result = new ArrayList<>();

        for (Flight flight : flights.values()) {
            if (flight.getDestination().toLowerCase().contains(destination)) {
                result.add(flight);
            }
        }

        return result;
    }

    public void searchFlightsByDestination(String destination) {
        ArrayList<Flight> matchedFlights = getFlightsByDestination(destination);

        if (matchedFlights.isEmpty()) {
            System.out.println("No flights found for this destination.");
            return;
        }

        for (Flight flight : matchedFlights) {
            System.out.println(flight);
        }
    }

    public Customer findCustomerByName(String name) {
        ArrayList<Customer> allCustomers = customers.values();

        for (Customer customer : allCustomers) {
            if (customer.getName().equalsIgnoreCase(name.trim())) {
                return customer;
            }
        }

        return null;
    }

    public void bookFlight(int customerId, String flightId, String flightDate, String seatClass, String preferredSeat) {
        Customer customer = customers.get(new CustomerKey(customerId));
        Flight flight = flights.get(new FlightKey(flightId));

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        String finalSeat;

        if (seatClass.equalsIgnoreCase("Economy")) {
            finalSeat = assignRandomSeat(flight.getFlightId(), flightDate, flight.getTotalSeats());
            if (finalSeat == null) {
                System.out.println("No seats available.");
                return;
            }
        } else if (seatClass.equalsIgnoreCase("Business") || seatClass.equalsIgnoreCase("Premium")) {
            if (preferredSeat == null || preferredSeat.isBlank()) {
                System.out.println("Seat selection is required for Business or Premium class.");
                return;
            }

            preferredSeat = preferredSeat.toUpperCase().trim();

            if (isSeatTaken(flight.getFlightId(), flightDate, preferredSeat)) {
                System.out.println("Selected seat is already booked.");
                return;
            }

            finalSeat = preferredSeat;
        } else {
            System.out.println("Invalid class. Choose Economy, Business, or Premium.");
            return;
        }

        String bookingId = generateBookingId();
        String bookingDate = LocalDate.now().toString();
        double price = calculatePrice(seatClass);

        Booking booking = new Booking(
                bookingId,
                customerId,
                flight.getFlightId(),
                flightDate,
                normalizeSeatClass(seatClass),
                finalSeat,
                price,
                bookingDate
        );

        bookings.put(new BookingKey(bookingId), booking);

        System.out.println("Booking created successfully.");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Customer ID: " + customer.getDisplayCustomerId());
        System.out.println("Flight: " + flight.getSource() + " -> " + flight.getDestination());
        System.out.println("Booking ID: " + bookingId);
        System.out.println(booking);
    }

    private String normalizeSeatClass(String seatClass) {
        if (seatClass.equalsIgnoreCase("Economy")) {
            return "Economy";
        } else if (seatClass.equalsIgnoreCase("Business")) {
            return "Business";
        } else if (seatClass.equalsIgnoreCase("Premium")) {
            return "Premium";
        }
        return seatClass;
    }

    private double calculatePrice(String seatClass) {
        if (seatClass.equalsIgnoreCase("Economy")) {
            return 200.0;
        } else if (seatClass.equalsIgnoreCase("Business")) {
            return 500.0;
        } else if (seatClass.equalsIgnoreCase("Premium")) {
            return 800.0;
        }
        return 0.0;
    }

    private boolean isSeatTaken(String flightId, String flightDate, String seatNumber) {
        ArrayList<Booking> allBookings = bookings.values();

        for (Booking booking : allBookings) {
            if (booking.getFlightId().equalsIgnoreCase(flightId)
                    && booking.getFlightDate().equalsIgnoreCase(flightDate)
                    && booking.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return true;
            }
        }

        return false;
    }

    private String assignRandomSeat(String flightId, String flightDate, int totalSeats) {
        int maxAttempts = 200;

        for (int i = 0; i < maxAttempts; i++) {
            String seat = generateRandomSeat(totalSeats);
            if (!isSeatTaken(flightId, flightDate, seat)) {
                return seat;
            }
        }

        return null;
    }

    private String generateRandomSeat(int totalSeats) {
        int row = random.nextInt(Math.max(1, totalSeats / 4)) + 1;
        char seatLetter = (char) ('A' + random.nextInt(4));
        return row + String.valueOf(seatLetter);
    }

    public void cancelBooking(String bookingId) {
        Booking removed = bookings.remove(new BookingKey(bookingId));

        if (removed == null) {
            System.out.println("Booking not found.");
        } else {
            System.out.println("Booking cancelled successfully.");
        }
    }

    public void searchCustomer(int customerId) {
        Customer customer = customers.get(new CustomerKey(customerId));

        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            System.out.println(customer);
        }
    }

    public void searchBookingById(String bookingId) {
        Booking booking = bookings.get(new BookingKey(bookingId));

        if (booking == null) {
            System.out.println("Booking not found.");
        } else {
            System.out.println(booking);
        }
    }

    public void showBookingsByCustomer(int customerId) {
        ArrayList<Booking> allBookings = bookings.values();
        boolean found = false;

        for (Booking booking : allBookings) {
            if (booking.getCustomerId() == customerId) {
                System.out.println(booking);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found for this customer.");
        }
    }

    public void showAllCustomers() {
        ArrayList<Customer> allCustomers = customers.values();

        if (allCustomers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : allCustomers) {
            System.out.println(customer);
        }
    }

    public void showAllBookings() {
        ArrayList<Booking> allBookings = bookings.values();

        if (allBookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : allBookings) {
            System.out.println(booking);
        }
    }
}