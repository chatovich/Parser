package com.chatovich.infohandling.interpreter;

/**
 * Created by Yultos_ on 04.11.2016
 */
public class MinusMathExpression extends AbstractMathExpression {

    @Override
    void interpret(ContextExpression c) {
        if (c.getContext().size()>1) {
            Double a = c.popValue();
            Double b = c.popValue();
            c.pushValue(b - a);
        } else {
            Double a = c.popValue();
            c.pushValue(a-2*a);
        }
    }
}
