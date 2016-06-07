package ${MODEL_PACKAGE}.example;

import com.avalon.db.api.IMybatisCommonMapper;
import ${MODEL_PACKAGE}.${CLASS_NAME};
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ${CLASS_NAME}Mapper extends IMybatisCommonMapper {
   
    int countByExample(${CLASS_NAME}Example example);

    int deleteByExample(${CLASS_NAME}Example example);

    int deleteByPrimaryKey(${PK_TYPE} ${PK_NAME});

    int insert(${CLASS_NAME} record);

    int insertSelective(${CLASS_NAME} record);

    List<${CLASS_NAME}> selectByExample(${CLASS_NAME}Example example);

    ${CLASS_NAME} selectByPrimaryKey(${PK_TYPE}  ${PK_NAME});

    int updateByExampleSelective(@Param("record") ${CLASS_NAME} record, @Param("example") ${CLASS_NAME}Example example);

    int updateByExample(@Param("record") ${CLASS_NAME} record, @Param("example") ${CLASS_NAME}Example example);

    int updateByPrimaryKeySelective(${CLASS_NAME} record);

 	int updateByPrimaryKey(${CLASS_NAME} record);
}