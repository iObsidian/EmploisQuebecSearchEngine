import {Component, OnInit} from '@angular/core';
import {SelectItem} from "primeng/api";
import {RegionDTO} from "../model/RegionDTO";
import {RegionSelectorService} from "./region-selector.service";

@Component({
  selector: 'app-region-selector',
  templateUrl: './region-selector.component.html',
  styleUrls: ['./region-selector.component.css']
})
export class RegionSelectorComponent implements OnInit {

  regions: RegionDTO[];

  constructor(public service: RegionSelectorService) {
    this.service.getHeroes().subscribe(e => this.receivedRegions(e));
  }

  receivedRegions(e: RegionDTO[]) {
    this.regions = e;

    console.log("Length of regions : "+e.length);

    for (let entry of e) {
      console.log(entry.name);
    }
  }

  ngOnInit() {
  }

}

