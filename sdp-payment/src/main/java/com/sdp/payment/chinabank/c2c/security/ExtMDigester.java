package com.sdp.payment.chinabank.c2c.security;

import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ExtMDigester
{
  private static MessageDigest md = null;

  private static MessageDigest getMessageDigest() {
    if (md == null) {
      try {
        md = MessageDigest.getInstance("MD5");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    md.reset();
    return md;
  }

  public static String getMD5(String str)
  {
    byte[] unencodedPassword = str.getBytes();

    MessageDigest md = getMessageDigest();

    md.update(unencodedPassword);

    byte[] encodedPassword = md.digest();

    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < encodedPassword.length; i++) {
      if ((encodedPassword[i] & 0xFF) < 16) {
        buf.append("0");
      }

      buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
    }

    return buf.toString();
  }

  public static String encodeBase64(String str)
  {
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encodeBuffer(str.getBytes()).trim();
  }

  public static String decodeBase64(String str)
  {
    BASE64Decoder dec = new BASE64Decoder();
    try {
      return new String(dec.decodeBuffer(str)); } catch (IOException io) {
    	  throw new RuntimeException(io.getMessage(), io.getCause());
    }
  }

  public static void main(String[] args) {
    String x = "";
    x = x + "67158-73623-10064";
    System.out.println(getMD5("67158-73623-10064"));
    System.out.println(getMD5(x));
  }
}