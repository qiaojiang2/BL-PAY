package cn.jishuyuan.pay.base.common.utils;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description 加密数据转换工具类
 * @encoding UTF-8
 * @date 2017/6/29
 * @time 17:24
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class DataConvert {
    public DataConvert() {
    }

    /**
     * print Hex byteArray
     *
     * @param b byte[]
     */
    public static void PrintByteArray(byte[] b) {
        char[] hex =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                        'e', 'f'};

        for (int i = 0; i < b.length; i++) {

            if (i % 8 == 0)
                System.out.println("");
            System.out
                    .print("0x" + hex[(b[i] >> 4 & 0x0f)] + hex[b[i] & 0x0f] + "; ");


        }

    }

    public static String getBytePrintString(byte[] b) {
        char[] hex =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                        'E', 'F'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {

            sb.append("" + hex[(b[i] >> 4 & 0x0f)] + hex[b[i] & 0x0f]);


        }
        return sb.toString();
    }

    /**
     * This method convert byte array to String
     *
     * @param b b,int bLen is :b' availability length
     * @return String
     * @author sgc
     */
    public static String ByteArraytoHexString(byte[] b, int bLen) {
        int iLen = bLen;
        //每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = b[i];
            //把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            //小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * This method convert byte array to String
     *
     * @param b b,int bLen is :b' availability length
     * @return String
     * @author sgc
     */
    public static String ByteArraytoHexString(byte[] b) {
        int iLen = b.length;
        //每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = b[i];
            //把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            //小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Int (or) long to ByteArray
     *
     * @param number long
     * @return byte[]
     */
    public static byte[] LongtoByteArray(long number) {
        long temp = number;
        StringBuffer s = new StringBuffer(Long.toString(temp, 16));
        if (s.length() % 2 == 1) {
            return StringToBytes("0" + s.toString());
        } else {
            return StringToBytes(s.toString());
        }
    }

    /**
     * ByteArray to int or long
     *
     * @param b byte[]
     * @return int
     */
    public static int ByteArraytoInteger(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[i] > 0) {
                s = s + b[i];
            } else {
                s = s + 256 + b[i];
            }
            s = s * 256;
        }
        if (b[3] > 0) {
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }
        return s;
    }


    public static long ByteArraytoLong(byte[] b) {
        long l = 0;
        for (int i = 0; i < b.length; i++) {
            l += (long) (b[b.length - i - 1] & 0xff) << (8 * i);
        }
        return l;
    }

    public static long ByteArraytoLong(byte[] b, int start, int len) {
        long l = 0;
        for (int i = 0; i < len; i++) {
            l += (long) (b[start + len - i - 1] & 0xFF) << (8 * i);

        }
        return l;
    }

    /**
     * ByteArray to Double
     *
     * @param b byte[]
     * @return double
     */
    public static double ByteArraytoDouble(byte[] b) {
        long l = 0;
        Double D = new Double(0.0);
        l = b[0];
        l = ((long) b[1] << 8);
        l = ((long) b[2] << 16);
        l = ((long) b[3] << 24);
        l = ((long) b[4] << 32);
        l = ((long) b[5] << 40);
        l = ((long) b[6] << 48);
        l = ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    public static final byte[] StringToBytes(String s) {
        int temp[] = new int[s.length()];
        byte b[] = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i++) {
            temp[i] = Integer.parseInt(String.valueOf(s.charAt(i)), 16);
        }
        int k = 0;
        for (int j = 0; j < s.length(); j += 2) {
            b[k] = (byte) (temp[j] * 16 + temp[j + 1]);
            k++;
        }
        return b;
    }

    /**
     * Integer和Long提供了toBinaryString, toHexString和toOctalString方 法，可以方便的将数据转换成二进制、十六进制和八进制字符串。功能更加强大的是其toString(int/long i, int radix)方法，可以将一个十进制数转换成任意进制的字符串形式。
     *
     * @param len
     * @param radix
     * @return
     */

    public static String getRandom(int len, int radix) {
        StringBuffer buf = new StringBuffer("1");
        for (int i = 0; i < len; i++) {
            buf.append("0");
        }
        int div = Integer.parseInt(buf.toString());

        int value = (int) (Math.random() * div);

        if (radix == 10)
            return strPadding(value, len);
        else
            return hexStrPadding(Integer.toHexString(value), len);
    }

    /**
     * @param in
     * @param outlen
     * @return
     */
    public static final String strPadding(long in, int outlen) {
        String str = String.valueOf(in);
        int padlen = outlen - str.length();

        StringBuffer zeroBuf = new StringBuffer("");
        for (int i = 0; i < padlen; i++) {
            zeroBuf.append("0");
        }

        return zeroBuf.append(str).toString();
    }

    public static final String hexStrPadding(String in, int outlen) {
        int padlen = outlen - in.length();

        StringBuffer zeroBuf = new StringBuffer("");
        for (int i = 0; i < padlen; i++) {
            zeroBuf.append("0");
        }

        return zeroBuf.append(in).toString();
    }

    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    public static String decimal2Hex(String str) {
        return Integer.toHexString(Integer.parseInt(str));
    }

    public static byte[] LongtoByteArray(long number, int len) {
        long temp = number;
        StringBuffer s = new StringBuffer(Long.toString(temp, 16));
        StringBuffer tmp = new StringBuffer();
        if (s.length() % 2 == 1) {
            tmp.append("0" + s.toString());
        } else {
            tmp.append(s.toString());
        }
        while (tmp.length() < len * 2) {
            tmp.insert(0, "00");
        }
        return StringToBytes(tmp.toString());
    }
}
