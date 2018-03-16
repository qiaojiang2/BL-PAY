package java8.part1.chapter07;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description
 * @encoding UTF-8
 * @date 2017/10/17
 * @time 15:21
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {//处理当前字符
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null;  //返回空表示要解析的String已经足够小，可以顺序处理
        }
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {//将试探拆分位置设定为要解析的String的中间
            if (Character.isWhitespace(string.charAt(splitPos))) {//将拆分位置前进直到下一个空格
                //创建一个新的WordCounterSpliterator来解析String从开始到拆分位置的部分
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;//将这个WordCounterSpliterator的起始位置设置为拆分位置
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
