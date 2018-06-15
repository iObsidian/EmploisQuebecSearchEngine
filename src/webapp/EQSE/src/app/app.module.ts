import {MultiSelectModule} from 'primeng/multiselect';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {RegionSelectorComponent} from './region-selector/region-selector.component';
import {CheckboxModule} from 'primeng/checkbox';
import {RegionSelectorService} from "./region-selector/region-selector.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    RegionSelectorComponent
  ],
  imports: [
    BrowserModule,
    MultiSelectModule,
    CheckboxModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    RegionSelectorService,
    HttpClientModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
