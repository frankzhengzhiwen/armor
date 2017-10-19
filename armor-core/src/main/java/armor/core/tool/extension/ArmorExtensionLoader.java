/**
 * Copyright (c) 2017, armor All Rights Reserved. 
 */  
 
package armor.core.tool.extension;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 静态SPI扩展接口注册器
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年8月14日
 */
public class ArmorExtensionLoader<T> {
	
	private static final String SERVICES_DIRECTORY = "META-INF/services/";
	
	private static final String ARMOR_DIRECTORY = "META-INF/armor/";
	
	private static final String ARMOR_INTERNAL_DIRECTORY = ARMOR_DIRECTORY + "internal/";
	
	private static final String[] REGISTER_DIRECTORIES = {SERVICES_DIRECTORY, ARMOR_DIRECTORY, ARMOR_INTERNAL_DIRECTORY};

	private static final ConcurrentMap<Class<?>, ArmorExtensionLoader<?>> EXTENSION_HOLDERS = new ConcurrentHashMap<>();
	
	// ==============================
	
	protected final Class<?> type;
	
	protected final ConcurrentMap<String, T> cachedInstances = new ConcurrentHashMap<>();
	
	private final List<T> armorInstances = new ArrayList<>();
	
	/**
	 * Creates a new instance of ArmorLoader
	 * @param type
	 */
	private ArmorExtensionLoader(Class<?> type) {
		if (type == null)
			throw new IllegalArgumentException("Extension type == null");
		if(!type.isInterface()) {
			throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
		}
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ArmorExtensionLoader<T> getInstance(Class<T> type) {
		ArmorExtensionLoader<T> loader = (ArmorExtensionLoader<T>) EXTENSION_HOLDERS.get(type);
        if (loader == null) {
            EXTENSION_HOLDERS.putIfAbsent(type, new ArmorExtensionLoader<>(type));
            loader = (ArmorExtensionLoader<T>) EXTENSION_HOLDERS.get(type);
            loader.load(type.getName());
        }
        return loader;
	}
	
	public List<T> get() {
		return armorInstances;
	}
	
	public T get(String name) {
		return cachedInstances.get(name);
	}

	@SuppressWarnings("unchecked")
	private void load(String typeName) {
		for (String dir : REGISTER_DIRECTORIES) {
	        String filePath = dir + typeName;
	        try {
	            Enumeration<java.net.URL> urls;
	            ClassLoader classLoader = ArmorExtensionLoader.class.getClassLoader();
	            if (classLoader != null) {
	                urls = classLoader.getResources(filePath);
	            } else {
	                urls = ClassLoader.getSystemResources(filePath);
	            }
	            if (urls != null) {
	                while (urls.hasMoreElements()) {
	                    java.net.URL url = urls.nextElement();
	                    SpiReader.read(new File(url.toURI())).forEach((name, clazz)->{
	                    	if (! type.isAssignableFrom(clazz)) {
	    		        		throw new IllegalStateException("Error when load extension class(interface: " +
	    		        				type + "), class " + clazz.getName() + " is not subtype of " + type + ".");
	    		        	}
	    		        	if(cachedInstances.containsKey(name)) {
	    		        		throw new IllegalStateException("Error when load extension class(interface: " +
	    		        				type + "), class " + clazz.getName() + " has duplicated name: " + name + ".");
	    		        	}
	    		        	try {
	    						T instance = (T) clazz.newInstance();
	    						cachedInstances.put(name, instance);
	    						armorInstances.add(instance);
	    					} catch (InstantiationException | IllegalAccessException e) {
	    						throw new SpiLoadException("extension class initialize error: " + clazz.getName(), e);
	    					}
	                    });
	                }
	            }
	        } catch (Throwable t) {
	        	throw new SpiLoadException("Exception when load extension file: " + filePath + ".", t);
	        }
		}
	}

}
