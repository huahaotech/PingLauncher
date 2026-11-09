package cn.ping.launcher;
final class WorkspaceRules {
 static int targetPage(int start,int count,int width,float distance,float speed,int slop,float density){
 int target=start;
 if(Math.abs(distance)>width*.18f||(Math.abs(speed)>420*density&&Math.abs(distance)>slop))target+=distance<0?1:-1;
 return Math.max(0,Math.min(Math.max(0,count-1),target));
 }
 static int dropSlot(int page,int capacity,int row,int column,int columns,int appCount){return Math.min(appCount-1,page*capacity+row*columns+column);}
}
