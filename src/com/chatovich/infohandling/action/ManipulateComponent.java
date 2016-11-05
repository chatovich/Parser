package com.chatovich.infohandling.action;

import com.chatovich.infohandling.entity.SymbolLeaf;
import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.entity.TextComposite;
import com.chatovich.infohandling.type.ComponentType;
import com.chatovich.infohandling.type.CompositeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yultos_ on 05.11.2016
 */
public class ManipulateComponent {

    public List<TextComponent> sortSentencesByLexeme (TextComponent text){

        List<TextComponent> list = getComponentsByType(text, CompositeType.SENTENCE);

        Collections.sort(list, new Comparator<TextComponent>() {
            @Override
            public int compare(TextComponent o1, TextComponent o2) {
                TextComposite a = (TextComposite)o1;
                TextComposite b = (TextComposite)o2;
                return a.getLines().size()-b.getLines().size();
            }
        });
        return list;
    }

    public List<TextComponent> getComponentsByType (TextComponent text, ComponentType type){

        List<TextComponent> resultList = new ArrayList<>();
        if (text.getType().equals(type)){
            resultList.add(text);
        } else{
            if (!text.getType().equals(CompositeType.SYMBOL)){
                TextComposite composite = (TextComposite)text;
                for (TextComponent line : composite.getLines()) {
                    resultList.addAll(getComponentsByType(line, type));
                }
            }

        }
        return resultList;
    }

    public TextComposite deleteLexemes(TextComponent text, Character letter, int lenght) {

        TextComposite resultText = (TextComposite)text;
        for (int i = 0; i < resultText.getLines().size(); i++) {
            TextComponent component = resultText.getLines().get(i);
            if ("LEXEME".equals(component.getType().toString())){
                String lexeme = component.toString();
                if (letter.equals(Character.toLowerCase(lexeme.charAt(0)))&&lexeme.length()==lenght){
                    resultText.getLines().remove(component);
                }
            } else {
                deleteLexemes(component,letter, lenght);
            }
        }
        return resultText;
    }

    public List<TextComponent> sortLexemes (TextComponent text, Character character){

        List<TextComponent> list = getComponentsByType(text, CompositeType.LEXEME);

//        Comparator<TextComponent> comparator = Comparator.comparing(TextComponent->calcSymbolQuantity(TextComponent, character));
//        comparator.thenComparing(TextComponent->calcSymbolQuantity(TextComponent, 'e'));
//        Collections.sort(list, comparator.reversed());

        Collections.sort(list, new Comparator<TextComponent>() {
            @Override
            public int compare(TextComponent o1, TextComponent o2) {
                int o1symbols = calcSymbolQuantity(o1, character);
                int o2symbols = calcSymbolQuantity(o2, character);
                if (o1symbols!=o2symbols){
                    return o2symbols-o1symbols;
                } else {
                    return o1.toString().compareToIgnoreCase(o2.toString());
                }
            }
        });
        return list;
    }

    public int calcSymbolQuantity(TextComponent lexeme, Character character){

        int count = 0;
        String line = lexeme.toString();
        for (int i = 0; i < line.length(); i++) {
            char c = Character.toLowerCase(line.charAt(i));
            if (character.equals(c)){
                count++;
            }
        }
        return count;
}}
