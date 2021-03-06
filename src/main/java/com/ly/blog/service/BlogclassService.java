package  com.ly.blog.service;

import com.ly.blog.vo.Blogclass;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class BlogclassService extends IdEntityService<Blogclass> {

	public static String CACHE_NAME = "blogclass";
    public static String CACHE_COUNT_KEY = "blogclass_count";

    public List<Blogclass> queryCache(Cnd c,Page p)
    {
        List<Blogclass> list_blogclass = null;
        String cacheKey = "blogclass_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_blogclass = this.query(c, p);
            cache.put(new Element(cacheKey, list_blogclass));
        }else{
            list_blogclass = (List<Blogclass>)cache.get(cacheKey).getObjectValue();
        }
        return list_blogclass;
    }

    public int listCount(Cnd c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


