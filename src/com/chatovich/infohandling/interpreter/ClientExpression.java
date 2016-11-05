package com.chatovich.infohandling.interpreter;

import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.exception.WrongDataException;

import java.util.*;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class ClientExpression {

    private ArrayList<AbstractMathExpression> listExp;
    private ParserAction parserAction;

    public ClientExpression() {
        listExp = new ArrayList<>();
        parserAction = new ParserAction();
    }

    public void expToPolishNotation(String expression) throws WrongDataException {

        Set<String> signs = new HashSet<>();
        signs.add("+");
        signs.add("-");
        signs.add("*");
        signs.add("/");

        //mathematic postfix expression in Reverse Polish Notation
        //List<String> polishExp = new ArrayList<>();
        //accessory stack for signs
        Stack<String> signsStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            String symbol = expression.substring(i,i+1);

            if (signs.contains(symbol)){
                parserAction.sortingStationSymbol(listExp, signsStack, symbol);
            } else {
                if ("(".equals(symbol)){
                    signsStack.push(symbol);
                }
                if (")".equals(symbol)){
                    do {
                        String sign = signsStack.pop();
                        //polishExp.add(sign);
                        parserAction.defineOperation(sign,listExp);
                    } while(!"(".equals(signsStack.peek()));
                    signsStack.pop();
                }
                if (Character.isDigit(symbol.charAt(0))){
                    listExp.add(new NumberExpression(Integer.parseInt(symbol)));
                    //polishExp.add(symbol);
                }
            }

            //polishExp.forEach(System.out::print);

        }
        while (!signsStack.isEmpty()){
            String sign = signsStack.pop();
            parserAction.defineOperation(sign,listExp);
            //polishExp.add(sign);
        }

    }

    public Double calculate (){
        ContextExpression context = new ContextExpression();
        for (AbstractMathExpression exp : listExp) {
            exp.interpret(context);
            context.getContext().forEach(System.out::print);
            System.out.println();
        }
        return context.popValue();
    }
}
