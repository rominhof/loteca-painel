package loteca.persistencia;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import loteca.persistencia.api.CartelaDAO;
import loteca.persistencia.api.EstatisticaDAO;
import loteca.persistencia.api.GrupoCartelaDAO;
import loteca.persistencia.api.LotecaDAO;
import loteca.persistencia.api.PalpiteDAO;
import loteca.persistencia.api.PartidaDAO;
import loteca.persistencia.api.TimeDAO;
import loteca.persistencia.api.UsuarioDAO;
import loteca.persistencia.dao.CartelaDAOImpl;
import loteca.persistencia.dao.EstatisticaDAOImpl;
import loteca.persistencia.dao.GrupoCartelaDAOImpl;
import loteca.persistencia.dao.LotecaDAOImpl;
import loteca.persistencia.dao.PalpiteDAOImpl;
import loteca.persistencia.dao.PartidaDAOImpl;
import loteca.persistencia.dao.TimeDAOImpl;
import loteca.persistencia.dao.UsuarioDAOImpl;

public class DAOFactory {

	private static Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

	static {
		JpaHelper helper = new JpaHelper("lotecaPU");

		map.put(CartelaDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { CartelaDAO.class },
				new JpaTransactionalInvocatioHandler(
						new CartelaDAOImpl(helper), helper)));

		map.put(GrupoCartelaDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { GrupoCartelaDAO.class },
				new JpaTransactionalInvocatioHandler(new GrupoCartelaDAOImpl(
						helper), helper)));

		map.put(LotecaDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { LotecaDAO.class },
				new JpaTransactionalInvocatioHandler(new LotecaDAOImpl(helper),
						helper)));

		map.put(PalpiteDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { PalpiteDAO.class },
				new JpaTransactionalInvocatioHandler(
						new PalpiteDAOImpl(helper), helper)));

		map.put(PartidaDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { PartidaDAO.class },
				new JpaTransactionalInvocatioHandler(
						new PartidaDAOImpl(helper), helper)));

		map.put(TimeDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { TimeDAO.class },
				new JpaTransactionalInvocatioHandler(new TimeDAOImpl(helper),
						helper)));

		map.put(UsuarioDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { UsuarioDAO.class },
				new JpaTransactionalInvocatioHandler(
						new UsuarioDAOImpl(helper), helper)));

		map.put(EstatisticaDAO.class, Proxy.newProxyInstance(DAOFactory.class
				.getClassLoader(), new Class<?>[] { EstatisticaDAO.class },
				new JpaTransactionalInvocatioHandler(new EstatisticaDAOImpl(
						helper), helper)));

	}

	@SuppressWarnings("unchecked")
	public static <T> T getRepository(Class<T> interfaceType) {
		return (T) map.get(interfaceType);
	}

}
