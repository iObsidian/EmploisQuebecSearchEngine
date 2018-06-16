import {Pipe, PipeTransform} from '@angular/core';
import {JobDTO} from "./model/JobDTO";

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(jobs: JobDTO[], searchTerm: string): any {
    //check if searchTerm is undefined
    if (!searchTerm) {
      console.log("Returned")
      return jobs;
    }

    // return updated jobs array
    return jobs.filter(function (job) {
      if (!job.nameOfTheJob) {
        return true;
      } else {
        return job.nameOfTheJob.toLowerCase().includes(searchTerm.toLowerCase());
      }
    })
  }

}
