package com.chatovich.infohandling.runner;

import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.parserchain.*;

/**
 * Created by Yultos_ on 02.11.2016
 */
public class ParserRunner {

    static final String FILE_NAME = System.getProperty("user.dir")+"/file/text.txt";

    public static void main(String[] args) {

        ParserAction parserAction = new ParserAction();
        String text = parserAction.readFile(FILE_NAME);

        AbstractParser wordParser = new WordParser();
        AbstractParser lexemeParser = new LexemeParser(wordParser);
        SentenceParser sentenceParser = new SentenceParser(lexemeParser);
        ParagraphParser paragraphParser = new ParagraphParser(sentenceParser);
        TextParser textParser = new TextParser(paragraphParser);

        TextComposite parsedText = textParser.parse(text);
        System.out.println(parsedText);

        //paragraphParser.chain(new TextComposite());
    }


}
