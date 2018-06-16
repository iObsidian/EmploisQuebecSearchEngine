import {Pipe, PipeTransform} from '@angular/core';
import {JobDTO} from "./model/JobDTO";

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(jobs: JobDTO[], searchTerms: string[]): any {
    //check if searchTerm is undefined
    if (!searchTerms) {
      console.log("Returned")
      return jobs;
    }

    // return updated jobs array
    return jobs.filter(function (job) {

      if (!searchTerms || searchTerms.length == 0 ) {
        return true;
      }

      var match = false;

      for (let s of searchTerms) {
        if (!job.nameOfTheJob || !job.employer) {
          return true;
        } else {

          var nameMatch = job.nameOfTheJob.toLowerCase().includes(s.toLowerCase());
          var employerMatch = job.employer.toLowerCase().includes(s.toLowerCase());

          match = nameMatch || employerMatch;
        }
      }

      return match;

    })
  }

}
