package com.zookeeper.lyt.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Created by louyuting on 2017/3/19.
 */
public class ConfigWatcher implements Watcher{

    private ActiveKeyValueStore store;

    public ConfigWatcher(String hosts) throws Exception{
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void displayConfig() throws Exception{
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("read %s as %s \n", ConfigUpdater.PATH, value);
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDataChanged){
            try {
                displayConfig();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        ConfigWatcher watcher = new ConfigWatcher("123.206.13.151:2181");
        watcher.displayConfig();

        Thread.sleep(Long.MAX_VALUE);
    }
}
