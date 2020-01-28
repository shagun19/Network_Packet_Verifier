package com.firewall.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

/**
 * Unit test for simple App.
 */
public class FirewallTest
{
    static Firewall firewall = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        firewall = new Firewall(System.getProperty("user.dir")+"/rulesTest.csv");
    }

    @Test
    public void testAcceptPacketCase1() {
        boolean acceptPacket = firewall.acceptPacket("inbound,tcp,42312,162.67.63.127");
        assertTrue(String.valueOf(true),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase2() {
        boolean acceptPacket = firewall.acceptPacket("inbound,tcp,42312,0.0.5.6");
        assertFalse(String.valueOf(false),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase3() {
        boolean acceptPacket = firewall.acceptPacket("outbound,tcp,20000,192.168.10.11");
        assertTrue(String.valueOf(true),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase4() {
        boolean acceptPacket = firewall.acceptPacket("inbound,tcp,64944,249.169.155.198");
        assertTrue(String.valueOf(true),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase5() {
        boolean acceptPacket = firewall.acceptPacket("inbound,udp,44444,237.188.6.199");
        assertFalse(String.valueOf(false),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase6() {
        boolean acceptPacket = firewall.acceptPacket("outbound,tcp,15900,200.255.255.255");
        assertTrue(String.valueOf(true),acceptPacket);
    }

    @Test
    public void testAcceptPacketCase7() {
        boolean acceptPacket = firewall.acceptPacket("outbound,udp,10,85.27.56.24");
        assertFalse(String.valueOf(false),acceptPacket);
    }

}
