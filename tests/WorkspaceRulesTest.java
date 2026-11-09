package cn.ping.launcher;
public class WorkspaceRulesTest {
 static void equal(int expected,int actual){if(expected!=actual)throw new AssertionError(expected+" != "+actual);}
 public static void main(String[] args){
 equal(1,WorkspaceRules.targetPage(0,4,1080,-200,0,24,3));
 equal(1,WorkspaceRules.targetPage(0,4,1080,-40,-1800,24,3));
 equal(0,WorkspaceRules.targetPage(0,4,1080,-40,-500,24,3));
 equal(0,WorkspaceRules.targetPage(0,4,1080,500,2000,24,3));
 equal(3,WorkspaceRules.targetPage(3,4,1080,-600,-2000,24,3));
 equal(1,WorkspaceRules.targetPage(2,4,1080,220,0,24,3));
 equal(0,WorkspaceRules.targetPage(0,1,1080,-600,-2000,24,3));
 equal(26,WorkspaceRules.dropSlot(1,20,4,3,4,27));
 equal(24,WorkspaceRules.dropSlot(1,20,1,0,4,27));
 System.out.println("PASS: 9 paging and drop boundary checks");
 }
}
