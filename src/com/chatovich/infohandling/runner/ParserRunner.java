package com.chatovich.infohandling.runner;

import com.chatovich.infohandling.action.ManipulateComponent;
import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.parserchain.*;
import com.chatovich.infohandling.type.CompositeType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yultos_ on 02.11.2016
 */
public class ParserRunner {

    static final String FILE_NAME = System.getProperty("user.dir")+"/file/text.txt";
    static final Logger LOGGER = LogManager.getLogger(ParserRunner.class);

    public static void main(String[] args) {

        ParserAction parserAction = new ParserAction();
        ManipulateComponent manipulator = new ManipulateComponent();
        String text = parserAction.readFile(FILE_NAME);

        AbstractParser wordParser = new WordParser();
        AbstractParser lexemeParser = new LexemeParser(wordParser);
        SentenceParser sentenceParser = new SentenceParser(lexemeParser);
        ParagraphParser paragraphParser = new ParagraphParser(sentenceParser);
        TextParser textParser = new TextParser(paragraphParser);

        TextComposite parsedText = textParser.parse(text);
        LOGGER.log(Level.INFO, "Initial text:");
        LOGGER.log(Level.INFO, parsedText);

        LOGGER.log(Level.INFO, "Sentences by lexemes' quantity order:");
        manipulator.sortSentencesByLexeme(parsedText).forEach(a -> LOGGER.log(Level.INFO, a));

        LOGGER.log(Level.INFO, "Lexemes sorted by the quantity of the specifed symbol and than in alphabetic order:");
        manipulator.sortLexemes(parsedText, 'a').forEach(a-> LOGGER.log(Level.INFO, a));

        LOGGER.log(Level.INFO, "Text without lexemes of specified length:");
        manipulator.deleteLexemes(parsedText, 'i', 2).getLines().forEach(a-> LOGGER.log(Level.INFO, a));

    }


}
