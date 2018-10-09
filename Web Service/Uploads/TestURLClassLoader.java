import java.lang.module.ModuleDescriptor;
import java.lang.reflect.Module;
import java.net.URL;
import java.net.URLClassLoader;

public class TestURLClassLoader {
	public static void main(String[] args) throws Exception {
		Module myModule = TestURLClassLoader.class.getModule();
		ModuleDescriptor md = myModule.getDescriptor();
		System.out.printf("myModule=%s, layer=%s, md=%s\n", myModule, myModule.getLayer(), md);
		if(md != null)
			System.out.printf("requires=%s, uses=%s\n",  md.requires(), md.uses());
		System.out.printf("myModule.classLoader=%s, parent=%s\n", myModule.getClassLoader(), myModule.getClassLoader().getParent());

		URL rootURL = TestURLClassLoader.class.getResource("/");
		URL jsqlJar = new URL(rootURL, "jsql.jar");
		System.out.printf("rootURL=%s\n", rootURL);
		URL path[] = {rootURL, jsqlJar};
		ClassLoader parent = null;
		//ClassLoader parent = ClassLoader.getPlatformClassLoader();
		URLClassLoader loader = new URLClassLoader(path, parent);
		Class<?> jsqlUserClass = loader.loadClass("JavaSqlUser");
		System.out.printf("Loaded class: %s, loader=%s\n", jsqlUserClass, jsqlUserClass.getClassLoader());
		Object jsqlUser = jsqlUserClass.getConstructor().newInstance();
		System.out.printf("Loaded instance: %s\n", jsqlUser);
		loader.close();
	}

}
