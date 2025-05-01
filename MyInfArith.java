import arbitraryarithmetic.*;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println(
                    "Please enter input in format of <int/float> <add/sub/mul/div> <First operand> <Second operand>");
            return;
        }
        // if (args[0] != "int" || args[0] != "float"){
        // System.out.println("");
        // }

        String typeOfNumbers = args[0];
        String operatationToDO = args[1];
        String number1 = args[2];
        String number2 = args[3];
        // System.out.println(args[0]);

        if (typeOfNumbers.equals("int")) {
            AInteger a = new AInteger(number1);
            AInteger b = new AInteger(number2);
            AInteger result;
            switch (operatationToDO) {
                case "add":
                    result = a.add(b);
                    break;
                case "sub":
                    result = a.subtract(b);
                    break;
                case "mul":
                    result = a.multiply(b);
                    break;
                case "div":
                    result = a.divide(b);
                    break;
                default:
                    System.out.println("Invalid operation");
                    return;
            }
            System.out.println(result);
        }else if (typeOfNumbers.equals("float")){
            // AFloat a = new AFloat(number1);
            // AFloat b = new AFloat(number2);
            // AFloat result;
            // switch (operatationToDO) {
            //     case "add":
            //         result = a.add(b);
            //         break;
            //     case "sub":
            //         result = a.subtract(b);
            //         break;
            //     case "mul":
            //         result = a.multiply(b);
            //         break;
            //     case "div":
            //         result = a.divide(b);
            //         break;
            //     default:
            //         System.out.println("Invalid operation");
            //         return;
            // }
            // System.out.println(result);
        }else {
            System.out.println("Invalid type : type of number should be 'int' or 'float' ");
        }

    }
}
