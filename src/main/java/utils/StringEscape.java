package utils;

import org.apache.commons.lang.StringEscapeUtils;

public class StringEscape {
    public static void main(String[] args) {
        String a = "https:\\/\\/redpack-oss.tltgame.com\\/beijuxiong\\/6.jpeg?9343";
        System.out.println(StringEscapeUtils.unescapeJava(a));
    }
}
