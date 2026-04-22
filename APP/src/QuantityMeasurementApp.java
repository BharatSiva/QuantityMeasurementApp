enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    // Convert this unit → base (feet)
    public double toBase(double value) {
        return value * factor;
    }

    // Convert base (feet) → this unit
    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}


// ===== MAIN CLASS =====
public class QuantityMeasurementApp {

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

        // ===== EQUALITY =====
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisBase = unit.toBase(value);
            double otherBase = other.unit.toBase(other.value);

            return Double.compare(thisBase, otherBase) == 0;
        }

        // ===== CONVERSION =====
        public QuantityLength convertTo(LengthUnit target) {

            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = unit.toBase(value);

            double result = target.fromBase(base);

            return new QuantityLength(result, target);
        }

        // ===== ADD (UC6) =====
        public QuantityLength add(QuantityLength other) {

            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }

            double sumBase =
                    unit.toBase(value) +
                            other.unit.toBase(other.value);

            double result = unit.fromBase(sumBase);

            return new QuantityLength(result, unit);
        }

        // ===== ADD WITH TARGET (UC7) =====
        public QuantityLength add(QuantityLength other, LengthUnit target) {

            if (other == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumBase =
                    unit.toBase(value) +
                            other.unit.toBase(other.value);

            double result = target.fromBase(sumBase);

            return new QuantityLength(result, target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);

        // Equality
        System.out.println("Equal: " + a.equals(b));

        // Conversion
        System.out.println("Convert: " + a.convertTo(LengthUnit.INCH));

        // UC6 Addition
        System.out.println("Add (same unit): " + a.add(b));

        // UC7 Addition with target
        System.out.println("Add (target YARD): " + a.add(b, LengthUnit.YARD));
    }
}