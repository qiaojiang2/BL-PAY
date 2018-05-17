package parall;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/18
 * @time 9:54
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class ArrayReverseTest1 {
    public static void main(String[] args) {
        String[] array = {"a", "b", "c", "d", "e"};
        //使用集合ArrayList实现反转
        reverseArray1(array);//使用集合ArrayList实现反转
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + " ");
        }
        System.out.print("\n");

        String[] temp = reverseArray2(array);// 直接使用数组实现反转
        for (int j = 0; j < temp.length; j++) {
            System.out.print(array[j] + " ");
        }
        reverseArray3(array);
    }

    private static void reverseArray1(String[] array) {
        ArrayList<String> array_list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            array_list.add(array[array.length - i - 1]);
        }
        array = array_list.toArray(array);
    }

    private static String[] reverseArray2(String[] Array) {
        String[] new_array = new String[Array.length];
        for (int i = 0; i < Array.length; i++) {
            // 反转后数组的第一个元素等于源数组的最后一个元素：
            new_array[i] = Array[Array.length - i - 1];
        }
        return new_array;
    }

    private static void reverseArray3(String[] array) {
        ArrayList<String> array_list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            array_list.add(array[i]);
        }
        Collections.reverse(array_list);
        System.out.println("反转后排序: " + array_list);
    }
}

