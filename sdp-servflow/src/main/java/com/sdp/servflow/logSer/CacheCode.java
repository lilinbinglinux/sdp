package com.sdp.servflow.logSer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CacheCode {

    //缓存Map  
    private static Map<String,CacheContent> map = new ConcurrentHashMap<>();
    private static CacheCode CacheCode = new CacheCode();

    private CacheCode(){
    }

    public  String getCacheTest(String key) {
        CacheContent cc = map.get(key);

        if(null == cc) {
            return null;
        }

        long currentTime = System.currentTimeMillis();

        if(cc.getCacheMillis() > 0 && currentTime - cc.getCreateTime() > cc.getCacheMillis()) {
            //超过缓存过期时间,返回null  
            map.remove(key);
            return null;
        } else {
            return cc.getElement();
        }
    }

    public void setCacheTest(String key,int cacheMillis,String value) {
        long currentTime = System.currentTimeMillis();
        CacheContent cc = new CacheContent(cacheMillis,value,currentTime);
        map.put(key,cc);
    }

    public static CacheCode getInStance(){
        return CacheCode;
    }

//    @Getter
//    @Setter
//    @AllArgsConstructor
    class CacheContent{
        // 缓存生效时间  
        private  int cacheMillis;
        // 缓存对象  
        private String element;
        // 缓存创建时间  
        private long createTime ;
		public int getCacheMillis() {
			return cacheMillis;
		}
		public void setCacheMillis(int cacheMillis) {
			this.cacheMillis = cacheMillis;
		}
		public long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
		public String getElement() {
			return element;
		}
		public void setElement(String element) {
			this.element = element;
		}
		public CacheContent(int cacheMillis, String element, long createTime) {
			super();
			this.cacheMillis = cacheMillis;
			this.element = element;
			this.createTime = createTime;
		}
		
        

    }



} 