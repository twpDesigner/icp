package org.core.task.boot.config.tuple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JobProperty {

    private boolean enable;

    private Class <? extends Job> jobClass;
    /*
    定时表达式
     */
    private String cronTab;

    private JobIdentity jobDetail;

    private JobIdentity jobTrigger;
}
