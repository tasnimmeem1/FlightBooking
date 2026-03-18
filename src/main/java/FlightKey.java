public class FlightKey {
    private String flightId;

    public FlightKey(String flightId) {
        this.flightId = flightId.toUpperCase().trim();
    }

    public String getFlightId() {
        return flightId;
    }

    @Override
    public int hashCode() {
        return flightId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FlightKey)) return false;

        FlightKey other = (FlightKey) obj;
        return this.flightId.equalsIgnoreCase(other.flightId);
    }

    @Override
    public String toString() {
        return flightId;
    }
}