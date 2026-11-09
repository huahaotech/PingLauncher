package cn.ping.launcher;
import android.accessibilityservice.AccessibilityService;import android.view.accessibility.AccessibilityEvent;
public class DesktopGestureService extends AccessibilityService {
 static DesktopGestureService current;
 protected void onServiceConnected(){current=this;}
 public void onAccessibilityEvent(AccessibilityEvent event){}
 public void onInterrupt(){}
 public void onDestroy(){if(current==this)current=null;super.onDestroy();}
 static boolean openNotifications(){return current!=null&&current.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);}
}
