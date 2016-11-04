package com.chatovich.infohandling.interpreter;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class DivideMathExpression extends AbstractMathExpression {

    @Override
    void interpret(ContextExpression c) {
        Double a = c.popValue();
        Double b = c.popValue();
        c.pushValue(b/a);
    }
}
