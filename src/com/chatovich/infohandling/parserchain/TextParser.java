package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.CompositeType;
import org.apache.logging.log4j.Level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class TextParser extends AbstractParser {

    private static final String PARAGRAPH_REGEX = "(\\s){5}(.)+(\\.!?)+";

    public TextParser(AbstractParser successor){
        lines = new TextComposite();
        this.successor = successor;

    }
    @Override
    public TextComposite parse(String text){

        TextComposite parsedText = new TextComposite();
        parsedText.setType(CompositeType.TEXT);

        Pattern pattern = Pattern.compile(PARAGRAPH_REGEX);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String paragraph = matcher.group().trim();
            //System.out.println("!!!"+paragraph);
            parsedText.addComponent(successor.parse(paragraph));

        }
        LOGGER.log(Level.INFO,"Text has "+parsedText.getLines().size()+" paragraphs");

        return parsedText;
    }

    @Override
    public void chain (TextComponent textComponent){
        System.out.println("symbol");
    }
}
