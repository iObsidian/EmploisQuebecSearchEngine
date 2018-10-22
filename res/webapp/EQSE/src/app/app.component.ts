import { Component } from '@angular/core';
import {FilterPipe} from "./filter.pipe";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Emplois Québec Search Engine';
  description = 'Outil de recherche pour Emplois Québec Placement en Ligne';
}
