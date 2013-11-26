package org.chinasb.example.qzonelogin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QQUtils {
    public static String charset = "UTF-8";
    public static String FUNCTION_PATTERN = "\\'(.+)\\'";

    public static String getCharset() {
        return charset;
    }
    
    public static String getPwd(String uin, String pwd, String verifyCode) throws Exception {
        String C = "";
        try {
            String H = MD5Utils.hexchar2bin(MD5Utils.md5(pwd));
            String F = MD5Utils.md5(H + MD5Utils.hexchar2bin(uin.replace("\\x", "").toUpperCase()));
            C = MD5Utils.md5(F + verifyCode.toUpperCase());
        } catch (Exception e) {
            throw new Exception("getPwd error:", e);
        }
        return C;
    }
    
    public static int getGTK(String skey){
        int hash = 5381;
        for(int i = 0, len = skey.length(); i < len; ++i){
            hash += (hash << 5) + skey.charAt(i);
        }
        return hash & 0x7fffffff;
    }
    
    public static String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    public static String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }
    
    public static String String2Unicode(String str) {
        StringBuffer ostr = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // Does the char need to be converted to unicode?
            if ((ch >= 0x0020) && (ch <= 0x007e))  {
                ostr.append(ch); 
            } else {
                ostr.append("\\u"); // standard unicode format.
                String hex = Integer.toHexString(str.charAt(i) & 0xFFFF); // Get hex value of the char. 
                for (int j = 0; j < 4 - hex.length(); j++) {
                    // Prepend zeros because unicode requires 4 digits
                    ostr.append("0");
                }
                ostr.append(hex.toLowerCase()); // standard unicode format.
                //ostr.append(hex.toLowerCase(Locale.ENGLISH));
            }
        }
        return (new String(ostr)); //Return the stringbuffer cast as a string.
    }
    
    public static String unicode2String(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
    }
}
