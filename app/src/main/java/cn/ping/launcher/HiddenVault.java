package cn.ping.launcher;
import android.content.*;import android.util.Base64;import java.security.*;import javax.crypto.*;import javax.crypto.spec.*;import java.util.*;
final class HiddenVault {
 static final int ROUNDS=120000;static String ticket;
 static boolean configured(Context c){return c.getSharedPreferences("vault",0).contains("hash");}
 static boolean valid(String pin){return pin!=null&&pin.matches("[0-9]{6,12}");}
 static byte[] derive(String pin,byte[] salt) throws Exception{PBEKeySpec spec=new PBEKeySpec(pin.toCharArray(),salt,ROUNDS,256);try{return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}finally{spec.clearPassword();}}
 static void set(Context c,String pin)throws Exception{if(!valid(pin))throw new IllegalArgumentException();byte[] salt=new byte[16];new SecureRandom().nextBytes(salt);byte[] hash=derive(pin,salt);if(!c.getSharedPreferences("vault",0).edit().putString("salt",Base64.encodeToString(salt,Base64.NO_WRAP)).putString("hash",Base64.encodeToString(hash,Base64.NO_WRAP)).putInt("length",pin.length()).putInt("fails",0).putLong("blocked",0).commit())throw new java.io.IOException("save");}
 static boolean verify(Context c,String pin)throws Exception{android.content.SharedPreferences p=c.getSharedPreferences("vault",0);return MessageDigest.isEqual(derive(pin,Base64.decode(p.getString("salt",""),Base64.NO_WRAP)),Base64.decode(p.getString("hash",""),Base64.NO_WRAP));}
 static synchronized String grant(){ticket=UUID.randomUUID().toString();return ticket;}
 static synchronized boolean consume(String token){boolean ok=token!=null&&token.equals(ticket);ticket=null;return ok;}
}
