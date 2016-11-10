package com.chatovich.infohandling.test;

import com.chatovich.infohandling.action.ManipulateComponent;
import com.chatovich.infohandling.action.ParserAction;
import com.chatovich.infohandling.entity.TextComponent;
import com.chatovich.infohandling.exception.WrongDataException;
import com.chatovich.infohandling.interpreter.AbstractMathExpression;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Yultos_ on 05.11.2016
 */
public class TextParserTest {

    TextComponent component;
    ManipulateComponent manipulator;
    ParserAction parserAction;
    List<AbstractMathExpression> listExp;

    @Before
    public void init(){
        component = mock(TextComponent.class);
        when(component.toString()).thenReturn("asa");
        manipulator = new ManipulateComponent();
        parserAction = mock(ParserAction.class);
        listExp = new ArrayList<>();

    }

    @After
    public void clear(){
        component = null;

    }

    @Test
    public void checkFile (){
        File file = new File(System.getProperty("user.dir")+"/file/text.txt");
        boolean actual = file.exists();
        Assert.assertTrue("File doesn't exist", actual);
    }

    @Test
    public void calcSymbolQuantityTest(){
        int actual = manipulator.calcSymbolQuantity(component,'a');
        Assert.assertEquals("Wrong calculation of symbol quantity",2,actual);
    }

    @Test
    public void getPriorityTest(){
        when(parserAction.getPriority("+")).thenReturn(1);
        when(parserAction.getPriority("*")).thenReturn(2);
        when(parserAction.getPriority("")).thenReturn(0);
        parserAction.getPriority("+");

    }

//    @Test
//    public void defineOperationTest() throws WrongDataException {
//        when(parserAction.defineOperation("",listExp)).thenThrow(new WrongDataException("Wrong operation"));
//
//        try {
//            parserAction.defineOperation("", listExp);
//        } catch (WrongDataException e) {
//            System.err.println("No such operation");
//        }
//    }




}
