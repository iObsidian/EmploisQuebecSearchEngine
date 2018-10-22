import {Component} from '@angular/core';
import {RegionDTO} from "../model/RegionDTO";
import {EmploisQuebecAPI} from "../api/emplois-quebec-api";
import {CityDTO} from "../model/CityDTO";
import {JobDTO} from "../model/JobDTO";

@Component({
  selector: 'app-region-selector',
  templateUrl: 'region-selector.html',
  styleUrls: ['region-selector.css']
})
export class RegionSelectorComponent {

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

  regionsChanged(event: any) {
    console.log("Regions changed, loading cities...");

    this.setLoading(true, 'Chargement des villes...');
    this.cities = [];

    for (let entry of this.selectedRegions) {
      this.service.getCities(entry.code).subscribe(cities => {
        console.log('Received ' + cities.length + ' new Cities.');
        this.cities = [...this.cities, ...cities]; // push doesnt update

        this.setLoading(false);
      });
    }
  }

  jobs: JobDTO[];
  selectedJobs: JobDTO[];

  citiesChanged(event: any) {
    this.setLoading(true, 'Chargement des emplois...');

    this.jobs = [];
    for (let entry of this.selectedCities) {
      this.service.getJobs(entry.url).subscribe(jobs => {
        console.log('Received ' + jobs.length + ' new jobs.');
        this.jobs = [...this.jobs, ...jobs]; // push doesnt update

        this.setLoading(false);
      });
    }
  }

  getSubtitle() {
    if (this.searchTerm.length > 0) {
      return "offre(s) d'emplois dans " + this.getVilles();
    } else {
      return "offre(s) d'emplois trouvés dans " + this.getVilles() + ".";
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

}
