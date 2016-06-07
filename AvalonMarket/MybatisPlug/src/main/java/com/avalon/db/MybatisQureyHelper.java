package com.avalon.db;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public abstract class MybatisQureyHelper<T> {

	public List<T> executeQuery(SqlSessionFactory sqlSessionFactory)
	{
		SqlSession mybatisSqlSession = null;
		List<T> result = new ArrayList<>();
		try
		{
			mybatisSqlSession = sqlSessionFactory.openSession();
			result = executeQuery(mybatisSqlSession);
		} catch (Exception e)
		{

		} finally
		{
			if (mybatisSqlSession != null)
			{
				mybatisSqlSession.close();
			}
		}
		return result;
	}

	public abstract List<T> executeQuery(SqlSession mybatisSqlSession);

}