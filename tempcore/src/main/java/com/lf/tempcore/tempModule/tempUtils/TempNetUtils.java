package com.lf.tempcore.tempModule.tempUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lf.tempcore.tempEnum.TempNetType;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class TempNetUtils {

	private static  final String LOGTAG = "NetUtils";

	/**
	 * 无网络连接
	 */
	public static int NONET_TYPE = 0;
	/**
	 * 标志可用网络类型
	 */
	private static int  AVAILABLE_NET_TYPE;
	public static TempNetType getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null) { // connected to the internet
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				return TempNetType.NET_WIFI;
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				return TempNetType.NET_MOBILE;
			}else {
				return TempNetType.NET_UNKNOWN;
			}
		} else {
			return TempNetType.NET_DISABLED;
		}
	}
	/**
	 *
	 * 网络连接检测，使用此函数前必须加入{android.permission.ACCESS_NETWORK_STATE}权限
	 * @return 返回true表示连接上
	 */
	protected static boolean checkNetwork(Context context){
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager == null){
			Log.w(LOGTAG, "checkNetwork()...获取ConnectivityManager对象失败");
			AVAILABLE_NET_TYPE = NONET_TYPE;
			return false;
		}else{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0){
				for (int i = 0; i < networkInfo.length; i++){
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
						int type = networkInfo[i].getType();
						switch(type){
							case ConnectivityManager.TYPE_WIFI:
								AVAILABLE_NET_TYPE = type;
								break;
							case ConnectivityManager.TYPE_MOBILE:
								//判断具体为哪种手机网络
								type = networkInfo[i].getSubtype();
								if(type == TelephonyManager.NETWORK_TYPE_EVDO_0 || type == TelephonyManager.NETWORK_TYPE_EVDO_A ||
										type == TelephonyManager.NETWORK_TYPE_EVDO_B ||
										type == TelephonyManager.NETWORK_TYPE_HSDPA || type == TelephonyManager.NETWORK_TYPE_UMTS){
									AVAILABLE_NET_TYPE = ConnectivityManager.TYPE_MOBILE;
								}else{
									AVAILABLE_NET_TYPE = NONET_TYPE;
								}
								break;
						}
						return true;
					}
				}
				return false;
			}else{
				Log.w(LOGTAG, "checkNetwork()...获取NetworkInfo对象失败");
				AVAILABLE_NET_TYPE = NONET_TYPE;
				return false;
			}
		}
	}

	/**
	 * 检测网络是否可用
	 *
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null) {
			Log.w(LOGTAG, "无法获得ConnectivityManager");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].isAvailable()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean checkNetState(Context context){
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++)
				{
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}

	public static boolean isNetworkRoaming(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			Log.w(LOGTAG, "couldn't get connectivity manager");
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null
					&& info.getType() == ConnectivityManager.TYPE_MOBILE) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm != null && tm.isNetworkRoaming()) {
					return true;
				} else {
				}
			} else {
			}
		}
		return false;
	}

	public static boolean isMobileDataEnable(Context context) throws Exception {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileDataEnable = false;

		isMobileDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		return isMobileDataEnable;
	}

	public static boolean isWifiDataEnable(Context context) throws Exception {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable = false;
		isWifiDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}
	/**
	 * 检测是否连接上网络</br>
	 * 该函数不能在子线程中使用
	 * @return
	 */
	public static boolean isNetConnected(Context context){
		if(checkNetwork(context)){
			return true;
		}else{
//			Toast.makeText(context, "无网络连接，请打开网络！", Toast.LENGTH_LONG).show();
			return false;
		}
	}

	/**
	 * 用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
	 *
	 * @return
	 * @author SHANHY
	 */
	public static String getPsdnIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						//if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * wifi 环境下ip
	 * @param context
	 * @return
     */
	public static String getWifiIp(Context context){
		//获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		//获取32位整型IP地址
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}
	//把整型地址转换成“*.*.*.*”地址
	public static String intToIp(int i) {

		return (i & 0xFF ) + "." +
				((i >> 8 ) & 0xFF) + "." +
				((i >> 16 ) & 0xFF) + "." +
				( i >> 24 & 0xFF) ;
	}

}
