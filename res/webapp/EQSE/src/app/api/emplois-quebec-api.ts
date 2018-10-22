import {Injectable} from '@angular/core';


import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {RegionDTO} from "../model/RegionDTO";
import {CityDTO} from "../model/CityDTO";
import {JobDTO} from "../model/JobDTO";

@Injectable()
export class EmploisQuebecAPI {

  private url = 'http://localhost:7000/';

  private regions = this.url + 'regions/';  // URL to web api
  private cities = this.url + 'cities/';  // URL to web api
  private jobs = this.url + 'jobs/';  // URL to web api

  constructor(private http: HttpClient) {
  }

  /** GET regions */
  getRegions(): Observable<RegionDTO[]> {
    return this.http.get<RegionDTO[]>(this.regions)
  }

  /** GET cities (using Region code) */
  getCitiesForRegion(region: string): Observable<CityDTO[]> {
    return this.http.get<CityDTO[]>(this.cities + region)
  }

  /** GET cities (using City url) */
  getJobsForCity(city: string): Observable<JobDTO[]> {
    return this.http.get<JobDTO[]>(this.jobs + encodeURIComponent(city))
  }

}
