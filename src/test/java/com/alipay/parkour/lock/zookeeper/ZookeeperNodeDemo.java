package com.alipay.parkour.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午10:25
 */
public class ZookeeperNodeDemo {

    private static CuratorFramework client;

    private static final String connectStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        //
        // /meta/brokers/ids

//        client = CuratorFrameworkFactory.newClient(connectStr, new ExponentialBackoffRetry(1000, 10));
//
//        client.start();

//        String config="/meta/brokers/ids";
        String config="/meta/brokers";

        ZooKeeper zk = new ZooKeeper(config, 3000, new ZookeeperWatcher());

        Stat exists = zk.exists(config, false);

        if (exists == null) {
            System.out.println("不存在");
            // 如果根节点不存在，则创建根节点
            zk.create("/meta", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/meta/brokers", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/meta/brokers/ids", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

//        client = CuratorFrameworkFactory.newClient(connectStr, new ExponentialBackoffRetry(1000, 10));

//        InterProcessMutex lock = new InterProcessMutex(client, config);

//
//        client.create().withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE);
//        client.create().withMode(CreateMode.EPHEMERAL);
//        String forPath = client.create().forPath(config);
//
//        System.out.println(forPath);
    }
}
