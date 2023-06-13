package org.example;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {

    public static BlockingQueue<String> LISTWITH_A = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> LISTWITH_B = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> LISTWITH_C = new ArrayBlockingQueue<>(100);
    public static final  int TEXT_COUNT = 10000;

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < TEXT_COUNT; i++) {
                try {
                    LISTWITH_A.put(generateText("abc", 100_000));
                    LISTWITH_B.put(generateText("abc", 100_000));
                    LISTWITH_C.put(generateText("abc", 100_000));
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        Thread thread1 = new Thread(() -> {
            maxSize(LISTWITH_A, 'a');
       });
        Thread thread2 = new Thread(() -> {
            maxSize(LISTWITH_B, 'b');
        });
        Thread thread3 = new Thread(() -> {
            maxSize(LISTWITH_C, 'c');
        });
        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();

    }
    public static void maxSize(BlockingQueue<String> bq, char a) {
        int max = 0;
        for (int i = 0; i < TEXT_COUNT; i++) {
            int count = 0;
            String text;
            try {
                text = bq.take();
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == a) {
                        count++;
                    }
                }
                if (max < count) {
                    max = count;
                }
            } catch (InterruptedException e) {
                return;
            }

        }
        System.out.printf("Максимальное кол-во символов '%s' в строке %s\n",a,max);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}