package cn.ping.launcher;
import java.util.*;
/** Sparse, persistent workspace. Empty cells are intentional. */
final class GridModel {
 final TreeMap<Integer,String> cells=new TreeMap<>(); int columns=4,rows=5;
 int capacity(){return columns*rows;}
 int pages(){return cells.isEmpty()?1:cells.lastKey()/capacity()+1;}
 int find(String id){for(Map.Entry<Integer,String> e:cells.entrySet())if(e.getValue().equals(id))return e.getKey();return -1;}
 void reconcile(List<String> ids){reconcile(ids,Collections.emptySet());}
 void reconcile(List<String> ids,Set<String> excludedFromDesktop){
  HashSet<String> valid=new HashSet<>(ids),seen=new HashSet<>();
  cells.entrySet().removeIf(e->!valid.contains(e.getValue())||!seen.add(e.getValue()));
  // Reserved positions of hidden/Dock apps do not define the visible desktop tail.
  int next=0;for(Map.Entry<Integer,String> e:cells.entrySet())if(!excludedFromDesktop.contains(e.getValue()))next=Math.max(next,e.getKey()+1);
  for(String id:ids)if(!seen.contains(id)){while(cells.containsKey(next)&&!excludedFromDesktop.contains(cells.get(next)))next++;if(cells.containsKey(next)){String reserved=cells.remove(next);int free=cells.isEmpty()?next+1:Math.max(next+1,cells.lastKey()+1);while(cells.containsKey(free))free++;cells.put(free,reserved);}cells.put(next++,id);seen.add(id);}
 }
 void move(String id,int target){int source=find(id);if(source<0||target<0||source==target)return;String other=cells.remove(target);cells.remove(source);cells.put(target,id);if(other!=null)cells.put(source,other);}
 void resize(int cols,int newRows){if(cols==columns&&newRows==rows)return;int oldCap=capacity(),oldCols=columns;TreeMap<Integer,String> next=new TreeMap<>();ArrayList<Map.Entry<Integer,String>> overflow=new ArrayList<>();for(Map.Entry<Integer,String> e:cells.entrySet()){int page=e.getKey()/oldCap,local=e.getKey()%oldCap,row=local/oldCols,col=local%oldCols;if(row<newRows&&col<cols)next.put(page*cols*newRows+row*cols+col,e.getValue());else overflow.add(new AbstractMap.SimpleEntry<>(page,e.getValue()));}for(Map.Entry<Integer,String> e:overflow){int target=e.getKey()*cols*newRows;while(next.containsKey(target))target++;next.put(target,e.getValue());}columns=cols;rows=newRows;cells.clear();cells.putAll(next);}
}
