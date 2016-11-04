package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.entity.SymbolLeaf;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.CompositeType;
import com.chatovich.infohandling.type.SymbolType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class LexemeParser extends AbstractParser {

    private static final String WORD_REGEX = "[a-zA-Z-]+";

    public LexemeParser(AbstractParser successor){
        lines = new TextComposite();
        this.successor = successor;

    }
    @Override
    public TextComposite parse(String lexeme){

        TextComposite parsedLexeme = new TextComposite();
        parsedLexeme.setType(CompositeType.LEXEME);

        Pattern pattern = Pattern.compile(WORD_REGEX);
        Matcher matcher = pattern.matcher(lexeme);
        if (matcher.find()){
            String word = matcher.group();
            int start = matcher.start();
            //if the word starts from the beginning of the lexeme
            if (start==0){
                parsedLexeme.addComponent(successor.parse(word));
                String punct = lexeme.substring(matcher.end());
                for (char c : punct.toCharArray()) {
                    parsedLexeme.addComponent(new SymbolLeaf(SymbolType.PUNCT, c));
                }
                //System.out.println(word+"   "+punct);
            } else{
                //if not, take part before the word, than the word itself, than the part after
                String punctStart = lexeme.substring(0,start);
                for (char c : punctStart.toCharArray()) {
                    parsedLexeme.addComponent(new SymbolLeaf(SymbolType.PUNCT, c));
                }
                parsedLexeme.addComponent(successor.parse(word));
                String punctEnd = lexeme.substring(matcher.end());
                for (char c : punctEnd.toCharArray()) {
                    parsedLexeme.addComponent(new SymbolLeaf(SymbolType.PUNCT, c));
                }
                //System.out.println(punctStart+"   "+word+"   "+punctEnd);
            }
        }

        return parsedLexeme;

    }

}
