package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.CompositeType;
import org.apache.logging.log4j.Level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class ParagraphParser extends AbstractParser {

    private static final String SENTENCE_REGEX = "[a-zA-Z\\d\\s,:;\\-+\\(\\)='\\*/#$%&<>@^_`{|}~]+[.!?]+";

    public ParagraphParser(AbstractParser successor){
        lines = new TextComposite();
        this.successor = successor;

    }
    @Override
    public TextComposite parse(String paragraph){
        TextComposite parsedParagraph = new TextComposite();
        parsedParagraph.setType(CompositeType.PARAGRAPH);

        Pattern pattern = Pattern.compile(SENTENCE_REGEX);
        Matcher matcher = pattern.matcher(paragraph);
        while (matcher.find()){
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                parsedParagraph.addComponent(successor.parse(sentence));
            }

        }
        return parsedParagraph;
    }
}
