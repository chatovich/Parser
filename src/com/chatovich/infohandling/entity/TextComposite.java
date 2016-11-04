package com.chatovich.infohandling.entity;

import com.chatovich.infohandling.type.CompositeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class TextComposite implements TextComponent {
    private List<TextComponent> lines;
    private CompositeType type;

    @Override
    public CompositeType getType() {
        return type;
    }

    public void setType(CompositeType type) {
        this.type = type;
    }

    public TextComposite() {
        lines = new ArrayList<>();
    }

    public List<TextComponent> getLines() {
        return lines;
    }

    public void addComponent (TextComponent textComponent){
        lines.add(textComponent);
    }

    @Override
    public String toString (){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<lines.size(); i++) {
            TextComponent line = lines.get(i);
            switch (line.getType().toString()){
                case "PARAGRAPH": sb.append("\n     ").append(line);
                    break;
                case "LEXEME": sb.append(line).append(" ");
                    break;
                case "SENTENCE":
                case "WORD":
                case "PUNCT": sb.append(line);
                    break;
                case "MATHEMATIC": {
                    sb.append(line);
                    if (i==lines.size()){
                        sb.append(" ");
                    }
                }

                    break;
                case "LETTER": sb.append(line);
                    break;
            }
        }
        return sb.toString();
    }

}
