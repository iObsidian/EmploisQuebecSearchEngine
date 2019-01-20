import {MultiSelectModule} from 'primeng/multiselect';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {CheckboxModule} from 'primeng/checkbox';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {EmploisQuebecAPI} from "./api/emplois-quebec-api";

import {CommonModule} from "@angular/common";

import {SelectButtonModule} from 'primeng/selectbutton';

import {DataScrollerModule} from 'primeng/datascroller';

import {ListboxModule} from 'primeng/listbox';
import {KeywordFilterPipe} from './keyword-filter.pipe';

import {CardModule} from 'primeng/card';

import {ChipsModule} from 'primeng/chips';
import {EQSEComponent} from './eqse/eqse.component';

import {SliderModule} from 'primeng/slider';
import {EducationLevelFilterPipe} from "./education-level-filter.pipe";


@NgModule({
  declarations: [
    AppComponent,
    EQSEComponent,
    KeywordFilterPipe,
    EducationLevelFilterPipe
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
    ChipsModule,
    CardModule,
    SliderModule
  ],
  providers: [
    EmploisQuebecAPI,
    HttpClientModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
