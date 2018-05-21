package org.core.datasource.boot.config.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.core.datasource.boot.config.annotate.TargetDataSource;
import org.core.datasource.boot.config.container.DynamicDataSourceContextHolder;
import org.core.datasource.boot.config.property.DynamicDSPropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-10)//保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    @Autowired
    DynamicDSPropertyConfig dynamicDSPropertyConfig;

    @Before("@annotation(targetDataSource)")
    public void beforeSwitchDS(JoinPoint point,TargetDataSource targetDataSource){
        if (!DynamicDataSourceContextHolder.containDataSourceKey(targetDataSource.value())) {
            System.out.println("DataSource [{}] doesn't exist, use default DataSource [{}] " + targetDataSource.value());
        } else {
            // 切换数据源
            DynamicDataSourceContextHolder.setDataSourceKey(targetDataSource.value());
            System.out.println("Switch DataSource to [{}] in Method [{}] " +
                    DynamicDataSourceContextHolder.getDataSourceKey() + point.getSignature());
        }

    }


    @After("@annotation(targetDataSource)")
    public void afterSwitchDS(JoinPoint point,TargetDataSource targetDataSource){
        // 将数据源置为默认数据源
        DynamicDataSourceContextHolder.clearDataSourceKey();
        System.out.println("Restore DataSource to [{}] in Method [{}] " +
        DynamicDataSourceContextHolder.getDataSourceKey() + point.getSignature());
    }
}