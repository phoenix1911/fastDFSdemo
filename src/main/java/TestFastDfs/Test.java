package TestFastDfs;

/**
 * Created by Tjl on 2019/3/7 15:34.
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.out.println(this.getClass().getResource("/").getPath());
        System.out.println(this.getClass().getClassLoader().getResource(""));
        System.out.println(this.getClass().getClassLoader().getResource("").getPath()+"fdfs_client.conf");

    }
}
