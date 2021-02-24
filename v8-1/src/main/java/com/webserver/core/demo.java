package com.webserver.core;

public class demo {
    public static void main(String[] args) {
        String str=new String("abc");
        System.out.println(str);
        String str1=str;

        str1=str+1;
        System.out.println(str1);
        String str2="bcd";
        str1 =str+str2;

        System.out.println(str1);

    }
}
