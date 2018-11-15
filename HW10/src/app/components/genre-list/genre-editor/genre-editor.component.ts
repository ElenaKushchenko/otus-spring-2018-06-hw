import {Component} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {Subject} from "rxjs";
import {Genre} from "../../../model/genre";

@Component({
  selector: 'genre-editor',
  templateUrl: './genre-editor.component.html',
  styleUrls: ['./genre-editor.component.scss']
})
export class GenreEditorComponent {
  public output: Subject<Genre>;

  genre: Genre;

  constructor(public bsModalRef: BsModalRef) {}

  public ngOnInit(): void {
    this.output = new Subject();
  }

  public onConfirm(): void {
    this.output.next(this.genre);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.bsModalRef.hide();
  }
}
