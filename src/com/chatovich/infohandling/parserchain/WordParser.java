package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.entity.SymbolLeaf;
import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.CompositeType;
import com.chatovich.infohandling.type.SymbolType;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class WordParser extends AbstractParser {

    public WordParser() {
        super();
    }
    @Override
    public TextComposite parse(String word){

        TextComposite parsedWord = new TextComposite();
        parsedWord.setType(CompositeType.WORD);

        for (char c : word.toCharArray()) {
            parsedWord.addComponent(new SymbolLeaf(SymbolType.LETTER, c));
        }

        return parsedWord;
    }
}
