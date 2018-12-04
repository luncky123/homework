package com.westos.reflect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestTreasure {
    public static void main(String[] args) throws ClassNotFoundException {
        // 类加载器，加载不在classpath下的类
        ClassLoader c1=new ClassLoader(){
            @Override
            protected Class<?>findClass(String name){
                try {
                    FileInputStream in = new FileInputStream("D:\\java_xikai\\JavaSE课件\\20181201-day20-JavaSE课件\\笔记\\Treasure.class");
                    byte[] bytes=new byte[1024*8];
                    int len = in.read(bytes);
                    //根据字节数组加载类
                    return defineClass(name,bytes,0,len);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;//报了异常，没有加载到，返回null
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;//报了异常，没有加载到，返回null
                }
            }
        };

        //获取类对象
        Class<?> clazz = c1.loadClass("com.westos.Treasure");//根据类加载器中找到类对象
        //获取类中的所有方法
        Method[] allMethods = clazz.getDeclaredMethods();
        for(Method m:allMethods){
            //找带标记注释的方法，即含有宝藏的方法
            Annotation[] dat = m.getDeclaredAnnotations();
            if (dat.length!=0){
                System.out.println(m);
            }
        }
    }
}
