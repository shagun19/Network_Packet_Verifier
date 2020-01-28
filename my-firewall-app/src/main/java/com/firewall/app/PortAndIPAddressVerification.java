package com.firewall.app;

import java.util.HashMap;
import java.util.List;

public class PortAndIPAddressVerification {

    /**
     * Verification whether the port equals the given port or belongs to the range of port in the rules.
     * @param key
     * @param queryPort
     * @param queryIpAddress
     * @return
     */
    public static boolean portVerification(String key, String queryPort, String queryIpAddress,
                                            HashMap<String,List<String>> structuredRules){
        boolean result = false;
        List<String> directionProtocolValues = structuredRules.get(key);
        /* Once direction and protocol matches then for all the ports check if the query port matches a single
        or a range of ports. As soon as port match, then corresponding IPs are matched in the below method*/
        for(String directionProtocolValue: directionProtocolValues){
            String port = directionProtocolValue.split(":")[0];
            String ipAddress = directionProtocolValue.split(":")[1];
            if(port.split("-").length==1){
                if(Integer.parseInt(queryPort)==Integer.parseInt(port)){
                    result = ipVerification(queryIpAddress,ipAddress);
                    if(result) break;
                }
            }
            else{
                int lower = Integer.parseInt(port.split("-")[0]);
                int higher = Integer.parseInt(port.split("-")[1]);
                if(Integer.parseInt(queryPort)>=lower && Integer.parseInt(queryPort)<=higher)
                    result = ipVerification(queryIpAddress,ipAddress);
                if(result) break;
            }
        }
        return result;
    }

    /**
     * Adds padding to ip octet to convert them into long. Ex: 44 --> 044; 4 --> 004
     * @param ip
     * @return
     */
    private static String addPaddingToIPOctet(String ip){
        if(ip.length()==1) return "00"+ip;
        else if(ip.length()==2) return "0"+ip;
        else return ip;
    }

    /** This verifies whether the ip matches the single ip or is within the ip address range
     * @param queryIpAddress
     * @param ipAddress
     * @return
     */
    private static boolean ipVerification(String queryIpAddress, String ipAddress){
        boolean result=true;
        // If IP address is a single IP address then compare them directly
        if(ipAddress.split("-").length==1){
            if (queryIpAddress.equals(ipAddress)) result= true;
            else result=false;
        }
        // Otherwise split it, and compare whether the query IP address falls within the range.
        if(ipAddress.split("-").length==2){
            String lower = "",upper="",query="";
            String[] ruleIpAddressLowerSplit  = ipAddress.split("-")[0].split("\\.");
            String[] ruleIpAddressUpperSplit  = ipAddress.split("-")[1].split("\\.");
            String[] queryIpAddressSplit = queryIpAddress.split("\\.");
            for(int i=0;i<queryIpAddressSplit.length;i++){
                lower+=addPaddingToIPOctet(ruleIpAddressLowerSplit[i]);
                upper+=addPaddingToIPOctet(ruleIpAddressUpperSplit[i]);
                query+=addPaddingToIPOctet(queryIpAddressSplit[i]);
            }
            result= Long.parseLong(query) >= Long.parseLong(lower) && Long.parseLong(query) <= Long.parseLong(upper);
        }
        return result;
    }
}
