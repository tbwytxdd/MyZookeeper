
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;


public class ZookeeperDemo {

    private static Logger logger = Logger.getLogger(ZookeeperDemo.class);

    @Setter@Getter
    private ZooKeeper zooKeeper;

    private static final String CONNECT_STRING = "192.168.16.128:2181";
    private static final int SESSION_TIMEOUT = 20*1000;
    public static final String PATH = "/atguigu01";

    public ZooKeeper startZK() throws Exception {
        //String connectString, int sessionTimeout, org.apache.ZookeeperDemo.Watcher watcher
        return new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void stopSK(ZooKeeper zooKeeper) throws InterruptedException {
        if (null != zooKeeper){
            zooKeeper.close();
        }
    }


    // path:路径,
    // data.getBytes()：value值,
    // ZooDefs.Ids.CREATOR_ALL_ACL：访问控制队列,
    // CreateMode.PERSISTENT：创建的znode的节点的类型
    public void createZnode(String path,String data) throws KeeperException, InterruptedException {
        zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    //zooKeeper.getData(path, false, new Stat());返回一个byte[]
    public String getZnode(String path) throws KeeperException, InterruptedException {
        String result = "";
        byte[] bytes = zooKeeper.getData(path, false, new Stat());
        result = new String(bytes);
        return result;
    }


    //修改、删除-都是要传递version的,如果传递-1，相当于不理会版本
    // (if the given version is -1, it matches any node's versions).
    public void setZnode(String path,String data) throws KeeperException, InterruptedException {
        zooKeeper.setData(path,data.getBytes(),-1);

    }


    public void deleteZnode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path,-1);
    }


    public static void main(String[] args) throws Exception {
        ZookeeperDemo zookeeperDemo = new ZookeeperDemo();
        ZooKeeper zooKeeper = zookeeperDemo.startZK();
        zookeeperDemo.setZooKeeper(zooKeeper);
        zookeeperDemo.setZnode("/atguigu","娟娟丫头");
        System.out.println(zookeeperDemo.getZnode("/atguigu"));
        zooKeeper.close();
        /*Stat stat = zooKeeper.exists("/atguigu/zsh", false);
        if(null == stat){
            //将启动后得到的zookeeper设置给zookeeperDemo的Zookeeper
            zookeeperDemo.setZooKeeper(zooKeeper);
            //zookeeperDemo.createZnode(PATH,"juanjuan");
            zookeeperDemo.createZnode("/atguigu/zsh","zhangshaohu");
            System.out.println(zookeeperDemo.getZnode("/atguigu/zsh"));
        }else{
            System.out.println("this znode is already exists");
        }*/
    }



}
