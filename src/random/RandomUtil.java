package random;

import java.util.Random;
public class RandomUtil{
    static Random random = new Random();
    public static double uniform(){
        double num = (Math.random() * 2) -1;
        return num;
    }
    public static <T> T choice(T[] list){
        int index = random.nextInt(list.length);
        return list[index];
    }

    public static int randint(int min, int max){
        return random.nextInt(max-min+1)+min;
    }

    public static double uniform1(double min, double max){
        double num = min + (Math.random() * (max-min));
        return num;
    }

}