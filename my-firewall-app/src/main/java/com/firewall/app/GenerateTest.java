package com.firewall.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

/**
 * @author Shagun Bhatia
 */
public class GenerateTest {
    private static Random random = new Random();

    /**
     * returns randomly either inbound or outbound
     * @return
     */
    private static String getDirection(){
        int direction = random.nextInt(2);
        if(direction==1) return "inbound";
        else return "outbound";
    }

    /**
     * returns randomly either tcp or udp
     * @return
     */
    private static String getProtocol(){
        int protocol = random.nextInt(2);
        if(protocol==1) return "tcp";
        else return "udp";
    }

    /**
     * generates port number in the range [1-65535]. Also generation of single port or range of ports is random.
     * @return
     */
    private static String getPortNumber(boolean input){
        int range = random.nextInt(2);
        if(input) range=0;
        if(range==0){
            int port = random.nextInt(65535) + 1;
            return String.valueOf(port);
        }
        else{
            int portLower = random.nextInt(65535);
            int portHigher = random.nextInt(65535-portLower)+portLower;
            if(portLower==portHigher) return String.valueOf(portLower);
            else return portLower+"-"+ portHigher;
        }

    }

    /**
     * generates ip address in the range [0.0.0.0-255.255.255.255]. Also generation of single address or range of
     * addresses is random.
     * Generates IP of the form: 234.12.45.12, 12.34.233.198-198.162.12.13, 198.162.178.1 - 198.162.178.5
     * @return
     */
    private static String getIPAddress(boolean input){
        String ipAddress = "";
        int range = random.nextInt(2);
        if(input) range=0;
        if(range==0){
            for(int i=0;i<4;i++) if(i!=3) ipAddress+=random.nextInt(256)+".";
            else ipAddress+=random.nextInt(256);
            return ipAddress;
        }
        else{
            String ipAddressLower = "";
            String ipAddressHigher = "";
            int count = 0;
            for(int i=0;i<4;i++){
                int nextRange = random.nextInt(2);
                int lower = random.nextInt(256);
                int higher;
                if(nextRange==0 && count!=3) {
                    higher=lower;
                    ++count;
                }
                else higher = random.nextInt(256-lower)+lower;
                if(i!=3){
                    ipAddressLower+=lower+".";
                    ipAddressHigher+=higher+".";
                }
                else{
                    ipAddressLower+=lower;
                    ipAddressHigher+=higher;
                }
            }
            return ipAddressLower+"-"+ipAddressHigher;
        }
    }

    /**
     * Takes the number of rules to be generated from command line. Writes the given number of rules to rules.csv
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int testcases = Integer.parseInt(args[0]);
        String inputType = args[1];
        boolean input = false;
        Writer writer;
        if(inputType.equals("INPUT")) {
            input=true;
            writer = new FileWriter(System.getProperty("user.dir")+"/input.csv");
        }
        else{
            writer = new FileWriter(System.getProperty("user.dir")+"/rules.csv");
        }
        for(int i=0;i<testcases;i++) {
            String direction = getDirection();
            String protocol = getProtocol();
            String port = getPortNumber(input);
            String ipAddress = getIPAddress(input);
            String query = direction + "," + protocol + "," + port + "," + ipAddress;
            if(i==testcases-1) writer.write(query);
            else writer.write(query+"\n");
        }
        writer.flush();
        writer.close();
    }
}
