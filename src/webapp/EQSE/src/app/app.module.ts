import {MultiSelectModule} from 'primeng/multiselect';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {RegionSelectorComponent} from './region-selector/region-selector.component';
import {CheckboxModule} from 'primeng/checkbox';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {EmploisQuebecAPI} from "./api/emplois-quebec-api";

import { CommonModule } from "@angular/common";

import {SelectButtonModule} from 'primeng/selectbutton';

import {DataScrollerModule} from 'primeng/datascroller';

import {ListboxModule} from 'primeng/listbox';
import { FilterPipe } from './filter.pipe';



import {ChipsModule} from 'primeng/chips';


@NgModule({
  declarations: [
    AppComponent,
    RegionSelectorComponent,
    FilterPipe
  ],
  imports: [
    BrowserModule,
    MultiSelectModule,
    CheckboxModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    SelectButtonModule,
    ListboxModule,
    CommonModule,
    DataScrollerModule,
    ChipsModule
  ],
  providers: [
    EmploisQuebecAPI,
    HttpClientModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
