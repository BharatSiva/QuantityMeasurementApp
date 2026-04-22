// ===== LENGTH UNIT =====
enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor; // to feet
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

// ===== WEIGHT UNIT =====
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor; // to kg
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

// ===== MAIN APP =====
public class Main {

    // ===== LENGTH CLASS =====
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value))
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toBase(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            return new QuantityLength(target.fromBase(base), target);
        }

        public QuantityLength add(QuantityLength other) {
            double sum = this.toBase() + other.toBase();
            return new QuantityLength(unit.fromBase(sum), unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityLength(target.fromBase(sum), target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== WEIGHT CLASS =====
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value))
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toBase(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.toBase(value);
            return new QuantityWeight(target.fromBase(base), target);
        }

        public QuantityWeight add(QuantityWeight other) {
            double sum = this.toBase() + other.toBase();
            return new QuantityWeight(unit.fromBase(sum), unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityWeight(target.fromBase(sum), target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        // ===== LENGTH =====
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Length Equal: " + l1.equals(l2));
        System.out.println("Length Convert: " + l1.convertTo(LengthUnit.INCH));
        System.out.println("Length Add: " + l1.add(l2));
        System.out.println("Length Add YARD: " + l1.add(l2, LengthUnit.YARD));

        // ===== WEIGHT =====
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Weight Equal: " + w1.equals(w2));
        System.out.println("Weight Convert: " + w1.convertTo(WeightUnit.POUND));
        System.out.println("Weight Add: " + w1.add(w2));
        System.out.println("Weight Add GRAM: " + w1.add(w2, WeightUnit.GRAM));

        // Category Safety
        System.out.println("Length vs Weight: " + l1.equals(w1)); // false
    }
}