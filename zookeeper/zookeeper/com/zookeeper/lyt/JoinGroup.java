package com.zookeeper.lyt;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.concurrent.TimeUnit;

/**
 * Created by louyuting on 2017/3/18.
 * 加入组
 */
public class JoinGroup extends ConnectionWatcher {


    public void join(String groupName, String menberName) throws Exception{

        String path = "/" + groupName + "/" + menberName;

        String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println("created " + createdPath);
    }


    public static void main(String[] args) throws Exception {
        JoinGroup joinGroup = new JoinGroup();

        joinGroup.connect("123.206.13.151:2181");
        joinGroup.join("zoo", "cow");

        TimeUnit.MICROSECONDS.sleep(Long.MAX_VALUE);
    }


}






