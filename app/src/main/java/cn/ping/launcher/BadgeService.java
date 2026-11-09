package cn.ping.launcher;
import android.app.*;import android.content.*;import android.os.*;import android.provider.Settings;import android.service.notification.*;import java.util.*;import java.util.concurrent.CopyOnWriteArraySet;
/** Keeps only active notification package names, never message contents or history. */
public class BadgeService extends NotificationListenerService {
 private static volatile Set<String> packages=Collections.emptySet();
 static final Set<Runnable> observers=new CopyOnWriteArraySet<>();
 static boolean has(String pkg){return packages.contains(pkg);}
 static boolean granted(Context c){String value=Settings.Secure.getString(c.getContentResolver(),"enabled_notification_listeners");ComponentName expected=new ComponentName(c,BadgeService.class);if(value!=null)for(String item:value.split(":"))if(expected.equals(ComponentName.unflattenFromString(item)))return true;return false;}
 private static void publish(Set<String> value){packages=Collections.unmodifiableSet(value);new Handler(Looper.getMainLooper()).post(()->{for(Runnable r:observers)r.run();});}
 public void onListenerConnected(){refresh();}
 public void onListenerDisconnected(){publish(new HashSet<>());}
 public void onNotificationPosted(StatusBarNotification n,RankingMap ranks){refresh();}
 public void onNotificationRemoved(StatusBarNotification n,RankingMap ranks){refresh();}
 public void onNotificationRankingUpdate(RankingMap ranks){refresh();}
 private void refresh(){HashSet<String> result=new HashSet<>();try{StatusBarNotification[] active=getActiveNotifications();RankingMap ranks=getCurrentRanking();if(active!=null)for(StatusBarNotification n:active){Notification notification=n.getNotification();if((notification.flags&(Notification.FLAG_ONGOING_EVENT|Notification.FLAG_GROUP_SUMMARY))!=0)continue;Ranking rank=new Ranking();if(ranks==null||!ranks.getRanking(n.getKey(),rank)||!rank.canShowBadge())continue;result.add(n.getPackageName());}}catch(RuntimeException ignored){}publish(result);}
 public void onDestroy(){publish(new HashSet<>());super.onDestroy();}
}
