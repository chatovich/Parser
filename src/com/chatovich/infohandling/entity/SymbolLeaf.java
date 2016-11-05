package com.chatovich.infohandling.entity;

import com.chatovich.infohandling.type.CompositeType;
import com.chatovich.infohandling.type.SymbolType;

/**
 * Created by Yultos_ on 01.11.2016
 */
public class SymbolLeaf implements TextComponent, Cloneable {
    private SymbolType symbolType;
    private Character symbol;

    public SymbolLeaf(SymbolType symbolType, Character symbol) {
        this.symbolType = symbolType;
        this.symbol = symbol;
    }

    @Override
    public SymbolType getType() {
        return symbolType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbolType(SymbolType symbolType) {
        this.symbolType = symbolType;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
