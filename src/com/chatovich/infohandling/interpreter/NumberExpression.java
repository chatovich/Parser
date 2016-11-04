package com.chatovich.infohandling.interpreter;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class NumberExpression extends AbstractMathExpression {

    private double number;

    public NumberExpression(Integer number) {
        this.number = number;
    }

    @Override
    void interpret(ContextExpression c) {
        c.pushValue(number);
    }
}
