package org.core.mail.boot.config;

import org.core.mail.boot.config.service.impl.EmailServiceImpl;
import org.core.task.boot.config.property.TaskPropertyConfig;
import org.core.task.boot.config.service.impl.TaskTriggerSchedule;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
        EmailServiceImpl.class
})
public class EmailConfig {

}
