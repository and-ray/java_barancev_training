package ru.belozerova.lesson2;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistanceEqual(){
        Point p1 = new Point(0,0);
        // менее информативный метод ассерт.
        assert p1.distanceTo(new Point(3,4))==5;
    }

    @Test
    public void testDistanceEqual2(){
        Point p1 = new Point(0,0);
        Assert.assertEquals(p1.distanceTo(new Point(-6,-8)),10.0);
    }

    @Test
    public void testDistanceNonEqual(){
        Point p1 = new Point(0,0);
        Assert.assertNotEquals(p1.distanceTo(new Point(-6,-8)),15.0);
    }

    @Test
    public void testDistanceTrue(){
        Point p1 = new Point(0,0);
        Assert.assertTrue(p1.distanceTo(new Point(-6,-8))==10.0);
    }

    @Test
    public void testDistanceNotNull(){
        Point p1 = new Point(0,0);
        Assert.assertNotNull(p1); //объект с нулевыми координатами, но не Null.
    }

    @Test
    public void testDistanceEqual3(){
        Point p1 = new Point(0,0);
        // менее информативный метод ассерт.
        assert p1.distanceTo(new Point(6,8))==10;
    }

}
