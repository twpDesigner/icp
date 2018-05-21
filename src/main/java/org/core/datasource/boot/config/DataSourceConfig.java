package org.core.datasource.boot.config;

import org.core.datasource.boot.config.component.DynamicDataSource;
import org.core.datasource.boot.config.aop.DynamicDataSourceAspect;
import org.core.datasource.boot.config.container.DynamicDataSourceContextHolder;
import org.core.datasource.boot.config.property.DynamicDSPropertyConfig;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DynamicDSPropertyConfig.class)
@ImportAutoConfiguration({
        DynamicDataSourceAspect.class
})
//@PropertySources
//        ({
//                @PropertySource(value = "config/dynamicDS/application-dynamicDS.yml" )
//        })
public class DataSourceConfig{

    @Autowired
    DynamicDSPropertyConfig dynamicDSPropertyConfig;

    @Primary
    @Bean(name = "defaultDataSourceProperty")
    @ConfigurationProperties(prefix = "spring.datasource") // application.properteis中对应属性的前缀
    public DataSourceProperties defaultDataSourceProperty() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return defaultDataSourceProperty().initializeDataSourceBuilder().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> dynamicDSObject = dynamicDSPropertyConfig.getDynamicDSObject();

//        dynamicDSPropertyConfig.getDynamicDS().entrySet().stream().filter(
//                s->s.getValue().isDefaultDS()
//        ).findFirst().ifPresent(
//                s->dynamicDataSource.setDefaultTargetDataSource(dynamicDSObject.get(s.getKey()))
//        );
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource());
        dynamicDataSource.setTargetDataSources(dynamicDSObject);

        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dynamicDSObject.keySet());

        return dynamicDataSource;
    }

    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return the sql session factory bean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     *
     * @ConfigurationProperties(prefix = "mybatis") 不写，Reason: No converter found capable of converting from type [java.lang.String] to type [@org.springframework.boot.context.properties.ConfigurationProperties org.mybatis.spring.SqlSessionFactoryBean]
     * Github 资料表明与 SpringBoot 2.0 有关
     */

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
//        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
//        return sqlSessionFactoryBean;
//    }

    /**
     * 配置事务管理，如果使用到事务需要注入该 Bean，否则事务不会生效
     * 在需要的地方加上 @Transactional 注解即可
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return the sql session factory bean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     * Bean 的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     *
     */

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
}
