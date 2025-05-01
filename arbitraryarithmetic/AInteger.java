package arbitraryarithmetic;

import java.util.ArrayList;
import java.util.List;

public class AInteger {
    // private String number_in_string; // the input is string so this variable will
    // store it (this is magnitude)
    public boolean is_negative; // it will tell us about the sign of number (true = negative)
    public ArrayList<Integer> number_in_integer = new ArrayList<>();

    // Default constructor
    public AInteger() {
        this.number_in_integer.add(0);
        this.is_negative = false;
    }

    // Constructor to initialize the instance of number which is given as string
    public AInteger(String s) {

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

        // converting each digit in string to integer
        // checking that only numbers are provided in input
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NumberFormatException("Invalid digit: " + c);
            }
            this.number_in_integer.add(Character.getNumericValue(c));
        }
    }

    // copy constructor
    public AInteger(AInteger other) {
        this.number_in_integer = new ArrayList<>(other.number_in_integer);
        this.is_negative = other.is_negative;
    }

    // parse method
    public static AInteger parse(String s) {
        return new AInteger(s);
    }

    // addition logic so that i can handle signs and all properly
    public AInteger add(AInteger other) {
        AInteger result = new AInteger(); // Declare result at the beginning

        if (this.is_negative == other.is_negative) {
            result.number_in_integer = addNumbers(this.number_in_integer, other.number_in_integer);
            result.is_negative = this.is_negative;
        } else {
            if (isSmaller(this.number_in_integer, other.number_in_integer)) {
                result.number_in_integer = subtractNumbers(other.number_in_integer, this.number_in_integer);
                result.is_negative = true;
            } else {
                result.number_in_integer = subtractNumbers(this.number_in_integer, other.number_in_integer);
            }
        }
        result.normalize();
        return result;
    }

    // subraction logic so that i can handle signs and all properly
    public AInteger subtract(AInteger other) {
        AInteger result = new AInteger(); // Declare result at the beginning

        if (this.is_negative == other.is_negative) {
            if (isSmaller(this.number_in_integer, other.number_in_integer) == true) {
                result.number_in_integer = subtractNumbers(other.number_in_integer, this.number_in_integer);
                result.is_negative = true;
            } else {
                result.number_in_integer = subtractNumbers(this.number_in_integer, other.number_in_integer);
            }
        }

        else {
            result.number_in_integer = addNumbers(this.number_in_integer, other.number_in_integer);
            result.is_negative = this.is_negative;
        }
        result.normalize();
        return result;
    }

    // main addition
    public static ArrayList<Integer> addNumbers(List<Integer> a, List<Integer> b) {
        ArrayList<Integer> l1 = new ArrayList<>(a); // Copy to avoid modifying original
        ArrayList<Integer> l2 = new ArrayList<>(b);

        // Pad the shorter list with leading zeros
        while (l1.size() < l2.size())
            l1.add(0, 0);
        while (l1.size() > l2.size())
            l2.add(0, 0);

        ArrayList<Integer> ans = new ArrayList<>();
        int carry = 0;

        for (int i = l1.size() - 1; i >= 0; i--) {
            int sum = l1.get(i) + l2.get(i) + carry;
            carry = sum / 10;
            ans.add(0, sum % 10);
        }

        if (carry != 0)
            ans.add(0, carry);
        ans = stripLeadingZeros(ans);

        return ans;
    }

    // main subtraction
    public ArrayList<Integer> subtractNumbers(List<Integer> a, List<Integer> b) {
        ArrayList<Integer> l1 = new ArrayList<>(a); // Copy to avoid modifying original
        ArrayList<Integer> l2 = new ArrayList<>(b);
        while (l1.size() < l2.size())
            l1.add(0, 0);
        while (l1.size() > l2.size())
            l2.add(0, 0);

        ArrayList<Integer> ans = new ArrayList<>();
        int borrow = 0;

        for (int i = l1.size() - 1; i >= 0; i--) {
            int diff = l1.get(i) - l2.get(i) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            ans.add(0, diff);
        }

        ans = stripLeadingZeros(ans);
        return ans;
    }


    // my helper functions
    // converting my list of integers back to string
    public static String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int digit : list)
            sb.append(digit);
        return sb.toString();
    }

    // Compares two number lists: returns true if num1 < num2
    private boolean isSmaller(List<Integer> num1, List<Integer> num2) {
        // Remove leading zeros for a fair comparison
        num1 = stripLeadingZeros(num1);
        num2 = stripLeadingZeros(num2);

        if (num1.size() != num2.size()) {
            return num1.size() < num2.size();
        }

        // Lexicographic (digit-by-digit) comparison
        for (int i = 0; i < num1.size(); i++) {
            if (!num1.get(i).equals(num2.get(i))) {
                return num1.get(i) < num2.get(i);
            }
        }

        return false; // They are equal
    }

    // Normalize: remove leading zeros and handle zero case
    public void normalize() {
        while (number_in_integer.size() > 1 && number_in_integer.get(number_in_integer.size() - 1) == 0) {
            number_in_integer.remove(number_in_integer.size() - 1);
        }
        if (number_in_integer.size() == 1 && number_in_integer.get(0) == 0) {
            is_negative = false;
        }
    }

    // functions that will help me to write my logic
    // removing leading zeros so that I get proper ans
    private static ArrayList<Integer> stripLeadingZeros(List<Integer> numList) {
        int i = 0;
        while (i < numList.size() - 1 && numList.get(i) == 0) {
            i++;
        }

        return new ArrayList<>(numList.subList(i, numList.size()));
    }

    @Override
    public String toString() {
        String result = listToString(number_in_integer);
        if (is_negative && (result != "0")) {
            result = '-' + result;
        }
        return result;
    }

}