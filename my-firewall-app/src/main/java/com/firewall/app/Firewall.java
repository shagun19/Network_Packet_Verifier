package com.firewall.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Shagun Bhatia
 *
 */

public class Firewall {
    /**
     * Class variables: list of firewall rules and rule map as defined further in other methods
     */
    private List<String> firewallRules;
    private HashMap<String,List<String>> structuredRules;

    /**
     * Initialises list of firewall rules and rule map
     * @param fileName
     * @throws FileNotFoundException
     */
    public Firewall(String fileName) throws FileNotFoundException {
        this.firewallRules=getFirewallRules(fileName);
        this.structuredRules=generateRuleStructure();
    }

    /**
     * @param fileName
     * @return List of firewall rules
     * @throws FileNotFoundException
     */
    private List<String> getFirewallRules(String fileName) throws FileNotFoundException {
        List<String> firewallRules = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNext()){
            String temp = scanner.nextLine();
            firewallRules.add(temp);
        }
        return firewallRules;
    }

    /**
     * Generates the rule structured divided in 4 types:
     *      1. inbound,tcp
     *      2. inbound,udp
     *      3. outbound,tcp
     *      4. outbound,udp
     *      Example: {inbound.tcp=[13662-45323:147.17.193.16], outbound.tcp=[28307-37308:254.155.93.102, 20656:101.46.73
     *      .245], outbound.udp=[20949:90.2.78.201], inbound.udp=[20949-65535:0.0.0.0-90.2.78.201]}
     * @return Map with above keys and values as List of corresponding ports and ipAddresses
     */
    private HashMap<String,List<String>> generateRuleStructure(){
        HashMap<String,List<String>> ruleMap = new HashMap<>();
        for(String rule: firewallRules){
            String direction = rule.split(",")[0];
            String protocol = rule.split(",")[1];
            String port = rule.split(",")[2];
            String ipAddress = rule.split(",")[3];
            String key = direction+"."+protocol;
            List<String> directionProtocolValue = ruleMap.getOrDefault(key,new ArrayList<>());
            directionProtocolValue.add(port+":"+ipAddress);
            ruleMap.put(key,directionProtocolValue);
        }
        return ruleMap;
    }

    /**
     * First checks whether the composite key given as direction.protocol is present in the rule Map.
     * For the respective ports and ip addresses performs their verification
     * @param query
     * @return
     */
    public boolean acceptPacket(String query){
        boolean result = false;
        String queryDirection = query.split(",")[0];
        String queryProtocol = query.split(",")[1];
        String queryPort = query.split(",")[2];
        String queryIpAddress = query.split(",")[3];
        String key = queryDirection+"."+queryProtocol;
        if(structuredRules.containsKey(key)){
            result=PortAndIPAddressVerification.portVerification(key,queryPort,queryIpAddress,structuredRules);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String ruleFile = System.getProperty("user.dir")+"/rules.csv";
        Firewall firewall = new Firewall(ruleFile);
        String inputFile = System.getProperty("user.dir")+"/input.csv";
        Scanner scanner = new Scanner(new File(inputFile));
        List<Boolean> queryResult = new ArrayList<>();
        while (scanner.hasNext()){
            String query = scanner.nextLine();
            boolean res = firewall.acceptPacket(query);
            queryResult.add(res);
        }
        System.out.println(queryResult);
    }
}
