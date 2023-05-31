
package com.wwh.system.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public final class CTools {
    private static int bsnumCount = 0;
    private static int stringCount = 0;
    private static int numberCount = 1000;
    public static final int ID_LENGTH = 10;
    private static String bsCode = "00";

    public void setBsCode(String bsCode) {
        if (bsCode != null && bsCode.length() > 0) {
            bsCode = 100 + Integer.parseInt(bsCode) + "";
            bsCode = bsCode.substring(1);
        }

    }

    public CTools() {
    }
    /***************************************************************************
     * 复合数据转换方法集 1.List<String> 转化为'value1','value2','value3'
     **************************************************************************/
    /**
     * 将 List 集合中的字符转换为 'value1','value2','value3'...，用于 SQL 查询
     *
     * @param list java.util.List
     * @return String(Or null)
     */
    public static String listToString(List<String> list) {
        if (null != list && !list.isEmpty()) {
            StringBuffer sReturn = new StringBuffer();
            Iterator i$ = list.iterator();

            while (i$.hasNext()) {
                String str = (String) i$.next();
                sReturn.append("'").append(str).append("',");
            }

            return sReturn.substring(0, sReturn.length() - 1);
        } else {
            return null;
        }
    }

   /* public static String map2json(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = "";

        try {
            str = objectMapper.writeValueAsString(object);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return str;
    }*/

    public static String arrayJoin(Object[] array, String c) {
        if (null != array && array.length != 0) {
            String rstr = array[0].toString();

            for (int i = 1; i < array.length; ++i) {
                rstr = rstr + c + array[i];
            }

            return rstr;
        } else {
            return null;
        }
    }
    /***************************************************************************
     * XML转化方法集 1.XML转义符替换
     **************************************************************************/
    /**
     * 将 XML 的保留符号（<、>、&、'、"）替换成对应的转义字符
     *
     * @param str 输入字符串
     * @return 转义后的字符串
     */
    public static String toXMLStr(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 1) {
            return str;
        } else {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("'", "&apos;");
            str = str.replaceAll("\"", "&quot;");
            return str;
        }
    }
    /***************************************************************************
     * 加密解密方法集 1.MD5 加密
     **************************************************************************/
    /**
     * 取输入字符串的 MD5 摘要（字符串形式）
     *
     * @param str 输入字符串
     * @return String MD5 摘要（字符串形式）
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Digest(String str) throws NoSuchAlgorithmException {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] md = getMD5Digest(str.getBytes());
        int j = md.length;
        char[] strOut = new char[j * 2];
        int k = 0;

        for (int i = 0; i < j; ++i) {
            byte byte0 = md[i];
            strOut[k++] = hexDigits[byte0 >>> 4 & 15];
            strOut[k++] = hexDigits[byte0 & 15];
        }

        return new String(strOut);
    }

    /**
     * 取输入的字节的 MD5 摘要
     *
     * @param strBytes 输入的字节
     * @return byte[] MD5 摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getMD5Digest(byte[] strBytes) throws NoSuchAlgorithmException {
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strBytes);
        return mdTemp.digest();
    }

    /***************************************************************************
     * 数据库主键生成器 1. ORACLE
     **************************************************************************/
    /**
     * 生成 20 位的字符编号
     *
     * @return String
     */
    public static synchronized String getNewID() {
        int max = 9999;
        int p = 10000;
        if (stringCount >= max) {
            stringCount = 0;
        }
        int ci = p + stringCount;
        String rstr = (ci + "").substring(1);
        rstr = date2String(new Date(), "yyyyMMddHHmmssSSS") + rstr;
        ++stringCount;
        if (rstr.length() < 21) {
            rstr = rstr + "0";
        }
        return rstr;
    }

    public static synchronized String getBsnum() {
        return getBsnum(bsCode);
    }

    /**
     * 只用于生成业务流水号
     * 非业务流水号的主键　沿用原来的getNewID.
     *
     * @return
     */
    public static synchronized String getBsnum(String code) {
        if (bsnumCount >= 9999) {
            bsnumCount = 0;
        }

        int ci = 10000 + bsnumCount;
        String rstr = Integer.toString(ci).substring(1);
        rstr = date2String(new Date(), "yyMMddHHmmss") + rstr + code;

        ++bsnumCount;
        return rstr;
    }

    /**
     * 生成 15 位的数字编号
     *
     * @return String
     */
    public static synchronized long getNewNumID() {
        if (numberCount >= 999) {
            numberCount = 100;
        }

        StringBuffer sb = new StringBuffer(20);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        sb.append(format.format(new Date()));
        sb.append(numberCount++);
        return Long.parseLong(sb.toString());
    }

    /***************************************************************************
     * 生成随机数
     **************************************************************************/
    /**
     * 返回一个随机数
     *
     * @param i
     * @return System.out.println(getRandom ( 10)); 生成一个长度为10的随机字符串
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        String jj = "";

        for (int k = 0; k < i; ++k) {
            jj = jj + jjj.nextInt(9);
        }

        return jj;
    }

    /**
     * 获取当前时间的 String 类型
     *
     * @return 当前时间的 String 类型
     */
    public static String getCurrentTimeString() {
        try {
            return date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间的 Timestamp 类型
     *
     * @return 当前时间的 Timestamp 类型
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp((new Date()).getTime());
    }

    public static String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    public static String date2String(Date date, String format) {
        String rStr = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            rStr = sdf.format(date);
        }

        return rStr;
    }

    public static Date string2Date(String str) {
        return string2Date(str, "yyyy-MM-dd");
    }

    public static Date string2Date(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = sdf.parse(str);
        } catch (ParseException var5) {
            ;
        }

        return date;
    }

    /**
     * 将Map对象转成XML Map中可以包括List
     *
     * @param mObj
     * @return 返回Map 包括value和attr
     */
    public static Map map2Xml(Map mObj) {
        Map rMap = new HashMap();
        Iterator it = mObj.entrySet().iterator();
        String valueItem = "";
        String attrItem = "";

        while (true) {
            while (it.hasNext()) {
                Entry en = (Entry) it.next();
                String key = (String) en.getKey();
                Object value = en.getValue();
                if (value == null) {
                    valueItem = valueItem + "<" + key + "></" + key + ">";
                } else {
                    String vStr;
                    String v;
                    if (!(value instanceof List)) {
                        if (value instanceof Map) {
                            Map xMap = map2Xml((Map) value);
                            // String v = (String)xMap.get("value");
                            v = (String) xMap.get("attr");
                            valueItem = valueItem + "<" + key + v + ">" + v + "</" + key + ">";
                        } else if (value instanceof Date) {
                            vStr = date2String((Date) value, "yyyy-MM-dd HH:mm:ss");
                            if (key.indexOf("@") == 0) {
                                attrItem = attrItem + " " + key.replaceAll("@", "") + "=\"" + vStr + "\"";
                            } else {
                                valueItem = valueItem + "<" + key + "><![CDATA[" + vStr + "]]></" + key + ">";
                            }
                        } else if (key.indexOf("@") == 0) {
                            attrItem = attrItem + " " + key.replaceAll("@", "") + "=\"" + value + "\"";
                        } else {
                            vStr = objectValue(value);
                            valueItem = valueItem + "<" + key + ">" + vStr + "</" + key + ">";
                        }
                    } else {
                        vStr = "";
                        List tList = (List) value;
                        v = "";
                        String a = "";

                        for (int i = 0; i < tList.size(); ++i) {
                            Map tMap = new HashMap();
                            Object tobj = tList.get(i);
                            tMap.put(key, tobj);
                            Map xMap = map2Xml(tMap);
                            v = (String) xMap.get("value");
                            vStr = vStr + v;
                        }

                        valueItem = valueItem + vStr;
                    }
                }
            }

            rMap.put("value", valueItem);
            rMap.put("attr", attrItem);
            return rMap;
        }
    }

    public static String objectValue(Object obj) {
        Class rClass = obj.getClass();
        Field[] fields = rClass.getDeclaredFields();
        String rStr;
        if (fields.length <= 0) {
            rStr = "";
            if (obj != null) {
                rStr = obj.toString();
                if (rStr.indexOf("[CDATA[") >= 0) {
                    rStr = rStr.replaceAll("<\\!\\[CDATA\\[", "").replaceAll("]]>", "");
                }
            }

            return "<![CDATA[" + rStr + "]]>";
        } else {
            rStr = "";
            boolean hasGet = false;

            for (int i = 0; i < fields.length; ++i) {
                Field tf = fields[i];
                String name = tf.getName();
                String getMethod = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);

                try {
                    Method getM = rClass.getDeclaredMethod(getMethod);
                    Object tobj = getM.invoke(obj);
                    hasGet = true;
                    rStr = rStr + "<" + name + ">" + objectValue(tobj) + "</" + name + ">";
                } catch (Exception var11) {
                    ;
                }
            }

            if (!hasGet) {
                String tstr = "";
                if (obj != null) {
                    tstr = obj.toString();
                    if (tstr.indexOf("[CDATA[") >= 0) {
                        tstr = tstr.replaceAll("<\\!\\[CDATA\\[", "").replaceAll("]]>", "");
                    }
                }

                rStr = "<![CDATA[" + tstr + "]]>";
            }

            return rStr;
        }
    }

    /**
     * 生成guid，一般用于 主键值
     *
     * @return
     */
    public static String NewGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    /**
     * 根据传输的字符串日期"yyyy-MM-dd HH:mm:ss"转换成Date类型，如果传入null这返回系统当前时间
     *
     * @param cDate 需要转换的字符串日期格式 "yyyy-MM-dd HH:mm:ss"
     * @return 转换后的Date格式数据
     */
    public static Date getDate(String cDate) {
        Date hdate = new Date();
        if (cDate != null && cDate.length() > 0) {
            hdate = string2Date(cDate, "yyyy-MM-dd HH:mm:ss");
        }

        return hdate;
    }

    /**
     * 对象数据复制
     */
    public static boolean objCopy(Object from, Object to) {
        boolean rboolean = true;
        Class fClass = from.getClass();
        Class tClass = to.getClass();
        Method[] gms = fClass.getDeclaredMethods();
        Method[] arr$ = gms;
        int len$ = gms.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            String name = m.getName();
            if (name.indexOf("get") == 0) {
                String sname = "set" + name.substring(3);

                try {
                    Method sm = tClass.getDeclaredMethod(sname, m.getReturnType());
                    Object obj = m.invoke(from);
                    if (obj != null) {
                        sm.invoke(to, m.invoke(from));
                    }
                } catch (Exception var14) {
                    var14.printStackTrace();
                    rboolean = false;
                    return rboolean;
                }
            }
        }

        return rboolean;
    }

    public static String getMapValue(Object obj, String key) {
        if (obj == null) {
            return "";
        } else {
            Map pMap = (Map) obj;
            return (String) ((String) (pMap.get(key) == null ? "" : pMap.get(key)));
        }
    }

    /**
     * 此方法中产生的随机数的范围[min,max]
     *
     * @param min 随机数所在范围 最小
     * @param max 随机数所在范围 最大
     * @param n   需要产生的随机数的个数
     * @return
     */
    public static Integer[] randomArray(Integer min, Integer max, Integer n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        // 初始化给定范围的待选数组
        Integer[] source = new Integer[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        Integer[] result = new Integer[n];
        Random rd = new Random();
        for (Integer i = 0; i < result.length; i++) {
            int index = rd.nextInt(source.length);
            // 将随机到的数放入结果集
            result[i] = source[index];
            // 将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[source.length - 1];
            source = Arrays.copyOf(source, source.length - 1);
        }
        return result;
    }

    public static void main(String[] args) {
        Integer[] ints = randomArray(0, 1000, 4);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
        System.out.println(getNewID());

    }

}
