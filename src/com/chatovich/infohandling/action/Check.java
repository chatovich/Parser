package com.chatovich.infohandling.action;

import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.ClientExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 02.11.2016
 */
public class Check {

    static final String SENTENCE_REGEX = "[a-zA-Z\\d\\s,:;\\-+\\(\\)='\\*/#$%&<>@^_`{|}~]+[.!?]+";
    private static final String LEXEME_REGEX = "([^\\s]+)";
    private static final String WORD_REGEX = "[a-zA-Z-]+";
    private static final String PARENTHESES_REGEX = "(\\()[\\d\\/\\*\\-\\+\\(\\)]+(\\))";
    static final String MATH_ELEMENT_REGEX = "[\\d]+|[\\+\\-\\*\\/]+";

    public static void main(String[] args) throws WrongDataException {

        ParserAction parserAction = new ParserAction();
        //System.out.println(parserAction.calcExpression("(0-(2*2*(3*(2-1/2*2)-2)-10/2))*(++5)"));
        //System.out.println(calc("(0-(2*2*(3*(2-1/2*2)-2)-10/2))"));
        //System.out.println(parserAction.sortingStation("3+(4++)"));
        ClientExpression client = new ClientExpression();
        client.expToPolishNotation("5*(1*2*(3*(4*(5-4)-3)-2)-1)");
        Number mathResult = client.calculate();
        System.out.println("!!!"+mathResult.toString());
    }

    public static String calc (String line){
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
                System.out.println(newLine);
            }
            String result = calc(newLine);
            System.out.println("res "+result);
            System.out.println("line "+line);
            System.out.println("newline "+newLine);
            exp = line.replace(matcher.group(),result);
            System.out.println("exp2 "+exp);
            matcher.reset();

        }

        Matcher matcherMath = Pattern.compile(MATH_ELEMENT_REGEX).matcher(exp);
        //every element in the expression (number or math sign) add to the list
        while (matcherMath.find()) {
            elementList.add(matcherMath.group());
        }

        //elementList = parserAction.chooseOperation(elementList,"*","/");

        //elementList = parserAction.chooseOperation(elementList,"+","-");
        for (String s : elementList) {
            System.out.println("list "+s);
        }

        return elementList.get(0);
    }
}
