package org.core.task.boot.config;

import org.core.datasource.boot.config.aop.DynamicDataSourceAspect;
import org.core.datasource.boot.config.property.DynamicDSPropertyConfig;
//import org.core.task.boot.config.property.TaskPropertyConfig;
import org.core.task.boot.config.property.TaskPropertyConfig;
import org.core.task.boot.config.service.impl.TaskTriggerSchedule;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableConfigurationProperties(TaskPropertyConfig.class)
@ImportAutoConfiguration({
        TaskTriggerSchedule.class
})
//@PropertySources
//        ({
//                @PropertySource(value = "config/task/application-task.yml" )
//        })
public class TaskConfig {

}
