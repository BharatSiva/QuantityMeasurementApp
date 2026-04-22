public enum Main {

    FEET(1.0),          // base unit
    INCH(1.0 / 12.0);  // 1 inch = 1/12 feet

    private final double toFeetFactor;

    Main (double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }
