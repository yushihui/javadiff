package com.future.jdiff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Administrator on 9/19/2017.
 */
public class Test {

    public static void main(String args[]){
        testStream();
    }


    public static void testStream(){

        List<String> yi = List.of("abc","123", "456","abc", "123");
        Map<String,List<Integer>> mp = IntStream.range(0, yi.size()).boxed().collect(toMap(c -> yi.get(c), s-> {
            List<Integer> l= new ArrayList<>();
            l.add(s);
            return l;
        }, (a, b)-> {
            a.add(b.get(0));
            return a;
        }));

        Map<Integer,Integer> mm = new HashMap<>();
        mm.put(1,2);
        System.out.println(mm.get(2)+1);
    }
}
