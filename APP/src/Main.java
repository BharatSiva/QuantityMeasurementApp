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

        // ===== INSTANCE CONVERSION =====
        public double convertTo(LengthUnit target) {

            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toBase();

            return base / target.getFactor();
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

    // ===== DEMO METHODS (OVERLOADING) =====

    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {

        double result = convert(value, from, to);

        System.out.println("convert(" + value + ", " + from + ", " + to + ") = " + result);
    }

    public static void demonstrateLengthConversion(QuantityLength q,
                                                   LengthUnit to) {

        double result = q.convertTo(to);

        System.out.println("convert(" + q + " → " + to + ") = " + result);
    }

    public static void demonstrateLengthEquality(QuantityLength q1,
                                                 QuantityLength q2) {

        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        // Equality (UC1–UC4)
        demonstrateLengthEquality(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH)
        );

        demonstrateLengthEquality(
                new QuantityLength(1.0, LengthUnit.YARD),
                new QuantityLength(3.0, LengthUnit.FEET)
        );

        // Conversion (UC5)
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(2.54, LengthUnit.CENTIMETER, LengthUnit.INCH);

        // Instance conversion
        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.INCH);
    }
}