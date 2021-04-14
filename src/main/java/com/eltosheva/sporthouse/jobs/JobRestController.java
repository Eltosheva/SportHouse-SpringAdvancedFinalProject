package com.eltosheva.sporthouse.jobs;

import com.eltosheva.sporthouse.jobs.Info.ScheduleJob;
import com.eltosheva.sporthouse.utils.ResponseMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> deleteJob(ScheduleJob job) {
        try {
            schedulerService.deleteJob(job);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(new ResponseMsg(true));
    }

    @RequestMapping(path = "/run", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> runJob(ScheduleJob job) {
        try {
            schedulerService.runJob(job);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(new ResponseMsg(true));
    }
}
