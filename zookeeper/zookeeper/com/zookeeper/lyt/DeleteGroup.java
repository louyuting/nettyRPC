package com.zookeeper.lyt;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by louyuting on 2017/3/18.
 *
 * 列出组成员
 */
public class DeleteGroup extends ConnectionWatcher {


    public void delete(String groupName) throws Exception{

        String path = "/" + groupName;

        try {
            List<String> children= zk.getChildren(path, false);

            for (String child : children){
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            e.printStackTrace();
            System.out.println(groupName + " is not existed");
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        DeleteGroup deleteGroup = new DeleteGroup();

        deleteGroup.connect("123.206.13.151:2181");
        deleteGroup.delete("zoo");

        deleteGroup.close();
    }
}






