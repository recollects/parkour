package com.alipay.parkour.python3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月27日 下午9:15
 */

public class ExecShell {

    public static void main(String[] args) throws Exception{
        String python3="/Users/yejiadong/dev/intelliJWorkspace/parkour/src/test/java/com/alipay/parkour/python3/ApScheduler.py";
        Process ps =Runtime.getRuntime().exec("python3 "+python3);

        ps.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        System.out.println(result);

    }

}
