import {Pipe, PipeTransform} from '@angular/core';
import {JobDTO} from "./model/JobDTO";

@Pipe({
  name: 'educationFilter'
})
export class EducationLevelFilterPipe implements PipeTransform {

  transform(jobs: JobDTO[], educationLevel: string[]): any {

    if (!educationLevel) {
      console.log("No search terms...");
      return jobs;
    }

    // return updated jobs array
    return jobs.filter(function (job) {

      if (!educationLevel || educationLevel.length == 0) {
        return true;
      }

      let match = false;

      for (let s of educationLevel) {
        if (!job.education) {
          return true;
        } else {
          if (job.education.toLowerCase().includes(s.toLowerCase())) {
            match = true;
          }
        }
      }

      return match;

    });
  }

}
