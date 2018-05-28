package org.core.task.boot.config.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobYMLProperty {
    private String clazz;
    private String desc;
    private boolean enable;
    /*
    定时表达式
     */
    private String cronTab;

    private JobIdentity jobDetail;

    private JobIdentity jobTrigger;
}
