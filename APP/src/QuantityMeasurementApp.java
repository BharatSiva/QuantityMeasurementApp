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

        // ===== ADD (UC6) =====
        public QuantityLength add(QuantityLength other) {

            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }

            double sumBase = this.toBase() + other.toBase();

            double result = sumBase / this.unit.getFactor();

            return new QuantityLength(result, this.unit);
        }

        // ===== ADD WITH TARGET UNIT (UC7 CORE) =====
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            // Convert both → base
            double sumBase = this.toBase() + other.toBase();

            // Convert → target unit
            double result = sumBase / targetUnit.getFactor();

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== STATIC CONVERT =====
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Invalid unit");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        double base = value * source.getFactor();

        return base / target.getFactor();
    }

    // ===== DEMO =====
    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);

        // UC6
        System.out.println("UC6: " + a.add(b)); // 2 FEET

        // UC7 (target = FEET)
        System.out.println("UC7 FEET: " + a.add(b, LengthUnit.FEET));

        // UC7 (target = INCH)
        System.out.println("UC7 INCH: " + a.add(b, LengthUnit.INCH));

        // UC7 (target = YARD)
        System.out.println("UC7 YARD: " + a.add(b, LengthUnit.YARD));

        // UC7 (CM example)
        QuantityLength c = new QuantityLength(2.54, LengthUnit.CENTIMETER);
        System.out.println("UC7 CM: " + c.add(b, LengthUnit.CENTIMETER));
    }
}