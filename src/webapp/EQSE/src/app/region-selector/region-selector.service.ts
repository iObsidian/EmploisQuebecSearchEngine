import {Injectable} from '@angular/core';


import {HttpClient} from '@angular/common/http';
import {MessageService} from "primeng/components/common/messageservice";
import {Observable} from "rxjs/Observable";
import {RegionDTO} from "../model/RegionDTO";

@Injectable()
export class RegionSelectorService {

  private heroesUrl = 'http://localhost:7000/regions';  // URL to web api

  constructor(private http: HttpClient) {
  }

  /** GET heroes from the server */
  getHeroes(): Observable<RegionDTO[]> {
    return this.http.get<RegionDTO[]>(this.heroesUrl)
  }

}
