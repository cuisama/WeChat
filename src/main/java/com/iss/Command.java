package com.iss;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {
    public static String exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String commandStr = "curl --help";
        //String commandStr = "curl -F media=@C:\\Users\\yxcui\\Desktop\\aa.jpg \"https://api.weixin.qq.com/cgi-bin/media/upload?access_token=z3BEP9qS_4zlOdc0v0M27ZEluYqqYeuBrPejLqLnEnbNt5c-7-gBuN4ltm0OvSKDywG3gjN0ZQI5DZ0Nmkt6OlTmViq8-QkgDblcfkEMMKXtOiOOCaGE1XbVe3ceR012WOBfAHAMER&type=image\"";
        //String commandStr = "ipconfig";
        Command.exeCmd(commandStr);
    }
}