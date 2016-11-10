package com.chatovich.infohandling.interpreter;

import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.exception.WrongDataException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class ClientExpression {

    static final String INCR_REGEX = "\\+\\+";
    static final String DECR_REGEX = "\\-\\-";
    static final String DIGIT = "[0-9]+";
    //mathematic postfix expression in Reverse Polish Notation
    private ArrayList<AbstractMathExpression> polishExp;
    private ParserAction parserAction;

    public ClientExpression() {
        polishExp = new ArrayList<>();
        parserAction = new ParserAction();
    }

    public void expToPolishNotation(String expression) throws WrongDataException {

        //get rid of increment and decrement
        Pattern incrPattern = Pattern.compile(INCR_REGEX);
        Matcher incrMatcher = incrPattern.matcher(expression);
        while (incrMatcher.find()){
            Matcher matcher = incrPattern.matcher(expression);
            expression = parserAction.calcIncrDecr(expression, matcher);
        }

        Pattern decrPattern = Pattern.compile(DECR_REGEX);
        Matcher decrMatcher = decrPattern.matcher(expression);
        while (decrMatcher.find()){
            Matcher matcher = decrPattern.matcher(expression);
            expression = parserAction.calcIncrDecr(expression, matcher);
        }

        Set<String> signs = new HashSet<>();
        signs.add("+");
        signs.add("-");
        signs.add("*");
        signs.add("/");

        //accessory stack for signs
        Stack<String> signsStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            String symbol = expression.substring(i,i+1);

            if (signs.contains(symbol)){
                parserAction.sortingStationSymbol(polishExp, signsStack, symbol);
            } else {
                if ("(".equals(symbol)){
                    signsStack.push(symbol);
                }
                if (")".equals(symbol)){
                    while(!"(".equals(signsStack.peek())){
                        String sign = signsStack.pop();
                        if (signs.contains(sign)){
                            parserAction.defineOperation(sign, polishExp);
                        }
                    }
                    signsStack.pop();
                }
                if (Character.isDigit(symbol.charAt(0))){
                    String line = expression.substring(i,expression.length());
                    Pattern pattern = Pattern.compile(DIGIT);
                    Matcher matcher = pattern.matcher(line);
                    String number="";
                    if (matcher.find()){
                        number=matcher.group();
                    }
                    polishExp.add(new NumberExpression(Integer.parseInt(number)));
                    i+=number.length()-1;
                }
            }
        }
        //when all symbols in the line are operated, push symbols from stack to polishExp
        while (!signsStack.isEmpty()){
            String sign = signsStack.pop();
            parserAction.defineOperation(sign, polishExp);
        }

    }

    public Double calculate (){
        ContextExpression context = new ContextExpression();
        for (AbstractMathExpression exp : polishExp) {
            exp.interpret(context);
        }
        return context.popValue();
    }
}
