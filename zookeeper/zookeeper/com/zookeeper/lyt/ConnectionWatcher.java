package com.zookeeper.lyt;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by louyuting on 2017/3/18.
 * 在 Zookeeper 中新建组 : /zoo
 */
public class ConnectionWatcher implements Watcher{

    private static final int SESSION_TIMEOUT = 5000;

    protected ZooKeeper zk;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(String hosts) throws Exception{
        //传入: 主机地址; 会话超时参数; Watcher对象,用于接收来自zookeeper的回调,获得各种事件的通知.
        //内部会创建一个线程连接到服务器;
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        //等待连接成功的响应.
        connectedSignal.await();
    }

    // override
    // 当客户端与服务端建立成功之后, watcher 的 process()方法会被调用. 参数是一个用于表示该连接的事件.
    public void process(WatchedEvent watchedEvent) {
        //返回响应, 表示连接已经成功
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }

    /**
     * 创建一个新的 Zookeeper 的 znode,
     * @param groupName
     * @throws Exception
     */
    public void create(String groupName) throws Exception{
        String path = "/" + groupName;

        String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("created " + createdPath);
    }

    public void close() throws Exception{
        zk.close();
    }
}



//