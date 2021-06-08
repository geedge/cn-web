package net.geedge.common.utils;

import java.io.IOException;
import java.io.StringWriter;

import cn.hutool.cache.impl.LFUCache;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {

	// 创建配置类
	static Configuration configuration = new Configuration(Configuration.getVersion());
	static LFUCache<Object,Object> templateCache = Tool.CacheUtil.newLFUCache(100);

	/**
	 * 获取模板
	 * @param templateStr
	 * @param templateKey
	 * @return
	 * @throws IOException
	 */
	public static Template stringToTemplate(String name, String templateContent) throws IOException {
		Template template = (Template)templateCache.get(name);
		if(template == null) {
			//创建模板加载器
	        StringTemplateLoader templateLoader = new StringTemplateLoader();
	        // 存入模板
	        templateLoader.putTemplate(name, templateContent);  //template  = 虚拟名称, 用来当作获取静态文件的key
	        //加载模板加载器
	        configuration.setTemplateLoader(templateLoader);
	        configuration.setNumberFormat("#");
	        //得到模板
	        template = configuration.getTemplate(name, "utf-8");
	        templateCache.put(name, template);
		}
		// 得到模板
		return template;
	}

	
    /**
     * 
     * @param name
     * @param templateContent
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(String name, String templateContent, Object model) throws IOException, TemplateException {
    	Template template = FreeMarkerUtil.stringToTemplate(name, templateContent);
    	return processTemplateIntoString(template, model);
    }
    
    /**
     * 
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
    	StringWriter result = new StringWriter();
		template.process(model, result);
		return result.toString();
    }
}
