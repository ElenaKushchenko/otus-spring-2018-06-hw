import {Component} from '@angular/core';
import {Author} from "../../model/author";
import {AuthorService} from "../../service/author.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {AuthorEditorComponent} from "./author-editor/author-editor.component";
import {ConfirmationComponent} from "../modals/confirmation/confirmation.component";

@Component({
  selector: 'author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.scss']
})
export class AuthorListComponent {
  bsModalRef: BsModalRef;

  authors: Array<Author>;

  constructor(private modalService: BsModalService,
              private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.getAuthors()
  }

  getAuthors() {
    this.authorService.getAuthors()
      .subscribe(data =>
        this.authors = data,
      );
  }

  deleteAuthor(id: number) {
    this.authorService.delete(id)
      .subscribe(data =>
        this.authors = this.authors.filter(it => it.id != id)
      );
  }

  createAuthor(author: Author) {
    this.authorService.create(author)
      .subscribe(data =>
        this.authors.push(data)
      );
  }

  updateAuthor(id: number, author: Author) {
    this.authorService.update(id, author)
      .subscribe(data => {
          this.authors = this.authors.filter(it => it.id != id);
          this.authors.push(data);
        }
      )
    ;
  }

  openEditorComponent(toUpdate: Author) {
    const initialState = {
      author: Object.assign({}, toUpdate)
    };

    this.bsModalRef = this.modalService.show(AuthorEditorComponent, {initialState});
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (!!toUpdate)
          this.updateAuthor(toUpdate.id, updated);
        else
          this.createAuthor(updated)
      })
  }

  openConfirmationComponent(toDelete: number) {
    this.bsModalRef = this.modalService.show(ConfirmationComponent);
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (updated) this.deleteAuthor(toDelete);
      })
  }
}
