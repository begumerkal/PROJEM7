package com.wyd.dispatch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * 类 <code>TrustIpService</code>执行可信ip段检查
 * 
 * @since JDK 1.6
 */
public class TrustIpService {
	private int trustIps[][];
	private String type;
	private static final Logger log = Logger.getLogger(TrustIpService.class);

	/**
	 * 实例化TrustIpService，根据参数类型实例化对应可信ip段文件
	 * 
	 * @param type
	 * @throws Exception
	 */
	public TrustIpService(String type) throws Exception {
		this.type = type;
		if (type.equals("singlesocket")) {
			load("trustip_single.txt");
			log.info("load trustip_single.txt");
		} else {
			load("trustip.txt");
			log.info("load trustip.txt");
		}
	}

	/**
	 * 读取文件，取出可信ip段
	 * 
	 * @param fname
	 * @throws Exception
	 */
	private void load(String fname) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(Thread.currentThread().getContextClassLoader().getResource(fname)
				.getPath()));
		List<int[]> retList = new ArrayList<int[]>();
		while (true) {
			String line;
			if ((line = reader.readLine()) == null)
				break;
			String secs[] = line.split("-");
			if (secs.length == 2) {
				int arr[] = new int[2];
				arr[0] = strToIP(secs[0]);
				arr[1] = strToIP(secs[1]);
				retList.add(arr);
			}
		}
		reader.close();
		synchronized (this) {
			trustIps = new int[retList.size()][2];
			retList.toArray(trustIps);
		}
	}

	/**
	 * 重载可信ip文件
	 */
	public void reload() {
		try {
			if (type.equals("singelsocket"))
				load("trustip_single.txt");
			else
				load("trustip.txt");
		} catch (Exception ex) {
		}
	}

	/**
	 * 转换ip地址为int
	 * 
	 * @param s
	 * @return
	 */
	public int strToIP(String s) {
		String secs[] = s.split("\\.");
		return Integer.parseInt(secs[0]) << 24 & 0xff000000 | Integer.parseInt(secs[1]) << 16 & 0xff0000 | Integer.parseInt(secs[2]) << 8
				& 0xff00 | Integer.parseInt(secs[3]) & 0xff;
	}

	/**
	 * 转换ip地址为int
	 * 
	 * @param address
	 * @return
	 */
	public int addressToIP(InetSocketAddress address) {
		byte bytes[] = address.getAddress().getAddress();
		return (bytes[0] & 0xff) << 24 & 0xff000000 | (bytes[1] & 0xff) << 16 & 0xff0000 | (bytes[2] & 0xff) << 8 & 0xff00 | bytes[3]
				& 0xff;
	}

	/**
	 * 比较ip地址是否在可信ip段内
	 * 
	 * @param address
	 * @return
	 */
	public boolean isTrustIp(InetSocketAddress address) {
		long ip = addressToIP(address);
		synchronized (this) {
			for (int i = 0; i < this.trustIps.length; ++i) {
				long start = this.trustIps[i][0];
				long end = this.trustIps[i][1];
				if ((ip >= start) && (ip <= end)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 增加可信ip段
	 * 
	 * @param begin
	 * @param end
	 */
	public void addTrustIp(int begin, int end) {
		int newTrustIps[][] = new int[trustIps.length + 1][2];
		System.arraycopy(trustIps, 0, newTrustIps, 0, trustIps.length);
		newTrustIps[trustIps.length][0] = begin;
		newTrustIps[trustIps.length][1] = end;
		trustIps = newTrustIps;
	}
}