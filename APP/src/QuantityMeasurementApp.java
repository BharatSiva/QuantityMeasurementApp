public class QuantityMeasurementApp {

    // ===== ENUM =====
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // ===== CLASS =====
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }

            this.value = value;
            this.unit = unit;
        }

        // Convert to base (feet)
        private double toBase() {
            return value * unit.getFactor();
        }

        // ===== EQUALITY =====
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        // ===== CONVERSION =====
        public double convertTo(LengthUnit target) {

            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toBase();

            return base / target.getFactor();
        }

        // ===== ADDITION (UC6 CORE) =====
        public QuantityLength add(QuantityLength other) {

            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            // Convert both to base unit
            double sumBase = this.toBase() + other.toBase();

            // Convert back to THIS object's unit
            double resultValue = sumBase / this.unit.getFactor();

            return new QuantityLength(resultValue, this.unit);
        }

        // Static version (optional)
        public static QuantityLength add(QuantityLength a, QuantityLength b) {

            if (a == null || b == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }

            return a.add(b);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== STATIC CONVERSION API =====
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        double base = value * source.getFactor();

        return base / target.getFactor();
    }

    // ===== DEMO METHODS =====

    public static void demonstrateLengthEquality(QuantityLength q1, QuantityLength q2) {
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        System.out.println("convert(" + value + ", " + from + ", " + to + ") = "
                + convert(value, from, to));
    }

    public static void demonstrateLengthAddition(QuantityLength q1, QuantityLength q2) {
        System.out.println("add(" + q1 + ", " + q2 + ") = " + q1.add(q2));
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        // Equality
        demonstrateLengthEquality(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH)
        );

        // Conversion
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);

        // Addition (UC6)
        demonstrateLengthAddition(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH)
        ); // → 2 FEET

        demonstrateLengthAddition(
                new QuantityLength(12.0, LengthUnit.INCH),
                new QuantityLength(1.0, LengthUnit.FEET)
        ); // → 24 INCH

        demonstrateLengthAddition(
                new QuantityLength(1.0, LengthUnit.YARD),
                new QuantityLength(3.0, LengthUnit.FEET)
        ); // → 2 YARD
    }
}