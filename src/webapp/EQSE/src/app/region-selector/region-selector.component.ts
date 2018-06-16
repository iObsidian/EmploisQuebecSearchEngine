import {Component, OnInit} from '@angular/core';
import {RegionDTO} from "../model/RegionDTO";
import {EmploisQuebecAPI} from "../api/emplois-quebec-api";
import {CityDTO} from "../model/CityDTO";
import {JobDTO} from "../model/JobDTO";

@Component({
  selector: 'app-region-selector',
  template: `

    <!-- Region selector : -->

    <p-listbox [options]="regions" [(ngModel)]="selectedRegions" [style]="{'width':'400px'}"
               [listStyle]="{'max-height':'250px'}" multiple="multiple" checkbox="checkbox" filter="filter"
               (onChange)="regionsChanged($event)" optionLabel="name">
      <p-header>Région</p-header>
    </p-listbox>
    <p>Selected Regions: <span *ngFor="let c of selectedRegions" style="margin-right: 20px">{{c.name}}</span></p>

    <!-- City selector : -->

    <!--<div *ngIf="selectedRegions?.length > 0">-->
    <p-listbox [options]="cities" [(ngModel)]="selectedCities" [style]="{'width':'400px'}"
               [listStyle]="{'max-height':'250px'}" multiple="multiple" checkbox="checkbox" filter="filter"
               (onChange)="citiesChanged($event)" optionLabel="name">
      <p-header>Villes</p-header>
    </p-listbox>
    <p>Total de villes {{cities.length}}, {{selectedCities.length}}</p>
    <p>Villes sélectionnés : <span *ngFor="let c of selectedCities" style="margin-right: 20px">{{c.name}}</span></p>
    <!--</div>-->
  `,
  styles: ['h1 { font-weight: normal; }']
})
export class RegionSelectorComponent implements OnInit {

  regions: RegionDTO[] = [];
  selectedRegions: RegionDTO[] = [];

  cities: CityDTO[] = [];
  selectedCities: CityDTO[] = [];

  jobs: JobDTO[];
  selectedJobs: JobDTO[];

  constructor(public service: EmploisQuebecAPI) {
    this.service.getRegions().subscribe(e => {
      this.regions = e
    });

    const city: CityDTO = {name: 'CityDTO test', url: 'Hey', numberOfOffers: '10'};
    this.cities.push(city);
    this.cities.push(city);
  }

  regionsChanged(event: any) {
    for (let entry of this.selectedRegions) {
      this.service.getCities(entry.code).subscribe(new_cities => {
        console.log('Received new Cities ' + new_cities.length);
        this.cities.push(...new_cities);
      });
    }
  }

  citiesChanged(event: any) {
    console.log("Cities changed");
  }

  ngOnInit() {
  }

}
