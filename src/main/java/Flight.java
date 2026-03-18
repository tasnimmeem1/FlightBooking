public class Flight {
    private String flightId;
    private String airline;
    private String source;
    private String destination;
    private int totalSeats;

    public Flight(String flightId, String airline, String source, String destination, int totalSeats) {
        this.flightId = flightId.toUpperCase().trim();
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getAirline() {
        return airline;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId +
                ", Airline: " + airline +
                ", Route: " + source + " -> " + destination +
                ", Total Seats: " + totalSeats;
    }
}