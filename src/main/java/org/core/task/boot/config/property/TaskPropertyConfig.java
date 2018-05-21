package org.core.task.boot.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.core.task.boot.config.tuple.JobProperty;
import org.core.task.boot.config.tuple.JobYMLProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@ConfigurationProperties(prefix = "tasks")
@Configuration
public class TaskPropertyConfig {
    private List<JobYMLProperty> jobConfigList;
}
