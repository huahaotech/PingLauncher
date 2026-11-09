package cn.ping.launcher;
import java.util.*;
public class DockRulesTest {
 static void eq(Object a,Object b){if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}
 public static void main(String[] args){for(int width:new int[]{240,320,393,480,600,840,1200})eq(4,DockRules.count(4,width));eq(5,DockRules.count(0,393));eq(4,DockRules.count(0,369));eq(0,DockRules.slot(-10,8,384,4));eq(1,DockRules.slot(110,8,384,4));eq(3,DockRules.slot(500,8,384,4));eq(Arrays.asList("a","x","c",""),DockRules.place(Arrays.asList("a","","c",""),"x",1,4));eq(Arrays.asList("a","x","c","d"),DockRules.place(Arrays.asList("a","b","c","d"),"x",1,4));eq(Arrays.asList("b","a","c","d"),DockRules.place(Arrays.asList("a","b","c","d"),"a",1,4));eq(Arrays.asList("a","b","c","d","e","f"),DockRules.place(Arrays.asList("a","b","c","d","e","f"),"a",0,4));System.out.println("PASS: 16 Dock count, drop location, add, replace, swap and hidden-slot checks");}
}
