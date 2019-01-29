package com.sdp.servflow.logSer.flowControl.busi;

public class CounterDemo {
	public long timeStamp = getNowTime();
	public int reqCount = 0;
	public final int limit = 3; // 时间窗口内最大请求数
	public final long interval = 0; // 时间窗口ms

	public boolean grant() {
		long now = getNowTime();
		if (now < timeStamp + interval) {
			// 在时间窗口内
			reqCount++;
			// 判断当前时间窗口内是否超过最大请求控制数
			return reqCount <= limit;
		} else {
			timeStamp = now;
			// 超时后重置
			reqCount = 1;
			return true;
		}
	}
	
	private long getNowTime() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) throws InterruptedException {
		CounterDemo ct=new CounterDemo();
		while(true){
			for(int i=0;i<4;i++){
				System.out.println("i : "+ct.grant());
			}
			Thread.sleep(1500);
			System.out.println("--------------");
		}
	}
}
