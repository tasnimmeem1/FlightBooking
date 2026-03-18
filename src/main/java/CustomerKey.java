public class CustomerKey {
    private int customerId;

    public CustomerKey(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(customerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerKey)) return false;

        CustomerKey other = (CustomerKey) obj;
        return this.customerId == other.customerId;
    }

    @Override
    public String toString() {
        return "C" + customerId;
    }
}