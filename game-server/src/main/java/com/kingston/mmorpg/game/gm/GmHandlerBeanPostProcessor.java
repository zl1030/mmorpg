package com.kingston.mmorpg.game.gm;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;

@Component
public class GmHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

	@Autowired
	private GmDispatcher gmDispatcher;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		try {
			Class<?> clz = bean.getClass();
			Method[] methods = clz.getDeclaredMethods();
			for (Method method : methods) {
				GmHandler mapperAnnotation = method.getAnnotation(GmHandler.class);
				if (mapperAnnotation != null) {
					CmdExecutor cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), bean);
					gmDispatcher.registerHandler(mapperAnnotation.cmd().name(), cmdExecutor);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
