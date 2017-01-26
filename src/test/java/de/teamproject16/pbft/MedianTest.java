package de.teamproject16.pbft;

import de.teamproject16.pbft.Messages.InitMessage;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on
 *
 * @author luckydonald
 * @since 31.10.2016
 **/
public class MedianTest {

    @Test
    public void testCalculateMedian() throws Exception {
        List<InitMessage> initStore = new LinkedList<>();
        InitMessage init1 = new InitMessage(1,1,0.4);
        InitMessage init2 = new InitMessage(1,2,0.3);
        InitMessage init3 = new InitMessage(1,3,0.3);
        InitMessage init4 = new InitMessage(1,4,0.5);
        initStore.add(init1);
        initStore.add(init2);
        initStore.add(init3);
        initStore.add(init4);
        assertEquals(0.3, Median.calculateMedian(initStore), 0);
    }

    @Test
    public void moreTestCalculateMedian() throws Exception {
        List<InitMessage> initStore = new LinkedList<>();
        InitMessage init1 = new InitMessage(1,1,0.9);
        InitMessage init2 = new InitMessage(1,2,0.4);
        InitMessage init3 = new InitMessage(1,3,0.2);
        InitMessage init4 = new InitMessage(1,4,0.0);
        initStore.add(init1);
        initStore.add(init2);
        initStore.add(init3);
        initStore.add(init4);
        assertEquals(0.2, Median.calculateMedian(initStore), 0);
    }

    @Test
    public void moarTestCalculateMedian() throws Exception {
        List<InitMessage> initStore = new LinkedList<>();
        InitMessage init1 = new InitMessage(1,1,1.8079927);
        InitMessage init2 = new InitMessage(1,2,7.1203556);
        InitMessage init3 = new InitMessage(1,3,6.654901);
        InitMessage init4 = new InitMessage(1,4,5.485434);
        initStore.add(init1);
        initStore.add(init2);
        initStore.add(init3);
        initStore.add(init4);
        assertEquals(5.485434, Median.calculateMedian(initStore), 0);
    }

    @Test
    public void evunMoarTestCalculateMedian() throws Exception {
        List<InitMessage> initStore = new LinkedList<>();
        InitMessage init1 = new InitMessage(1,1,0);
        InitMessage init2 = new InitMessage(1,2,0.4458);
        InitMessage init3 = new InitMessage(1,3,2);
        initStore.add(init1);
        initStore.add(init2);
        initStore.add(init3);
        assertEquals(0.4458, Median.calculateMedian(initStore), 0);
    }
}