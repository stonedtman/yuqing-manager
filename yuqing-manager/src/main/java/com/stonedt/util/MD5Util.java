package com.stonedt.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {
  public static String getMD5(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      return (new BigInteger(1, md.digest())).toString(16);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static String MD5(String s) {
    char[] hexDigits = { 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F' };
    try {
      byte[] btInput = s.getBytes();
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xF];
        str[k++] = hexDigits[byte0 & 0xF];
      } 
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static void main(String[] args) {
    String s = getMD5("123456");
    System.out.println(s);
  }
}
