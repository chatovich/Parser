package com.chatovich.infohandling.parserchain;

import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.ComponentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yultos_ on 01.11.2016
 */
public abstract class AbstractParser {

    static final Logger LOGGER = LogManager.getLogger(AbstractParser.class);
    public AbstractParser successor;
    public TextComposite lines;

    public AbstractParser() {
        lines = new TextComposite();
    }

    public AbstractParser(AbstractParser successor){
        lines = new TextComposite();
        this.successor = successor;

    }

    public void setSuccessor(AbstractParser successor) {
        this.successor = successor;
    }

    abstract public TextComposite parse(String text);

    public void chain(TextComponent textComponent){

        if (!lines.getLines().isEmpty()){
            lines.getLines().forEach(a -> successor.chain(a));

        }

    }
}
