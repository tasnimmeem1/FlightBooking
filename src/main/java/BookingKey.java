public class BookingKey {
    private String bookingId;

    public BookingKey(String bookingId) {
        this.bookingId = bookingId.toUpperCase().trim();
    }

    public String getBookingId() {
        return bookingId;
    }

    @Override
    public int hashCode() {
        return bookingId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BookingKey)) return false;

        BookingKey other = (BookingKey) obj;
        return this.bookingId.equalsIgnoreCase(other.bookingId);
    }

    @Override
    public String toString() {
        return bookingId;
    }
}