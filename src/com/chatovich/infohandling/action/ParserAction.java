package com.chatovich.infohandling.action;

import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 02.11.2016
 */
public class ParserAction {

    static final Logger LOGGER = LogManager.getLogger(ParserAction.class);
    static final String PARENTHESES_REGEX = "(\\()[\\d\\/\\*\\-\\+\\(\\)]+(\\))";
    static final String MATH_ELEMENT_REGEX = "[\\d]+|[\\+\\-\\*\\/]+";

    public String readFile (String filename){

        String text = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()){
                text+=scanner.nextLine() + "\r\n";
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.ERROR, "File not found");
            throw new RuntimeException();
        } finally {
            if (scanner!=null) {
                scanner.close();
            }
        }
        return text;
    }


    public String calcExpression(String line) throws WrongDataException{

        Matcher matcher = Pattern.compile(PARENTHESES_REGEX).matcher(line);
        List<String> elementList = new ArrayList<>();
        ParserAction parserAction = new ParserAction();
        String exp=line;
        //String newLine="";
        //String result = "";
        if (matcher.find()) {
            String newLine = matcher.group();
            if (newLine.contains("(")){
                newLine = newLine.substring(1, matcher.group().length()-1);
            }
            String result = calcExpression(newLine);
            exp = line.replace(matcher.group(),result);
            matcher.reset();

        }

        Matcher matcherMath = Pattern.compile(MATH_ELEMENT_REGEX).matcher(exp);
        //every element in the expression (number or math sign) add to the list
        while (matcherMath.find()) {
            elementList.add(matcherMath.group());
        }
        String result = "";
        if (elementList.size()>2){
            elementList = parserAction.chooseOperation(elementList,"*","/");
            elementList = parserAction.chooseOperation(elementList,"+","-");
        }
        if (elementList.size()==2){
            result = checkUnaryOperation(elementList);
        }
        if (elementList.size()==1){
            result = elementList.get(0);
        }

        return result;
    }


    public String calcSimpleExp(String a, String b, String sign) throws WrongDataException{

        int x = Integer.parseInt(a);
        int y = Integer.parseInt(b);
        int result = 0;

        if (sign.equals("*")){
            result  = x*y;
        }
        if (sign.equals("/")){
            if (y!=0) {
                result = x / y;
            } else {
                throw new WrongDataException("Division by 0, impossible to calculate");
            }
        }
        if (sign.equals("+")){
            result  = x+y;
        }
        if (sign.equals("-")){
            result  = x-y;
        }

        return String.valueOf(result);
    }


    public List<String> chooseOperation (List<String> elementList, String signFirst, String signSecond) throws WrongDataException{

        while (elementList.contains(signFirst)|elementList.contains(signSecond)&&elementList.size()>2) {
            int indexFirst = elementList.indexOf(signFirst);
            int indexSecond = elementList.indexOf(signSecond);
            String result="";
            if (indexFirst>0&&indexSecond>0) {
                if (indexFirst < indexSecond) {
                    result = calcSimpleExp(elementList.get(indexFirst - 1), elementList.get(indexFirst + 1), signFirst);
                    elementList.set(indexFirst - 1, result);
                    elementList.remove(indexFirst);
                    elementList.remove(indexFirst);
                }
                if (indexFirst > indexSecond) {
                    result = calcSimpleExp(elementList.get(indexSecond - 1), elementList.get(indexSecond + 1), signSecond);
                    elementList.set(indexSecond - 1, result);
                    elementList.remove(indexSecond);
                    elementList.remove(indexSecond);
                }
            } else {
                if (indexFirst > indexSecond) {
                    result = calcSimpleExp(elementList.get(indexFirst - 1), elementList.get(indexFirst + 1), signFirst);
                    elementList.set(indexFirst - 1, result);
                    elementList.remove(indexFirst);
                    elementList.remove(indexFirst );
                }
                if (indexFirst < indexSecond) {
                    result = calcSimpleExp(elementList.get(indexSecond - 1), elementList.get(indexSecond + 1), signSecond);
                    elementList.set(indexSecond - 1, result);
                    elementList.remove(indexSecond);
                    elementList.remove(indexSecond );
                }
            }
        }

        return elementList;

    }


    public String checkUnaryOperation (List <String> elementList){
        String result="";
        switch (elementList.get(0)){
            case "++" : {
                result = String.valueOf(Integer.parseInt(elementList.get(1))+1);
                break;
            }
            case "--" : {
                result = String.valueOf(Integer.parseInt(elementList.get(1))-1);
                break;
            }
                default: result = "";

        }
        switch (elementList.get(1)){
            case "++" : {
                result = String.valueOf(Integer.parseInt(elementList.get(0))+1);
                break;
            }
            case "--" : {
                result = String.valueOf(Integer.parseInt(elementList.get(0))-1);
                break;
            }
        }
        if (result.isEmpty()){
            for (String s : elementList) {
                result+=s;
            }
        }
        return result;
    }


    public List<String> sortingStation(String expression) {

        Set <String> signs = new HashSet<>();
        signs.add("+");
        signs.add("-");
        signs.add("*");
        signs.add("/");

        //mathematic postfix expression in Reverse Polish Notation
        List<String> polishExp = new ArrayList<>();
        //accessory stack for signs
        Stack <String> signsStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            String symbol = expression.substring(i,i+1);
            System.out.println("symbol "+symbol);
            System.out.println("priority "+getPriority(symbol));

            if (signs.contains(symbol)){
                //sortingStationSymbol(polishExp, signsStack, symbol);
            } else {
                if ("(".equals(symbol)){
                    signsStack.push(symbol);
                }
                if (")".equals(symbol)){
                    do {
                        polishExp.add(signsStack.pop());
                    } while(!"(".equals(signsStack.peek()));
                    signsStack.pop();
                }
                if (Character.isDigit(symbol.charAt(0))){
                    polishExp.add(symbol);
                }
            }
            System.out.println("stack");
            signsStack.forEach(System.out::print);
            System.out.println();
            System.out.println("polish");
            polishExp.forEach(System.out::print);
            System.out.println();
        }
        while (!signsStack.isEmpty()){
            polishExp.add(signsStack.pop());
        }
        return polishExp;
    }

    public void sortingStationSymbol(List<AbstractMathExpression> listExp, Stack<String> signsStack, String symbol, List <String> polishExp) throws WrongDataException {
        if (!signsStack.isEmpty()) {
            if (signsStack.peek().equals(symbol)&&"-".equals(symbol)||signsStack.peek().equals(symbol)&&"+".equals(symbol)){
                    signsStack.pop();
                    String unary = symbol + symbol;
                    signsStack.push(unary);
            } else {
                if (getPriority(symbol)>getPriority(signsStack.peek())){
                    signsStack.push(symbol);

                } else {
                    String sign = signsStack.pop();
                    polishExp.add(sign);
                    defineOperation(sign, listExp);
                    signsStack.push(symbol);
                }
            }
        } else {
            signsStack.push(symbol);
        }
    }

    public int getPriority(String sign){
        int priority = 0;
        switch (sign){
            case "+": priority = 1;
                break;
            case "-": priority = 1;
                break;
            case "*": priority = 2;
                break;
            case "/": priority = 2;
                break;
        }
        return priority;
    }

    public void defineOperation (String sign, List<AbstractMathExpression> listExp) throws WrongDataException {
        switch (sign){
            case "+": listExp.add(new PlusMathExpression());
                break;
            case "-": listExp.add(new MinusMathExpression());
                break;
            case "*": listExp.add(new MultiplyMathExpression());
                break;
            case "/": listExp.add(new DivideMathExpression());
                break;
            case "++": listExp.add(new IncrementExpression());
                break;
            case "--": listExp.add(new DecrementExpression());
                break;
            default: throw new WrongDataException("Can't define the sign: "+sign);

        }

    }
}
