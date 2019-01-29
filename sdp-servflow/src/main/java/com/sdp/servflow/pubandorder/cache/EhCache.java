package com.sdp.servflow.pubandorder.cache;

/**
 * 
 * <p>Title:EhCache - EhCache</p>
 * 
 * <p>Description: EhCache基本操作工具类</p>
 * @author renpengyuan
 */
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCache {
	private  CacheManager cacheManager;  
	private  String cacheName="nodesCache";
	private static EhCache ehCache;
	private EhCache() {
		this.cacheManager = CacheManager.getInstance();
		if (this.cacheManager.getCache(this.cacheName) == null) {
			Cache cache = new Cache(this.cacheName, 5000, true,true, 0, 0);
			this.cacheManager.addCache(cache);
		}
	}
	public static EhCache getInstance() {
		if (ehCache == null) {
			synchronized(CacheManager.class){
				if(ehCache!=null){
					return ehCache;
				}
				ehCache = new EhCache();
			}
		}
		return ehCache;
	}
	public void put(String key, Object value) {
		Cache cache = this.cacheManager.getCache(this.cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	public Object get(String key) {
		Cache cache = this.cacheManager.getCache(this.cacheName);
		Element element = cache.get(key);
		return ((element == null) ? null : element.getObjectValue());
	}

	public Cache getCache(String cacheName) {
		return this.cacheManager.getCache(cacheName);
	}

	public void remove(String key) {
		Cache cache = this.cacheManager.getCache(this.cacheName);
		cache.remove(key);
	}
	public void removeAll(){
		Cache cache= this.cacheManager.getCache(this.cacheName);
		cache.removeAll();
	}
}
