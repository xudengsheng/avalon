package com.example.db.example;

import com.avalon.db.api.IMybatisCommonMapper;
import com.example.db.Account;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper extends IMybatisCommonMapper {
   
    int countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Long pid);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Long  pid);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

 	int updateByPrimaryKey(Account record);
}