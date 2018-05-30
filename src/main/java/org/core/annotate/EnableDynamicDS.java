package org.core.annotate;

import org.core.datasource.boot.config.DataSourceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DataSourceConfig.class)
@Documented
public @interface EnableDynamicDS {
}
