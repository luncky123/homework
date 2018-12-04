package com.westos.stream;

// 1. 找到武将中武力前三的hero对象, 提示流也可以排序
// 2. 按出生地分组
// 3. 找出寿命前三的武将
// 4. 女性寿命最高的
// 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
// 6. 按各个年龄段分组： 0~20, 2140, 41~60, 60以上
// 7. 按武力段分组： >=90， 80~89, 70~79, <70
// 8. 按出生地分组后，统计各组人数
//采用Stream api
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HeroTest {
    public static void main(String[] args) throws IOException {
        //把文件转换为流
        Stream<String> lines = Files.lines(Paths.get("heroes.txt"), Charset.forName("utf-8"));
        //读取文件中的内容并存入到Hero类对象中
        Stream<String[]> stream = lines.map(string -> string.split("\t"));
        Stream<Hero> heroStream = stream.map(s -> new Hero(Integer.parseInt(s[0]), s[1], s[2], s[3], Integer.parseInt(s[4]),
                Integer.parseInt(s[5]), Integer.parseInt(s[6])));
        //把heroStream 流转为集合方便流的多次使用
        List<Hero> list = heroStream.collect(Collectors.toList());

        // 1. 找到武将中武力前三的hero对象, 提示流也可以排序
        Stream<Hero> limit = list.stream().sorted((h1, h2) -> {
            return -(h1.getPower() - h2.getPower());
        }).limit(3);
        List<Hero> collect = limit.collect(Collectors.toList());
        System.out.println(collect);

        // 2. 按出生地分组
        Map<String, List<Hero>> collect1 = list.stream().collect(Collectors.groupingBy(h -> h.getLoc()));
        System.out.println(collect1);

        // 3. 找出寿命前三的武将
        Stream<Hero> limit1 = list.stream().sorted((h1, h2) -> {
            return -((h1.getDeath() - h1.getBirth()) - (h2.getDeath() - h2.getBirth()));
        }).limit(3);
        List<Hero> collect2 = limit1.collect(Collectors.toList());
        System.out.println(collect2);

        // 4. 女性寿命最高的
        Stream<Hero> women = list.stream().filter(h -> h.getSex().equals("女")).sorted((h1, h2) -> {
            return -((h1.getDeath() - h1.getBirth()) - (h2.getDeath() - h2.getBirth()));
        }).limit(1);
        List<Hero> collect3 = women.collect(Collectors.toList());
        System.out.println(collect3);

        // 5. 找出武力排名前三(有武力相等的也算)  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
        Stream<Hero> sorted = list.stream().sorted((h1, h2) -> {
            return -(h1.getPower() - h2.getPower());
        }).limit(4);

        // 6. 按各个年龄段分组： 0~20, 21~40, 41~60, 60以上
        Map<String, List<Hero>> collect4 = list.stream().collect(Collectors.groupingBy(h -> {
            String s = null;
            int age = h.getDeath() - h.getBirth();
            if (age >= 0 && age <= 20) {
                s = "0~20";
            } else if (age >= 21 && age <= 40) {
                s = "21~40";
            } else if (age >= 41 && age <= 60) {
                s = "41~60";
            } else {
                s = "60以上";
            }
            return s;
        }));
        System.out.println(collect4);

        // 7. 按武力段分组： >=90， 80~89, 70~79, <70
        Map<String, List<Hero>> collect5 = list.stream().collect(Collectors.groupingBy(h -> {
            String s = null;
            int power = h.getPower();
            if (power < 70) {
                s = "<70";
            } else if (power >= 70 && power <= 79) {
                s = "70~79";
            } else if (power >= 80 && power <= 89) {
                s = "80~89";
            } else {
                s = ">=90";
            }
            return s;
        }));
        System.out.println(collect5);

        // 8. 按出生地分组后，统计各组人数
        Map<String, List<Hero>> collect6 = list.stream().collect(Collectors.groupingBy(h -> h.getLoc()));
        Set<String> keySet = collect6.keySet();
        for(String key:keySet){
            long count = collect6.get(key).stream().count();
            System.out.println("出生地："+key+"，出生的人数："+count);
        }

    }
}
