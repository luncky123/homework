package com.westons.pattern;

//定义被装饰类
public class Source implements Sourceable {
    @Override
    public void method(){
        System.out.println("the original method");
    }
}
