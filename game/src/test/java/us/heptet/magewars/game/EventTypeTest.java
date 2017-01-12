package us.heptet.magewars.game;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/* Created by kay on 6/27/2014. */
/**
 *
 */
public class EventTypeTest {
    private static Logger logger = LoggerFactory.getLogger(EventTypeTest.class);
    @Test(groups = {"broken"})
    public void testName() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(256);
        EventType et = MouseEvent.ANY;
        FileOutputStream fileOutputStream = new FileOutputStream("c:\\users\\kay\\documents\\" + et.getName() + ".ser");
        ObjectOutput s = new ObjectOutputStream(fileOutputStream);
        s.writeObject(et);
        fileOutputStream.flush();
        fileOutputStream.close();
        //logger.info("{}", out.toString());

    }

    @Test(groups = {"broken"})
    public void testName2() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(256);
        EventType et1 = MouseEvent.ANY;
        EventType et;
        FileInputStream fileInputStream = new FileInputStream("c:\\users\\kay\\documents\\MOUSE.ser");
        ObjectInput s = new ObjectInputStream(fileInputStream);
        et = (EventType)s.readObject();
        logger.info("{}", et.toString());

    }
}
