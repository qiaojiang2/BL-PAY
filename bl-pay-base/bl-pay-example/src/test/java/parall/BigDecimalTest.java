package parall;

import java.math.BigDecimal;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/7/15
 * @time 11:13
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal feeMoney = new BigDecimal(0);
        String feeAmount = "1.11_2.22_3.33_4.44";
        String[] feeAmo = feeAmount.split("_");
        BigDecimal b1 = new BigDecimal(feeAmo[0]);
        BigDecimal b2 = new BigDecimal(feeAmo[1]);
        BigDecimal b3 = new BigDecimal(feeAmo[2]);
        BigDecimal b4 = new BigDecimal(feeAmo[3]);
        BigDecimal b5 = feeMoney.add(b1).add(b2).add(b3).add(b4);
        System.out.println("" + b5);

        BigDecimal baqq = new BigDecimal(0);
        for (String str : feeAmo) {
            BigDecimal ba = new BigDecimal(str);
            //System.out.println(baqq.add(ba));
            //baqq.add(ba);
            baqq=baqq.add(ba);
            System.out.println(baqq);
        }
        System.out.println("" + baqq);
    }
}
