package calculator;

public class Logic {

    private double first;
    private String operator;

    public void setFirst(double value) {
        this.first = value;
    }

    public void setOperator(String op) {
        this.operator = op;
    }

    public double calculate(double second) {
        switch (operator) {
            case "+": return first + second;
            case "-": return first - second;
            case "*": return first * second;
            case "/": return first / second;
            case "%": return first % second;
            default: return second;
        }
    }
}