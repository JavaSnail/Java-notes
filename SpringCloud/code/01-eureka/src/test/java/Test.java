/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/4
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        SigleTon instance = SigleTon.getInstance();
        SigleTon instance1 = SigleTon.getInstance();
        SigleTon instance2 = SigleTon.getInstance();
        System.out.println(instance==instance1);
        System.out.println(instance==instance2);
        System.out.println(instance1==instance2);
    }
}
