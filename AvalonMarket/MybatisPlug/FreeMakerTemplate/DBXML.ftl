<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${MODEL_PACKAGE}.example.${DB_UPPER_CLASS}Mapper">

  <resultMap id="BaseResultMap" type="${DB_ALL_NAME}">
  	<#list sequence as item>
  	<#if item.pk>
    <id column="${item.upperName}" jdbcType="${item.jdbcName}" property="${item.upperName}" />
    <#else>
    <result column="${item.upperName}" jdbcType="${item.jdbcName}" property="${item.upperName}" />
    </#if>
    </#list>
  </resultMap>
  
  <sql id="Example_Where_Clause">
    <where>
      <foreach collection="oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${"$"}{criterion.condition}
                </when>
                <when test="criterion.singleValue">
                  and ${"$"}{criterion.condition} ${"#"}{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${"$"}{criterion.condition} ${"#"}{criterion.value} and ${"#"}{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${"$"}{criterion.condition}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                    ${"#"}{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  
  <sql id="Update_By_Example_Where_Clause">
   <where>
      <foreach collection="example.oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${"$"}{criterion.condition}
                </when>
                <when test="criterion.singleValue">
                  and ${"$"}{criterion.condition} ${"#"}{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${"$"}{criterion.condition} ${"#"}{criterion.value} and ${"#"}{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${"$"}{criterion.condition}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                    ${"#"}{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  
  <sql id="Base_Column_List">
   	<#list sequence as item>
   	<#if item_index==0>
   	${item.lowerName}
   	<#else>
   	,${item.lowerName}
   	</#if>
    </#list>
  </sql>
  
  <select id="selectByExample" parameterType="${DB_ALL_EXAMPLE_NAME}" resultMap="BaseResultMap">
    select 
    <if test="distinct">
      distinct
    </if>
    <include refid="Base_Column_List" />
    from ${DB_CLASS}
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
    <if test="orderByClause != null">
      order by ${"$"}{orderByClause}
    </if>
  </select>
  
  <select id="selectByPrimaryKey" parameterType="${PK_TYPE}" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${DB_CLASS}
    where ${PK_NAME} = ${"#"}{${PK_NAME},jdbcType=${PK_JDBCTYPE}}
  </select>
  
  <delete id="deleteByPrimaryKey" parameterType="${PK_TYPE}">
    delete from ${DB_CLASS}
    where ${PK_NAME} = ${"#"}{${PK_NAME},jdbcType=${PK_JDBCTYPE}}
  </delete>
  
  <delete id="deleteByExample" parameterType="${DB_ALL_EXAMPLE_NAME}">
    delete from ${DB_CLASS}
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </delete>
  
  <insert id="insert" parameterType="${DB_CLASS}">

    insert into ${DB_CLASS}
    (
	<#list sequence as item>
   	<#if item_has_next>
   	${item.lowerName},
   	<#else>
   	${item.lowerName}
   	</#if>
    </#list>
	)
    values 
    (
    <#list sequence as item>
   	<#if item_has_next>
    ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}},
    <#else>
   	${"#"}{${item.lowerName},jdbcType=${item.jdbcName}}
   	</#if>
    </#list>
    )
  </insert>
  
  <insert id="insertSelective" parameterType="${DB_ALL_NAME}">
  
    insert into ${DB_CLASS}
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <#list sequence as item>
      <if test="${item.lowerName} != null">
        ${item.lowerName},
      </if>
      </#list>
    </trim>
    
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <#list sequence as item>
      <if test="${item.lowerName} != null">
        ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}},
      </if>
      </#list>
    </trim>
  </insert>
  
  <select id="countByExample" parameterType="${DB_ALL_EXAMPLE_NAME}" resultType="java.lang.Integer">
    select count(*) from ${DB_CLASS}
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </select>
  
  <update id="updateByExampleSelective" parameterType="map">
    update ${DB_CLASS}
    <set>
      <#list sequence as item>
      <if test="record.${item.lowerName} != null">
        ${item.lowerName} = ${"#"}{record.${item.lowerName},jdbcType=${item.jdbcName}},
      </if>
      </#list>
    </set>
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  
  <update id="updateByExample" parameterType="map">
    update ${DB_CLASS}
    set ${PK_NAME} = ${"#"}{record.${PK_NAME},jdbcType=${PK_JDBCTYPE}},
   	  <#list sequence as item>
   	  <#if !item.pk>
   	  <#if !item_has_next>
   	  ${item.lowerName} = ${"#"}{record.${item.lowerName},jdbcType=${item.jdbcName}}
   	  <#else>
   	  ${item.lowerName} = ${"#"}{record.${item.lowerName},jdbcType=${item.jdbcName}},
   	  </#if>
   	  </#if>
      </#list>
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  
  <update id="updateByPrimaryKeySelective" parameterType="${DB_ALL_NAME}">
    update ${DB_CLASS}
    <set>
    <#list sequence as item>
    <#if !item.pk>
     <if test="${item.lowerName} != null">
        ${item.lowerName} = ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}},
      </if>
    </#if>
    </#list>
    </set>
    where ${PK_NAME} = ${"#"}{${PK_NAME},jdbcType=${PK_JDBCTYPE}}
  </update>
  
  <update id="updateByPrimaryKey" parameterType="${DB_ALL_NAME}">
    update ${DB_CLASS}
    <#list sequence as item>
    <#if !item.pk>
    <#if item_index==1>
    set ${item.lowerName} = ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}},
    <#elseif item_has_next>
  		${item.lowerName} = ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}},
    <#else>
    	${item.lowerName} = ${"#"}{${item.lowerName},jdbcType=${item.jdbcName}}
    </#if>
    </#if>
    </#list>
    where  ${PK_NAME} = ${"#"}{${PK_NAME},jdbcType=${PK_JDBCTYPE}}
  </update>
</mapper>