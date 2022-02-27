package com.stonedt.util;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class MapUtil {
  public static String getUrlParamsByMap(Map<String, Object> map) {
    if (map == null)
      return ""; 
    StringBuffer sb = new StringBuffer();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      sb.append((String)entry.getKey() + "=" + entry.getValue());
      sb.append("&");
    } 
    String s = sb.toString();
    if (s.endsWith("&"))
      s = StringUtils.substringBeforeLast(s, "&"); 
    return s;
  }
}
