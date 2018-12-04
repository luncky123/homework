package com.westos.stream;

// 1. 找到武将中武力前三的hero对象
// 2. 按出生地分组
// 3. 找出寿命前三的武将
// 4. 女性寿命最高的
// 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
// 6. 按各个年龄段分组： 0~20, 2140, 41~60, 60以上
// 7. 按武力段分组： >=90， 80~89, 70~79, <70
// 8. 按出生地分组后，统计各组人数

//采用传统的集合操作和流操作
import java.io.*;
import java.util.*;

//采用传统方法，不采用Stream api
public class HeroTest1 {
    public static void main(String[] args) {
        //根据路径得到一个File对象
        try {
            FileInputStream in=new FileInputStream("heroes.txt");
            InputStreamReader isr=new InputStreamReader(in);
            BufferedReader br=new BufferedReader(isr);
            //把文件读出来换为Hero对象存入list集合中
            List<Hero>list=new ArrayList<>();
            String line=null;
            StringBuffer strings=new StringBuffer();
            while((line=br.readLine())!=null){
                String[] s= line.split("\t");
                Hero hero = new Hero(Integer.parseInt(s[0]), s[1], s[2], s[3], Integer.parseInt(s[4]),
                        Integer.parseInt(s[5]), Integer.parseInt(s[6]));
                list.add(hero);
            }
            System.out.println(list);


            // 1. 找到武将中武力前三的hero对象
            method1(list);

            // 2. 按出生地分组
            //说明，出生地太多了，找了几个代表性的，逻辑是对的
            method2(list);

            // 3. 找出寿命前三的武将
            method3(list);

            // 4. 女性寿命最高的
            method4(list);

            // 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
            method5(list);

            // 6. 按各个年龄段分组： 0~20, 2140, 41~60, 60以上
            method6(list);

            // 7. 按武力段分组： >=90， 80~89, 70~79, <70
            method7(list);

            // 8. 按出生地分组后，统计各组人数
            //说明，出生地太多了，找了几个代表性的
            method8(list);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void method8(List<Hero> list) {
        Map<String,List<Hero>> map1=new HashMap<>();
        List<Hero> heroList1;
        for(Hero h:list) {
            if (h.getLoc().equals("云南")) {
                heroList1 = map1.get(h.getLoc());
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1 = new ArrayList<>();
                    map1.put(h.getLoc(), heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else if (h.getLoc().equals("江陵")) {
                heroList1 = map1.get(h.getLoc());
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1 = new ArrayList<>();
                    map1.put(h.getLoc(), heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            }
        }
        // System.out.println(map1);
        //统计人数
        Set<String> keySet = map1.keySet();
        for(String key:keySet){
            System.out.println(key+"人数为："+map1.get(key).size());
        }

    }

    private static void method7(List<Hero> list) {
        Map<String,List<Hero>> map=new HashMap<>();
        for(Hero h:list){
            String string = null;
            int power = h.getPower() - h.getPower();
            if (power >=90) {
                string = ">=90";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else if (power >= 80 && power <= 89) {
                string= "21~40";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else if (power >= 70 && power <= 79) {
                string = "41~60";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else {
                string = "<70";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            }
        }
        System.out.println(map);

    }

    private static void method6(List<Hero> list) {
        Map<String,List<Hero>> map=new HashMap<>();
        for(Hero h:list){
            String string = null;
            int age = h.getDeath() - h.getBirth();
            if (age >= 0 && age <= 20) {
                string = "0~20";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else if (age >= 21 && age <= 40) {
                string= "21~40";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else if (age >= 41 && age <= 60) {
                string = "41~60";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            } else {
                string = "60以上";
                List<Hero> heroList1 = map.get(string);
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList1 == null) {
                    heroList1= new ArrayList<>();
                    map.put(string, heroList1);//Map集合中加入键，值
                }
                heroList1.add(h);//Map集合中的值list集合中添加元素
            }
        }
        System.out.println(map);
    }

    private static void method5(List<Hero> list) {
        TreeSet<Hero> treeSet=new TreeSet<>((o1, o2) -> {
            int p= -(o1.getPower()-o2.getPower());
            int p2=p==0?o1.getId()-o2.getId():p;//有武力值一样的，需要用id来区分不是同一个对象
            return p2;
        });
        for(Hero h:list){
            treeSet.add(h);
        }
        //System.out.println(treeSet);
        //武力前三的hero对象存入list集合
        List<Hero> list2 = new ArrayList<>();
        int i=0;
        for(Hero h:treeSet){
            if(i<4){
                list2.add(h);
            }
            i++;
        }
        System.out.println(list2);

    }

    private static void method4(List<Hero> list) {
        TreeSet<Hero> treeSet=new TreeSet<>((o1, o2) -> {
            int p= -((o1.getDeath()-o1.getBirth())-(o2.getDeath()-o2.getBirth()));
            int p2=p==0?o1.getId()-o2.getId():p;//有寿命一样的，需要用id来区分不是同一个对象
            return p2;
        });
        for(Hero h:list){
            if(h.getSex().equals("女"))
                treeSet.add(h);
        }
        //System.out.println(treeSet);
        //寿命最高的女性对象存入list集合
        List<Hero> list2 = new ArrayList<>();
        int i=0;
        for(Hero h:treeSet){
            if(i<1){
                list2.add(h);
            }
            i++;
        }
        System.out.println(list2);
    }



    private static void method3(List<Hero> list) {
        TreeSet<Hero> treeSet=new TreeSet<>((o1, o2) -> {
            int p= -((o1.getDeath()-o1.getBirth())-(o2.getDeath()-o2.getBirth()));
            int p2=p==0?o1.getId()-o2.getId():p;//有寿命一样的，需要用id来区分不是同一个对象
            return p2;
        });
        for(Hero h:list){
            treeSet.add(h);
        }
        //System.out.println(treeSet);
        //寿命前三的hero对象存入list集合
        List<Hero> list2 = new ArrayList<>();
        int i=0;
        for(Hero h:treeSet){
            if(i<3){
                list2.add(h);
            }
            i++;
        }
        System.out.println(list2);
    }


    private static void method2(List<Hero> list) {
        //定义map 集合存储分组元素
        Map<String,List<Hero>> map=new HashMap<>();
        List<Hero> heroList;
        for(Hero h:list) {
            if (h.getLoc().equals("")) {
                heroList = map.get(h.getLoc());
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList == null) {
                    heroList = new ArrayList<>();
                    map.put(h.getLoc(), heroList);//Map集合中加入键，值
                }
                heroList.add(h);//Map集合中的值list集合中添加元素
            } else if (h.getLoc().equals("武陵")) {
                heroList = map.get(h.getLoc());
                //第一次没有这个分组存在时需要创建一个集合存储分组元素
                if (heroList == null) {
                    heroList = new ArrayList<>();
                    map.put(h.getLoc(), heroList);//Map集合中加入键，值
                }
                heroList.add(h);//Map集合中的值list集合中添加元素
            }
        }
        System.out.println(map);
    }

    private static void method1(List<Hero> list) {
        TreeSet<Hero> treeSet=new TreeSet<>((o1, o2) -> {
            int p= -(o1.getPower()-o2.getPower());
            int p2=p==0?o1.getId()-o2.getId():p;//有武力值一样的，需要用id来区分不是同一个对象
            return p2;
        });
        for(Hero h:list){
            treeSet.add(h);
        }
        //System.out.println(treeSet);
        //武力前三的hero对象存入list集合
        List<Hero> list2 = new ArrayList<>();
        int i=0;
        for(Hero h:treeSet){
            if(i<3){
                list2.add(h);
            }
            i++;
        }
        System.out.println(list2);
    }


}

