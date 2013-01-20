package border.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
 * This class will be called by the spring on startup
 * it will hold the main appcontext
 * For this init to happen, you must configure your beans.xml. Iclude
 * <bean id="applicationContextProvider" class="packagenamehere.ApplicationContextProvider"></bean>
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ApplicationContext ctx = null;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		ApplicationContextProvider.ctx = ctx;
	}

	public static ApplicationContext getContext() {
		return ctx;
	}
}
