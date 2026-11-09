package cn.ping.launcher;
import java.util.*;
final class DockRules {
 static int count(int preferred,float widthDp){return preferred==0?LayoutMetrics.dockSlots(widthDp):Math.max(2,Math.min(7,preferred));}
 static int slot(float x,int left,int width,int count){return Math.max(0,Math.min(count-1,(int)((x-left)/Math.max(1f,(float)width/count))));}
 static ArrayList<String> place(List<String> current,String id,int slot,int count){ArrayList<String> result=new ArrayList<>(current);while(result.size()<count)result.add("");int source=result.indexOf(id);String replaced=result.get(slot);if(source==slot)return result;if(source>=0)result.set(source,replaced);result.set(slot,id);return result;}
}
