package ru.goryachev.calculator.logic;

import java.text.ParseException;
import java.util.*;

public class RPNCalculator {

    private static final boolean DEBUG = false;
    //    Операторы (обозначение, приоритет)
    Map<String, Integer> operators = new HashMap<>();
    //    Стек вывода в ОПН
    Stack<String> output = new Stack<>();
    //    Временный стек операторв
    Stack<String> stack = new Stack<>();
    //    Операнды для вычисления
    private List<String> operands = new ArrayList<>();


    public RPNCalculator() {
//        Инициализируем словарь операторов, задаем приоритет оператора
        operators.put("+", 1);
        operators.put("-", 1);
        operators.put("*", 2);
        operators.put("/", 2);
    }

    public String getOperandsString() {
        StringBuilder sbOperands = new StringBuilder();
        for (String s : operands) {
            sbOperands.append(s);
        }
        return sbOperands.toString();
    }

    // Для теста
    public void setOperands(List<String> operands) {
        this.operands = operands;
    }

    public void addOperand(String operand) {
        if (isNumber(operand) && (operands.size() > 0 && isCloseBracket(operands.get(operands.size() - 1))))
            return;
        if (isOperator(operand) && (operands.size() > 0 && isOpenBracket(operands.get(operands.size() - 1))))
            return;
        operands.add(operand);
    }

    //    Переводит выражение в ОПН
    public void parse() throws ParseException {

        for (String currentToken : operands) {

            if (isNumber(currentToken)) {
                output.add(currentToken);
            } else if (isOperator(currentToken)) {
                while ((!stack.isEmpty() && isOperator(stack.peek())) && operators.get(currentToken) <= operators.get(stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(currentToken);
            } else if (isOpenBracket(currentToken)) {
                stack.push(currentToken);
            } else if (isCloseBracket(currentToken)) {
                while ((!stack.isEmpty() && !isOpenBracket(stack.peek()))) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    throw new ParseException("Incorrect brackets", 0);
                }
            }
        }

        while (!stack.isEmpty()) {
            if (isOpenBracket(stack.peek()) || isCloseBracket(stack.peek())) {
                throw new ParseException("Incorrect brackets", 0);

            }
            output.add(stack.pop());
        }

        log("Output: " + output.toString());
    }

    //    Производит подсчет
    public double calculate() throws ParseException {
        Stack<String> calculateStack = new Stack<>();
        log(calculateStack.toString());
        Collections.reverse(output);
        while (!output.isEmpty()) {
            if (isNumber(output.peek())) {
                calculateStack.push(output.pop());
            } else {
                try {
                    String operator = output.pop();
                    Double operand1 = Double.parseDouble(calculateStack.pop());
                    Double operand2 = Double.parseDouble(calculateStack.pop());

                    switch (operator) {
                        case "+":
                            calculateStack.push(String.valueOf(operand2 + operand1));
                            break;
                        case "-":
                            calculateStack.push(String.valueOf(operand2 - operand1));
                            break;
                        case "*":
                            calculateStack.push(String.valueOf(operand2 * operand1));
                            break;
                        case "/":
                            calculateStack.push(String.valueOf(operand2 / operand1));
                            break;
                    }
                } catch (EmptyStackException e) {
                    throw new ParseException("Incorrect data", 0);
                }
            }

        }
        return Double.parseDouble(calculateStack.pop());
    }

    //    Вспомогательные методы
    private boolean isNumber(String in) {
        try {
            Double.parseDouble(in);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isOperator(String in) {
        if (operators.containsKey(in)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOpenBracket(String in) {
        if (in.equals("(")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCloseBracket(String in) {
        if (in.equals(")")) {
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        output.clear();
        stack.clear();
        operands.clear();
    }

    private void log(String msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }
}
