import {Component} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {Subject} from "rxjs";
import {Author} from "../../../model/author";

@Component({
  selector: 'author-editor',
  templateUrl: './author-editor.component.html',
  styleUrls: ['./author-editor.component.scss']
})
export class AuthorEditorComponent {
  public output: Subject<Author>;

  author: Author;

  constructor(public bsModalRef: BsModalRef) {}

  public ngOnInit(): void {
    this.output = new Subject();
  }

  public onConfirm(): void {
    this.output.next(this.author);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.bsModalRef.hide();
  }
}
