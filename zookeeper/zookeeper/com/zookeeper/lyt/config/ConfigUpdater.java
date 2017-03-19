package com.zookeeper.lyt.config;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by louyuting on 2017/3/19.
 */
public class ConfigUpdater {

    public static final String PATH = "/config";

    private ActiveKeyValueStore store;
    private Random random = new Random();

    public ConfigUpdater(String hosts) throws Exception{
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws Exception{
        while (true){
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s \n", PATH, value);

            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws Exception {
        ConfigUpdater updater = new ConfigUpdater("123.206.13.151:2181");
        updater.run();

    }


}
