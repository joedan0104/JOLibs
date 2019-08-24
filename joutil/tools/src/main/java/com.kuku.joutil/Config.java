package net.hmzs.tools;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/23 15:49
 * <p/>
 * Description: 配置信息
 */
@SuppressWarnings("unused")
public class Config {
    /** 项目根目录路径 */
    public static Observable<String>  ROOT_PATH = new Observable<>(null);
    /** 是否是debug模式 */
    public static Observable<Boolean> DEBUG     = new Observable<>(true);
}
