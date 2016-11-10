package com.chatovich.infohandling.action;

import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.ClientExpression;

import java.util.*;
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

//
        ParserAction parserAction = new ParserAction();
//        String expression = "--5+(--4)";
//        Pattern incrPattern = Pattern.compile("\\+\\+");
//        Matcher incrMatcher = incrPattern.matcher(expression);
//        while (incrMatcher.find()){
//            Matcher matcher = incrPattern.matcher(expression);
//            expression = parserAction.calcIncrDecr(expression, matcher);
//        }
//
//        Pattern decrPattern = Pattern.compile("\\-\\-");
//        Matcher decrMatcher = decrPattern.matcher(expression);
//        while (decrMatcher.find()){
//            Matcher matcher = decrPattern.matcher(expression);
//            expression = parserAction.calcIncrDecr(expression, matcher);
//        }
//        System.out.println(expression);



        ClientExpression client = new ClientExpression();
        client.expToPolishNotation("13+2*21");
        System.out.println(client.calculate().toString());
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
