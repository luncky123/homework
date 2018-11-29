package com.westons.pattern;

//装饰类
public class Decorator implements Sourceable {
    private  Sourceable source;//定义一个接口类型的成员变量

    //有参构造保证装饰对象持有被装饰对象的实例
    public Decorator(Sourceable source){
        super();
        this.source=source;

    }
    //给被装饰的对象增加功能，直接在装饰类中动态增加即可，还可以动态撤销
    @Override
    public void method(){
        System.out.println("Before decorator");
        source.method();
        System.out.println("After decorator");
        System.out.println("add new method");
    }
}
