package cn.ping.launcher;
import java.util.*;
public class GridModelTest {
 static int checks=0;static void eq(Object a,Object b){checks++;if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}static void ok(boolean value){checks++;if(!value)throw new AssertionError();}
 public static void main(String[] args){
 GridModel m=new GridModel();m.reconcile(Arrays.asList("a","b","c"));m.move("a",19);eq(null,m.cells.get(0));eq("a",m.cells.get(19));eq(3,m.cells.size());m.move("a",1);eq("a",m.cells.get(1));eq("b",m.cells.get(19));m.move("c",47);eq(null,m.cells.get(2));eq("c",m.cells.get(47));eq(3,m.pages());
 TreeMap<Integer,String> saved=new TreeMap<>(m.cells);GridModel reopened=new GridModel();reopened.cells.putAll(saved);reopened.reconcile(Arrays.asList("a","b","c"));eq(saved,reopened.cells);
 m.reconcile(Arrays.asList("a","c","d"));eq(null,m.cells.get(19));eq("c",m.cells.get(47));eq("d",m.cells.get(48));m.move("missing",8);eq(3,m.cells.size());m.move("a",-2);eq("a",m.cells.get(1));
 m.resize(5,6);eq("a",m.cells.get(1));eq("c",m.cells.get(68));eq(3,m.cells.size());
 // New installs append after the visible tail, preserving all earlier holes.
 GridModel append=new GridModel();append.cells.put(2,"a");append.cells.put(19,"b");append.reconcile(Arrays.asList("new","a","b"));eq("new",append.cells.get(20));eq(2,append.pages());eq(null,append.cells.get(0));eq(null,append.cells.get(3));
 append.reconcile(Arrays.asList("a","b","new","next","last"));eq("next",append.cells.get(21));eq("last",append.cells.get(22));TreeMap<Integer,String> snapshot=new TreeMap<>(append.cells);append.reconcile(Arrays.asList("last","next","new","b","a"));eq(snapshot,append.cells);
 GridModel reserved=new GridModel();reserved.cells.put(0,"a");reserved.cells.put(40,"hidden");reserved.cells.put(1,"dock");reserved.reconcile(Arrays.asList("a","hidden","dock","new"),new HashSet<>(Arrays.asList("hidden","dock")));eq("new",reserved.cells.get(1));eq("hidden",reserved.cells.get(40));eq("dock",reserved.cells.get(41));
 GridModel empty=new GridModel();empty.reconcile(Arrays.asList("a","b"));eq("a",empty.cells.get(0));eq("b",empty.cells.get(1));
 // Random sparse moves and repeated responsive reflows must never lose or duplicate an app.
 ArrayList<String> ids=new ArrayList<>();for(int i=0;i<150;i++)ids.add("app"+i);m=new GridModel();m.reconcile(ids);Random random=new Random(17);for(int i=0;i<500;i++){m.move(ids.get(random.nextInt(ids.size())),random.nextInt(400));m.resize(2+random.nextInt(7),1+random.nextInt(9));eq(150,m.cells.size());eq(150,new HashSet<>(m.cells.values()).size());ok(m.cells.firstKey()>=0);}
 int[] widths={240,320,360,393,412,600,840,1200};for(int w:widths){int cols=LayoutMetrics.columns(w,0,50);ok(cols>=2&&cols<=8);ok((w-24)/cols>=68);int dock=LayoutMetrics.dockSlots(w-24);ok(dock>=2&&dock<=7);}eq(4,LayoutMetrics.columns(393,0,50));eq(5,LayoutMetrics.columns(480,0,50));eq(4,LayoutMetrics.dockSlots(369));eq(7,LayoutMetrics.dockSlots(816));eq(2,LayoutMetrics.rows(190,50,1));eq(1,LayoutMetrics.rows(120,56,1.5f));eq(39,LayoutMetrics.slot(1,20,4,3,4));
 System.out.println("PASS: "+checks+" sparse-grid, persistence, responsive-layout and no-loss checks");
 }
}
