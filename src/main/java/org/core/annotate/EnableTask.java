package org.core.annotate;

import org.core.task.boot.config.TaskConfig;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TaskConfig.class,SchedulingConfiguration.class})
@Documented
public @interface EnableTask {
}
