package com.zookeeper.lyt.config;

import com.zookeeper.lyt.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * Created by louyuting on 2017/3/19.
 */
public class ActiveKeyValueStore extends ConnectionWatcher {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 将一个关键字及其值写入Zookeeper.
     * @param path
     * @param value
     * @throws Exception
     */
    public void write(String path, String value) throws Exception {
        //首先检测 znode是否存在
        Stat stat = zk.exists(path, false);

        if(stat == null){
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }



    public String read(String path, Watcher watcher) throws Exception{

        byte[] data = zk.getData(path, watcher, null);
        return new String(data, CHARSET);
    }
}
