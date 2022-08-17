import java.util.Scanner;

public class Calculator {
    public static String[] operands;
    public static String operator;
    public static int[] operand = new int[3];
    public  static int result_expression_arabic = 0;
    public static String result_expression_roman;
    public static void main(String[] args) throws ExceptionValid{
        Scanner cnr = new Scanner(System.in);
        //System.out.println("Калькулятор умеет выполнять операции: a + b, a - b, a * b, a / b\nКалькулятор принимает числа только до 10\nКалькулятор умеет работать только с арабскими или римскими цифрами одновременно\nПример: (5+5)\nВведите арифметическое выражение ниже: ");
        String expression = cnr.nextLine().trim();
        String validateExpression = expression.replaceAll("[0-9IVXivx+/*-]","").trim();
        operator = expression.replaceAll("[^+/*-]","").trim();
        if (validateExpression.length() != 0){
            throw new ExceptionValid("throws Exception //т.к. строка не является математической операцией или вы используете запрещённые символы");
        }
        if (operator.length() != 1){
            throw new ExceptionValid("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        operands = expression.split("[" + operator + "]"); //делит операнды на 2 символа
        operands[0] = operands[0].trim().toUpperCase(); // проверки на пробелы и маленькие символы
        operands[1] = operands[1].trim().toUpperCase();

        // Проверяет какие символы написал пользователь
        if (operands[0].matches("\\d+") && operands[1].matches("\\d+")) { //Арабские
            arabic();
            System.out.println(result_expression_arabic);
        } else if (operands[0].matches("[IVX]+") && operands[1].matches("[IVX]+")){ //Римские
            roman();
            System.out.println(result_expression_roman);
        }else {
            throw new ExceptionValid("throws Exception //т.к. используются одновременно разные системы счисления");
        }
    }

    static void calculate() throws ExceptionValid{ //Метод который выполняет + - * / и проверяет на то что-бы числа были не больше 10ти
        if (operand[0] <= 10 && operand[1] <= 10){
            switch (operator){
                case "+":
                    result_expression_arabic = operand[0]+operand[1];
                    break;
                case "-":
                    result_expression_arabic = operand[0]-operand[1];
                    break;
                case "*":
                    result_expression_arabic = operand[0]*operand[1];
                    break;
                case "/":
                    result_expression_arabic = operand[0]/operand[1];
                    break;
                default:
                    System.out.println("throws Exception //Ошибка в методе 'calculate'!!!");
            }
        }else {
            throw new ExceptionValid("throws Exception //Вы не можете использовать число больше 10-ти или больше числа X");
        }
    }

    static void arabic() throws ExceptionValid{ //Функция для арабских чисел
        operand[0] = Integer.parseInt(operands[0]);
        operand[1] = Integer.parseInt(operands[1]);
        calculate();
    }

    static void roman() throws ExceptionValid{
        int[] arabic = {100, 90, 50, 40, 10, 9, 5, 4, 1}; //Массивы для преобразования
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        result_expression_roman = ""; // Ответ
        for (int i = 0; i < operands.length; i++) {
            switch (operands[i]) {
                case "IV":
                    operand[i] = 4;
                    break;
                case "IX":
                    operand[i] = 9;
                    break;
                default:
                    char[] oper = operands[i].toCharArray();
                    for (int j = 0; j < oper.length; j++) { // Преобразователь из римских в арабские
                        for (int f = 0; f <roman.length; f++){
                            if (Character.toString(oper[j]).equals(roman[f])) {
                                operand[i] += arabic[f];
                            }
                        }
                    }
            }

        }
        calculate();
        if (result_expression_arabic > 0){ // Преобразователь из арабских в римские
            for (int i=0; i<arabic.length ;i++) {
                while (arabic[i] <= result_expression_arabic) {
                    result_expression_roman += roman[i];
                    result_expression_arabic = result_expression_arabic - arabic[i];
                }
            }
        }else {
            throw new ExceptionValid("throws Exception //т.к. в римской системе нет нуля и отрицательных чисел");
        }
    }
}
