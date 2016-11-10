package com.chatovich.infohandling.action;

import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.*;
import com.chatovich.infohandling.type.ComponentType;
import com.chatovich.infohandling.type.CompositeType;
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



    public void sortingStationSymbol(List<AbstractMathExpression> listExp, Stack<String> signsStack, String symbol) throws WrongDataException {
        if (!signsStack.isEmpty()) {

                if (getPriority(symbol)>getPriority(signsStack.peek())){
                    signsStack.push(symbol);

                } else {
                    String sign = signsStack.pop();
                    defineOperation(sign, listExp);
                    signsStack.push(symbol);
                }
        } else {
            signsStack.push(symbol);
        }
    }

    public int getPriority(String sign) {
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
            default: throw new WrongDataException("Can't define the sign: "+sign);
        }
    }

    public String calcIncrDecr(String exp, Matcher matcher){

        String result=exp;
        if (matcher.find()){
            int end = matcher.end();
            int start = matcher.start();
            if (exp.length()>end&&Character.isDigit(exp.charAt(end))){
                int num=0;
                if ("++".equals(matcher.group())) {
                    num = Integer.parseInt(((Character) exp.charAt(end)).toString()) + 1;
                } else{
                    num = Integer.parseInt(((Character) exp.charAt(end)).toString()) - 1;
                }
                result = exp.replace(exp.substring(start,start+3),String.valueOf(num));
            } else{
                if (start>0&&Character.isDigit(exp.charAt(start-1))){
                    result = exp.replace(exp.substring(start, end),"");
                }
            }
        }
        return result;
    }



}
