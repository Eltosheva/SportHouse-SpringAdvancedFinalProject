package com.eltosheva.sporthouse.jobs.Info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jobId;

    private String jobName;

    private String jobGroup;

    private String cronExpression;

    private String desc;

    private String interfaceName;
}
