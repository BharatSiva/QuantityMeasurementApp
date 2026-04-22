public class QuantityMeasurementApp {

    // ===== ENUM FOR UNITS =====
    enum LengthUnit {

        FEET(1.0),                       // base unit

        INCH(1.0 / 12.0),                // 1 inch = 1/12 feet

        YARD(3.0),                       // 1 yard = 3 feet

        CENTIMETER(0.393701 / 12.0);     // cm → inch → feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ===== GENERIC CLASS =====
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) return true;

            // Null or type check
            if (obj == null || this.getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            // Compare after conversion
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {

        // UC1 → Feet
        System.out.println("Feet vs Feet: " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(1.0, LengthUnit.FEET)));

        // UC2 → Inches
        System.out.println("Inch vs Inch: " +
                new QuantityLength(1.0, LengthUnit.INCH)
                        .equals(new QuantityLength(1.0, LengthUnit.INCH)));

        // UC3 → Cross Unit
        System.out.println("Feet vs Inch: " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(12.0, LengthUnit.INCH)));

        // UC4 → Yard
        System.out.println("Yard vs Feet: " +
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        // UC4 → Yard vs Inch
        System.out.println("Yard vs Inch: " +
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(36.0, LengthUnit.INCH)));

        // UC4 → Centimeter
        System.out.println("CM vs Inch: " +
                new QuantityLength(1.0, LengthUnit.CENTIMETER)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH)));

        // Negative case
        System.out.println("Not Equal: " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(2.0, LengthUnit.FEET)));
    }
}
