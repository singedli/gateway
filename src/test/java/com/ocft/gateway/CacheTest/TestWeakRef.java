package com.ocft.gateway.CacheTest;

import com.ocft.gateway.cache.GatewayLocalCache;
import org.apache.lucene.util.RamUsageEstimator;
import org.aspectj.util.SoftHashMap;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lijiaxing
 * @Title: TestWeakRef
 * @ProjectName gateway
 * @date 2019/12/5下午6:20
 * @Description:
 *
 * -Xmx2000m  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:NewSize=1000m -XX:MaxNewSize=1000m
 */
public class TestWeakRef {
    public static void main(String[] args) throws InterruptedException {
        GatewayLocalCache cache = new GatewayLocalCache(RamUsageEstimator.ONE_MB*1);
        int i = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        System.out.println(RamUsageEstimator.humanSizeOf(simpleDateFormat));
        while (i++<1500) {
            cache.put(UUID.randomUUID().toString(), new SimpleDateFormat());
            System.out.println(RamUsageEstimator.humanSizeOf(cache) + ",key num ->"+cache.cache.entrySet().size());
        }

        //System.out.println(cache.size());

//        TimeUnit.SECONDS.sleep(5);
//
//        System.out.println(RamUsageEstimator.humanSizeOf(cache));


        //System.out.println(cache.size());

        //System.out.println(cache);

//        String line = null;
//        Scanner sc = new Scanner(System.in);
//        //只要还有下一个
//        while (sc.hasNext()) {
//            //获取扫描器的下一个完整标记
//            line = sc.next();
//            //判断结束标记
//            if ("over".equals(line))
//                break;
//            System.out.println(line);
//        }
//        System.out.println(cache.size());
//        sc.close();
    }
}
