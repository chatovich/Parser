package com.chatovich.infohandling.interpreter;

import java.util.ArrayDeque;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class ContextExpression {

    private ArrayDeque<Double> context;

    public ContextExpression() {
        context = new ArrayDeque<>();
    }

    public ArrayDeque<Double> getContext() {
        return context;
    }

    public Double popValue (){
        return context.pop();
    }

    public void pushValue (Double value){
        context.push(value);
    }
}
