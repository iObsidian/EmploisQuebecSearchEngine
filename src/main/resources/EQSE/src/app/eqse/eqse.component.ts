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

  searchTerm: string[] = [];

  isLoading: boolean = false;
  loadingText: string;

  constructor(public service: EmploisQuebecAPI) {
    this.loadRegions();
  }

  private loadRegions() {
    this.setLoading(true, 'Chargement des régions...');

    this.service.getRegions().subscribe(regions => {
      this.setLoading(false);

      console.log('Received ' + regions.length + ' new cities.');

      for (let region of regions) {
        console.log(region)
      }


      this.regions = regions
    });
  }

  // Set loading status. If true, displays a loading icon with informative text.
  setLoading(isLoading: boolean, loadingText?: string) {
    this.isLoading = isLoading;

    if (loadingText) {
      this.loadingText = loadingText;
    }
  }

  regions: RegionDTO[] = [];
  selectedRegions: RegionDTO[] = [];

  selectedRegionsChanged(event: any) {
    console.log("Regions changed, loading cities...");

    this.setLoading(true, 'Chargement des villes...');
    this.cities = [];

    for (let entry of this.selectedRegions) {
      this.service.getCitiesForRegion(entry.code).subscribe(cities => {
        console.log('Received ' + cities.length + ' new regions.');
        this.cities = [...this.cities, ...cities]; // use this instead of "push" since it doesn't update
        this.setLoading(false);
      });
    }
  }

  cities: CityDTO[] = [];
  selectedCities: CityDTO[] = [];

  jobs: JobDTO[];

  selectedCitiesChanged(event: any) {
    this.setLoading(true, 'Chargement des emplois...');

    this.jobs = [];
    for (let entry of this.selectedCities) {
      this.service.getJobsForCity(entry.url).subscribe(jobs => {
        console.log('Received ' + jobs.length + ' new jobs.');
        this.jobs = [...this.jobs, ...jobs]; // Push all jobs
        this.setLoading(false);
      });
    }
  }

  selectedSchoolLevel: string[] = ['Secondaire', 'Collégial', 'Universitaire'];

  selectedSchoolLevelChanged(event: any) {
    console.log('School level changed.');
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


}
