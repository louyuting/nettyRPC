package com.zookeeper.lyt;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by louyuting on 2017/3/18.
 *
 * 列出组成员
 */
public class ListGroup extends ConnectionWatcher {


    public void list(String groupName) throws Exception{

        String path = "/" + groupName;

        try {
            List<String> children= zk.getChildren(path, false);

            if(children.isEmpty()){
                System.out.println( path + " is empty");
                System.exit(1);
            }else {
                for (String child : children){
                    System.out.println(child);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ListGroup listGroup = new ListGroup();

        listGroup.connect("123.206.13.151:2181");
        listGroup.list("zoo");

        listGroup.close();
    }


}






