import {Pipe, PipeTransform} from '@angular/core';
import {JobDTO} from "./model/JobDTO";

@Pipe({
  name: 'keywordFilter'
})
export class KeywordFilterPipe implements PipeTransform {

  transform(jobs: JobDTO[], searchTerms: string[]): any {

    if (!searchTerms) {
      console.log("No search terms...");
      return jobs;
    }

    // return updated jobs array
    return jobs.filter(function (job) {

      if (!searchTerms || searchTerms.length == 0) {
        return true;
      }

      let match = false;

      for (let s of searchTerms) {
        if (!job.nameOfTheJob || !job.employer) {
          return true;
        } else {
          match = job.nameOfTheJob.toLowerCase().includes(s.toLowerCase()) || job.employer.toLowerCase().includes(s.toLowerCase());
        }
      }

      return match;

    });
  }

}
