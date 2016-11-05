package com.chatovich.infohandling.test;

import com.chatovich.infohandling.action.ManipulateComponent;
import com.chatovich.infohandling.entity.TextComponent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

import static org.mockito.Mockito.*;

/**
 * Created by Yultos_ on 05.11.2016
 */
public class TextParserTest {

    TextComponent component;
    ManipulateComponent manipulator;

    @Before
    public void init(){
        component = mock(TextComponent.class);
        when(component.toString()).thenReturn("asa");
        manipulator = new ManipulateComponent();

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

}
