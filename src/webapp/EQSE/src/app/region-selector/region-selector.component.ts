import {Component, OnInit} from '@angular/core';
import {RegionDTO} from "../model/RegionDTO";
import {EmploisQuebecAPI} from "../api/emplois-quebec-api";
import {CityDTO} from "../model/CityDTO";
import {JobDTO} from "../model/JobDTO";

@Component({
  selector: 'app-region-selector',
  template: `
    <div class="container-fluid mx-auto">
      <!-- Region selector : -->
      <p-listbox [options]="regions" [(ngModel)]="selectedRegions" [style]="{'width':'400px'}"
                 [listStyle]="{'max-height':'250px'}" multiple="multiple" checkbox="checkbox" filter="filter"
                 (onChange)="regionsChanged($event)" optionLabel="name">
        <p-header>Choisir une région</p-header>
      </p-listbox>

      <!-- City selector : -->
      <div *ngIf="selectedRegions?.length > 0">
        <p-listbox [options]="cities" [(ngModel)]="selectedCities" [style]="{'width':'400px'}"
                   [listStyle]="{'max-height':'250px'}" multiple="multiple" checkbox="checkbox" filter="filter"
                   (onChange)="citiesChanged($event)" optionLabel="name">
          <p-header>Choisir une ville</p-header>
        </p-listbox>

      </div>

      <p>Villes : {{cities.length}}, {{selectedCities.length}}</p>
      <p>Villes sélectionnés : <span *ngFor="let c of selectedCities" style="margin-right: 20px">{{c.name}}</span></p>
      <p>Régions selectionnés : <span *ngFor="let c of selectedRegions" style="margin-right: 20px">{{c.name}}</span>
      </p>

      Jobs : {{jobs?.length}}

    </div>

  `,
  styles: ['h1 { font-weight: normal; }']
})
export class RegionSelectorComponent implements OnInit {

  regions: RegionDTO[] = [];
  selectedRegions: RegionDTO[] = [];

  constructor(public service: EmploisQuebecAPI) {
    this.service.getRegions().subscribe(regions => {
      console.log('Received ' + regions.length + ' new Cities.');
      this.regions = regions
    });
  }

  cities: CityDTO[] = [];
  selectedCities: CityDTO[] = [];

  regionsChanged(event: any) {
    console.log("Regions changed, loading cities...");

    this.cities = [];

    for (let entry of this.selectedRegions) {
      this.service.getCities(entry.code).subscribe(cities => {
        console.log('Received ' + cities.length + ' new Cities.');
        this.cities = [...this.cities, ...cities]; // push doesnt update
      });
    }
  }

  jobs: JobDTO[];
  selectedJobs: JobDTO[];

  citiesChanged(event: any) {
    console.log("Cities changed, loading jobs...");

    this.jobs = [];

    for (let entry of this.selectedCities) {
      this.service.getJobs(entry.url).subscribe(jobs => {
        console.log('Received ' + jobs.length + ' new Jobs.');
        this.jobs = [...this.jobs, ...jobs]; // push doesnt update
      });
    }
  }

  ngOnInit() {
  }

}
