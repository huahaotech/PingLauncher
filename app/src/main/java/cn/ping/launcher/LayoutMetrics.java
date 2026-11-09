package cn.ping.launcher;
final class LayoutMetrics {
 static int columns(float widthDp,int preferred,int icon){int fit=Math.max(2,Math.min(8,(int)((widthDp-24)/Math.max(68,icon+24))));return preferred==0?Math.max(2,Math.min(fit,(int)((widthDp-24)/82))):Math.min(preferred,fit);}
 static int rows(float heightDp,int icon,float fontScale){return Math.max(1,Math.min(9,(int)(heightDp/Math.max(76,icon+20*Math.max(1,fontScale)+14))));}
 static int dockSlots(float widthDp){return Math.max(2,Math.min(7,(int)((widthDp-16)/72)));}
 static int slot(int page,int capacity,int row,int col,int columns){return page*capacity+row*columns+col;}
}
