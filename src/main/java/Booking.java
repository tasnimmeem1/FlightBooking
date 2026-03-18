public class Booking {
    private String bookingId;
    private int customerId;
    private String flightId;
    private String flightDate;
    private String seatClass;
    private String seatNumber;
    private double price;
    private String bookingDate;

    public Booking(String bookingId, int customerId, String flightId, String flightDate,
                   String seatClass, String seatNumber, double price, String bookingDate) {
        this.bookingId = bookingId.toUpperCase().trim();
        this.customerId = customerId;
        this.flightId = flightId.toUpperCase().trim();
        this.flightDate = flightDate;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber.toUpperCase().trim();
        this.price = price;
        this.bookingDate = bookingDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Customer ID: C" + customerId +
                ", Flight ID: " + flightId +
                ", Flight Date: " + flightDate +
                ", Class: " + seatClass +
                ", Seat: " + seatNumber +
                ", Price: $" + price +
                ", Booking Date: " + bookingDate;
    }
}