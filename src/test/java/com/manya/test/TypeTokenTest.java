package com.manya.test;


import com.manya.pdc.base.array.StringArrayDataType;

import java.nio.charset.Charset;
import java.util.Arrays;

public class TypeTokenTest {

    public static void main(String[] args) {

        StringArrayDataType dataType = new StringArrayDataType(Charset.defaultCharset());

        String[] s = {"first", "second", "third"};

        byte[] bytes = dataType.toPrimitive(s, null);

        System.out.println(Arrays.toString(dataType.fromPrimitive(bytes, null)));
    }











}






