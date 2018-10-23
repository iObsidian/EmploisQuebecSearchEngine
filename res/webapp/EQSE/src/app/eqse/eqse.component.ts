import {Component} from '@angular/core';
import {RegionDTO} from "../model/RegionDTO";
import {EmploisQuebecAPI} from "../api/emplois-quebec-api";
import {CityDTO} from "../model/CityDTO";
import {JobDTO} from "../model/JobDTO";

@Component({
  selector: 'EQSE',
  templateUrl: 'eqse.html',
  styleUrls: ['eqse.css']
})
export class EQSEComponent {

  debug: boolean = false;

  searchTerm: string[] = [];

  isLoading: boolean = false;
  loadingText: string;


  setLoading(isLoading: boolean, loadingText?: string) {
    this.isLoading = isLoading;

    if (loadingText) {
      this.loadingText = loadingText;
    }
  }

  regions: RegionDTO[] = [];
  selectedRegions: RegionDTO[] = [];

  constructor(public service: EmploisQuebecAPI) {

    this.setLoading(true, 'Chargement des régions...');

    this.service.getRegions().subscribe(regions => {
      this.setLoading(false);

      console.log('Received ' + regions.length + ' new Cities.');
      this.regions = regions
    });
  }

  cities: CityDTO[] = [];
  selectedCities: CityDTO[] = [];

  selectedRegionsChanged(event: any) {
    console.log("Regions changed, loading cities...");

    this.setLoading(true, 'Chargement des villes...');
    this.cities = [];

    for (let entry of this.selectedRegions) {
      this.service.getCitiesForRegion(entry.code).subscribe(cities => {
        console.log('Received ' + cities.length + ' new Cities.');
        this.cities = [...this.cities, ...cities]; // push doesnt update
        this.setLoading(false);
      });
    }
  }

  jobs: JobDTO[];

  selectedCitiesChanged(event: any) {
    this.setLoading(true, 'Chargement des emplois...');

    this.jobs = [];
    for (let entry of this.selectedCities) {
      this.service.getJobsForCity(entry.url).subscribe(jobs => {
        console.log('Received ' + jobs.length + ' new jobs.');
        this.receiveJobs(jobs);
        this.setLoading(false);
      });
    }
  }

  getVilles(): string {
    if (this.selectedCities.length == 0) {
      return 'aucun endroit';
    } else if (this.selectedCities.length == 1) {
      return this.selectedCities[0].name;
    } else {
      return this.selectedCities.length + ' villes';
    }
  }

  public selectedEducationsChanged(event: any) {

  }

  public selectedWorkplacesChanged(event: any) {

  }


  educations: string[] = [];
  selectedEducations: string[] = [];

  workplaces: string[] = [];
  selectedWorkplaces: string[] = [];

  private receiveJobs(jobs: JobDTO[]) {
    this.jobs = [...this.jobs, ...jobs]; // Push all jobs

    for (let job of jobs) { // Add types of educations
      if (!this.educations.includes(job.education)) {
        this.educations.push(job.education);
      }
    }

    for (let job of jobs) { // Add types of workplaces
      if (!this.workplaces.includes(job.workPlace)) {
        this.workplaces.push(job.workPlace);
      }
    }
  }
}
