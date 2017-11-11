package java8.part1.chapter03;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author qiao <jiangqiao, jiangqiao@mobike.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/13
 * @time 20:02
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
     String process(BufferedReader br) throws IOException;
}
