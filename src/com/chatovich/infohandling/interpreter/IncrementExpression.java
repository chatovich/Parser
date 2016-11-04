package com.chatovich.infohandling.interpreter;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class IncrementExpression extends AbstractMathExpression {

    @Override
    void interpret(ContextExpression c) {
        c.pushValue(c.popValue()+1);
    }
}
