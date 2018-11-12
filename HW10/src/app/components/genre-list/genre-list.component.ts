import {Component} from '@angular/core';
import {Genre} from "../../model/genre";
import {GenreService} from "../../service/genre.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {GenreEditorComponent} from "./genre-editor/genre-editor.component";
import {ConfirmationComponent} from "../modals/confirmation/confirmation.component";

@Component({
  selector: 'genre-list',
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.scss']
})
export class GenreListComponent {
  bsModalRef: BsModalRef;

  genres: Array<Genre>;

  constructor(private modalService: BsModalService,
              private genreService: GenreService) {
  }

  ngOnInit(): void {
    this.getGenres()
  }

  getGenres() {
    this.genreService.getGenres()
      .subscribe(data =>
        this.genres = data,
      );
  }

  deleteGenre(id: number) {
    this.genreService.delete(id)
      .subscribe(data =>
        this.genres = this.genres.filter(it => it.id != id)
      );
  }

  createGenre(genre: Genre) {
    this.genreService.create(genre)
      .subscribe(data =>
        this.genres.push(data)
      );
  }

  updateGenre(id: number, genre: Genre) {
    this.genreService.update(id, genre)
      .subscribe(data => {
          this.genres = this.genres.filter(it => it.id != id);
          this.genres.push(data);
        }
      )
    ;
  }

  openEditorComponent(toUpdate: Genre) {
    const initialState = {
      genre: Object.assign({}, toUpdate)
    };

    this.bsModalRef = this.modalService.show(GenreEditorComponent, {initialState});
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (!!toUpdate)
          this.updateGenre(toUpdate.id, updated);
        else
          this.createGenre(updated)
      })
  }

  openConfirmationComponent(toDelete: number) {
    this.bsModalRef = this.modalService.show(ConfirmationComponent);
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (updated) this.deleteGenre(toDelete);
      })
  }
}
