package com.eltosheva.sporthouse.jobs;

import com.eltosheva.sporthouse.jobs.Info.ScheduleJob;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController()
@RequestMapping(path = "/api/schedule")
public class JobRestController {

    private final SchedulerService schedulerService;

    public JobRestController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @RequestMapping(path = "/addOrEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> addOrEdit(ScheduleJob job) {
        try {
            schedulerService.addOrEditJob(job);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(new ResponseMsg(true));
    }

    private class ResponseMsg implements Serializable {
        private Boolean valid;

        public ResponseMsg(Boolean valid) {
            this.valid = valid;
        }

        public Boolean getValid() {
            return valid;
        }

        public ResponseMsg setValid(Boolean valid) {
            this.valid = valid;
            return this;
        }
    }
}
