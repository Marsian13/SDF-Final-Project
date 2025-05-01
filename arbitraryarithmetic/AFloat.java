package arbitraryarithmetic;

import java.util.ArrayList;
import java.util.List;

public class AFloat {
    private boolean is_negative;
    private ArrayList<Integer> intPart = new ArrayList<>();
    private ArrayList<Integer> fracPart = new ArrayList<>();
    private static final int tillDecimal = 1000;

    // Default constructor
    public AFloat() {
        this.intPart.add(0);
        this.fracPart.add(0);
        this.is_negative = false;
    }

    // Constructor to initnum1lize the instance of number which is given as string
    public AFloat(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input string is empty");
        }
        if (s.charAt(0) == '-') {
            this.is_negative = true;
            s = s.substring(1);
        } else if (s.charAt(0) == '+') {
            s = s.substring(1);
        }
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Invalid number format");
        }
        String mainNum = s;

        String[] diffNums = mainNum.split("\\.", -1);
        if (diffNums.length > 2) {
            throw new NumberFormatException("Too many decimal points");
        }

        // integer part handlings
        if (diffNums[0].isEmpty() && diffNums.length > 1) {
            this.intPart.add(0);
        }

        for (char c : diffNums[0].toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NumberFormatException("Invalid digit: " + c);
            }
            this.intPart.add(Character.getNumericValue(c));
        }

        // fractional part handlings
        if (diffNums.length > 1 && !diffNums[1].isEmpty()) {
            for (char c : diffNums[1].toCharArray()) {
                if (!Character.isDigit(c)) {
                    throw new NumberFormatException("Invalid digit: " + c);
                }
                this.fracPart.add(Character.getNumericValue(c));
            }
        }
    }

    // copy constructor
    public AFloat(AFloat other) {
        this.intPart = new ArrayList<>(other.intPart);
        this.fracPart = new ArrayList<>(other.fracPart);
        this.is_negative = other.is_negative;
    }

    // parse method
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    // addtion in float different type of logic tried
    public AFloat add(AFloat other) {
        if (this.is_negative != other.is_negative) {
            if (this.is_negative) {
                AFloat newNum = new AFloat(this);
                newNum.is_negative = false;
                return other.subtract(newNum);
            } else {
                AFloat newNum = new AFloat(other);
                newNum.is_negative = false;
                return this.subtract(newNum);
            }
        }

        AFloat num1 = new AFloat(this);
        AFloat num2 = new AFloat(other);
        alignFrac(num1, num2);

        // str1me sign case
        AFloat a = new AFloat(this), b = new AFloat(other);
        alignFrac(a, b);

        // adding fractional parts
        List<Integer> fsum = AInteger.addNumbers(a.fracPart, b.fracPart);
        int carry = 0;
        if (fsum.size() > a.fracPart.size()) {
            carry = fsum.get(0);
            fsum = fsum.subList(1, fsum.size());
        }

        // adding integer parts
        List<Integer> isum = AInteger.addNumbers(a.intPart, b.intPart);
        if (carry > 0) {
            AInteger tmp = new AInteger(AInteger.listToString(isum));
            isum = AInteger.addNumbers(tmp.number_in_integer, List.of(carry));
        }

        AFloat res = new AFloat();
        res.is_negative = this.is_negative;
        res.intPart = new ArrayList<>(isum);
        res.fracPart = new ArrayList<>(fsum);
        res.normalize();
        return res;
    }

    // subtraction in float different type of logic tried
    public AFloat subtract(AFloat other) {
        if (this.is_negative != other.is_negative) {
            AFloat posOther = new AFloat(other);
            posOther.is_negative = !other.is_negative;
            return this.add(posOther);
        }

        AFloat a = new AFloat(this), b = new AFloat(other);
        alignFrac(a, b);

        List<Integer> num1 = new ArrayList<>(a.intPart);
        num1.addAll(a.fracPart);
        List<Integer> num2 = new ArrayList<>(b.intPart);
        num2.addAll(b.fracPart);

        boolean resultNeg = false;
        if (isSmaller(num1, num2)) {
            AFloat tmp = a;
            a = b;
            b = tmp;
            resultNeg = true;
        }

        // Subtracting fractional parts from right to left using borrow type logic
        List<Integer> fr = new ArrayList<>();
        int borrow = 0;
        for (int i = a.fracPart.size() - 1; i >= 0; i--) {
            int diff = a.fracPart.get(i) - b.fracPart.get(i) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            fr.add(0, diff);
        }

        // Subtracting integer parts from right to left using borrow type logic
        List<Integer> ir = new ArrayList<>();
        for (int i = a.intPart.size() - 1; i >= 0; i--) {
            int diff = a.intPart.get(i) - b.intPart.get(i) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            ir.add(0, diff);
        }

        AFloat res = new AFloat();
        res.is_negative = resultNeg;
        res.intPart = stripLeadingZeros(ir);
        res.fracPart = stripTrailingZeros(fr);
        return res;
    }

    // multiplication in float
    public AFloat multiply(AFloat other) {
        AFloat a = new AFloat(this), b = new AFloat(other);
        int totalFrac = a.fracPart.size() + b.fracPart.size();

        // combining to make bif integers
        String str1 = AInteger.listToString(a.intPart) +
                AInteger.listToString(a.fracPart);
        String str2 = AInteger.listToString(b.intPart) +
                AInteger.listToString(b.fracPart);

        AInteger num1 = new AInteger(str1);
        AInteger num2 = new AInteger(str2);
        AInteger prod = num1.multiply(num2);

        // spliting it back again
        String ps = prod.toString();
        int len = ps.length();
        int maxLen = Math.max(1, len - totalFrac);

        AFloat res = new AFloat();
        res.is_negative = this.is_negative ^ other.is_negative;
        res.intPart = new ArrayList<>();
        for (char c : ps.substring(0, maxLen).toCharArray())
            res.intPart.add(c - '0');
        res.fracPart = new ArrayList<>();
        for (char c : ps.substring(maxLen).toCharArray())
            res.fracPart.add(c - '0');
        res.normalize();
        return res;
    }

    // division in float
    public AFloat divide(AFloat other) {
        if (other.intPart.size() == 1 && other.intPart.get(0) == 0
            && (other.fracPart.isEmpty() || other.fracPart.get(0) == 0)) {
            throw new ArithmeticException("Division by zero");
        }

        // scaling to eliminate decimals
        AFloat a = new AFloat(this), b = new AFloat(other);
        int shift = Math.max(a.fracPart.size(), b.fracPart.size());
        for (int i = a.fracPart.size(); i < shift; i++) a.fracPart.add(0);
        for (int i = b.fracPart.size(); i < shift; i++) b.fracPart.add(0);

        String str1 = AInteger.listToString(a.intPart) +
                    AInteger.listToString(a.fracPart);
        String str2 = AInteger.listToString(b.intPart) +
                    AInteger.listToString(b.fracPart);

        // extending dividend for proper precision
        str1 += "0".repeat(tillDecimal);

        AInteger num1 = new AInteger(str1);
        AInteger num2 = new AInteger(str2);
        AInteger quot = num1.divide(num2);

        // spliting it back 
        String strquot = quot.toString();
        int len = strquot.length();
        int maxLen = Math.max(1, len - tillDecimal);

        AFloat res = new AFloat();
        res.is_negative = this.is_negative ^ other.is_negative;
        res.intPart = new ArrayList<>();
        for (char c : strquot.substring(0, maxLen).toCharArray())
            res.intPart.add(c - '0');
        res.fracPart = new ArrayList<>();
        for (char c : strquot.substring(maxLen).toCharArray())
            res.fracPart.add(c - '0');
        res.normalize();
        return res;
    }

    // my helping functions
    // Normalize: remove leading zeros and handle zero case
    private void normalize() {
        // removing trailing zeros
        while (fracPart.size() > 1 && fracPart.get(fracPart.size() - 1) == 0) {
            fracPart.remove(fracPart.size() - 1);
        }

        // removing leading zeros
        while (intPart.size() > 1 && intPart.get(intPart.size() - 1) == 0) {
            intPart.remove(intPart.size() - 1);
        }

        // zero ans's case
        if (intPart.size() == 1 && intPart.get(0) == 0 && fracPart.isEmpty()) {
            this.is_negative = false;
            this.fracPart.add(0);
        }
    }

    // Pading with zeros
    private static void alignFrac(AFloat a, AFloat b) {
        int la = a.fracPart.size(), lb = b.fracPart.size();
        if (la < lb) {
            for (int i = 0; i < lb - la; i++)
                a.fracPart.add(0);
        } else if (lb < la) {
            for (int i = 0; i < la - lb; i++)
                b.fracPart.add(0);
        }
    }

    // Using isSmaller to strip leading zeros on an integer-digit list.
    private static ArrayList<Integer> stripLeadingZeros(List<Integer> num) {
        int i = 0, n = num.size();
        while (i < n - 1 && num.get(i) == 0)
            i++;
        return new ArrayList<>(num.subList(i, n));
    }

    // Striping trailing zeros in a fractional-digit list, but leaving at least one digit.
    private static ArrayList<Integer> stripTrailingZeros(List<Integer> frac) {
        int n = frac.size();
        int end = n;
        while (end > 1 && frac.get(end - 1) == 0)
            end--;
        return new ArrayList<>(frac.subList(0, end));
    }

    private static boolean isSmaller(List<Integer> num1, List<Integer> num2) {
        num1 = stripLeadingZeros(num1);
        num2 = stripLeadingZeros(num2);

        if (num1.size() != num2.size()) {
            return num1.size() < num2.size();
        }
        for (int i = 0; i < num1.size(); i++) {
            if (!num1.get(i).equals(num2.get(i))) {
                return num1.get(i) < num2.get(i);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str2 = new StringBuilder();
        if (is_negative)
            str2.append("-");
        for (int digit : intPart)
            str2.append(digit);
        if (!fracPart.isEmpty()) {
            str2.append(".");
            for (int digit : fracPart)
                str2.append(digit);
        }
        return str2.toString();
    }

}
