package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.entity.SymbolLeaf;
import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.ClientExpression;
import com.chatovich.infohandling.type.CompositeType;
import com.chatovich.infohandling.type.SymbolType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class SentenceParser extends AbstractParser {

    private static final String LEXEME_REGEX = "([^\\s]+)";
    private static final String MATH_REGEX = "([^A-z])+([\\d\\/\\*\\-\\+\\(\\)])+([^A-z])+";

    public SentenceParser(AbstractParser successor){
        lines = new TextComposite();
        this.successor = successor;

    }
    @Override
    public TextComposite parse(String sentence){
        TextComposite parsedSentence = new TextComposite();
        parsedSentence.setType(CompositeType.SENTENCE);

        Pattern pattern = Pattern.compile(LEXEME_REGEX);
        Matcher matcher = pattern.matcher(sentence);
        Pattern patternMath = Pattern.compile(MATH_REGEX);
        while (matcher.find()){
            String lexeme = matcher.group();
            Matcher matcherMath = patternMath.matcher(lexeme);
            if (matcherMath.find()){
                try {
                    ClientExpression client = new ClientExpression();
                    client.expToPolishNotation(matcher.group());
                    String mathResult = client.calculate().toString();
                    parsedSentence.addComponent(successor.parse(mathResult));

                } catch (WrongDataException e) {
                    LOGGER.log(Level.ERROR, e);
                }
            } else {
                parsedSentence.addComponent(successor.parse(lexeme));
            }

        }
        return parsedSentence;

    }
}
