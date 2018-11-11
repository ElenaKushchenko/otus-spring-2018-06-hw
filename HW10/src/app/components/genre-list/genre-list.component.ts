import { Component } from '@angular/core';
import {GenreService} from "../../service/genre.service";

@Component({
  selector: 'genre-list',
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.scss']
})
export class GenreListComponent {

  constructor(private genreService: GenreService) {
  }
}
