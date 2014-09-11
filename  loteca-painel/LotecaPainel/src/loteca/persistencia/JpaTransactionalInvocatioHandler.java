package loteca.persistencia;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JpaTransactionalInvocatioHandler implements InvocationHandler {

	private Object instance;
	private JpaHelper jpaHelper;

	public JpaTransactionalInvocatioHandler(Object instance, JpaHelper jpaHelper) {
		this.instance = instance;
		this.jpaHelper = jpaHelper;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		boolean isTransactional = instance.getClass()
				.getMethod(method.getName(), method.getParameterTypes())
				.isAnnotationPresent(Transactional.class);
		try {
			if (isTransactional)
				jpaHelper.beginTransaction();
			result = method.invoke(instance, args);
			if (isTransactional)
				jpaHelper.commitTransaction();
			jpaHelper.closeEntityManager();
		} catch (IllegalArgumentException e) {
			if (isTransactional)
				jpaHelper.rollbackTransaction();
			throw e;
		} catch (IllegalAccessException e) {
			if (isTransactional)
				jpaHelper.rollbackTransaction();
			throw e;
		} catch (InvocationTargetException e) {
			if (isTransactional)
				jpaHelper.rollbackTransaction();
			throw e.getCause();
		}
		return result;
	}

}
