/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/4
 * @Version: 1.0
 */
public class SigleTon {
    private volatile static SigleTon sigleTon;

    private SigleTon(){}

    public static SigleTon getInstance(){
        if (sigleTon == null){
            synchronized (SigleTon.class){
                if (sigleTon==null){
                    sigleTon = new SigleTon();
                }
            }
        }
        return sigleTon;
    }
}
