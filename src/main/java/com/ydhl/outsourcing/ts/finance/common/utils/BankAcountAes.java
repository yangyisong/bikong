package com.ydhl.outsourcing.ts.finance.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/3/3.
 */
public class BankAcountAes {
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
//  a0b891c2d563e4f7
    private static String sKey = "abcdef0123456789";
    private static String ivParameter = "0123456789abcdef";
    private static BankAcountAes instance = null;

    private BankAcountAes() {

    }

    public static BankAcountAes getInstance() {
        if (instance == null)
            instance = new BankAcountAes();
        return instance;
    }

    // 加密
    public static String encrypt(String sSrc){
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 此处使用BASE64做转码。
        return result;

    }

    // 解密
    public static String decrypt(String sSrc){
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<String> bigDecimalFormat(BigDecimal data){
        List<String> strs = new ArrayList<>();
        DecimalFormat df1 = new DecimalFormat("00000000.00");
        String bts = df1.format(data).replace(".","");
        Boolean f = true;
        for(int i=0;i<bts.length();i++){
           if(bts.charAt(i)=='0' && f){
               strs.add("a");
           }else{
               f = false;
               strs.add(bts.charAt(i)+"");
           }
        }
        return strs;
    }

    public static void main(String[] args){
        // 需要加密的字串
        String cSrc = "5110251989";
        System.out.println(cSrc + "  长度为" + cSrc.length());
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = BankAcountAes.getInstance().encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString + "长度为" + enString.length());

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = BankAcountAes.getInstance().decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");

        System.out.println(BankAcountAes.getInstance().encrypt("111222333"));

        List<String> strs = bigDecimalFormat(new BigDecimal(1010.2));
        for(String s : strs){
            System.out.println(s);
        }
    }
}
