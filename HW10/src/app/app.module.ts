import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {CollapseModule} from 'ngx-bootstrap/collapse';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BsDropdownModule } from 'ngx-bootstrap/dropdown';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {BookListComponent} from "./components/book-list/book-list.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";
import {AuthorListComponent} from "./components/author-list/author-list.component";
import {GenreListComponent} from "./components/genre-list/genre-list.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {GenreService} from "./service/genre.service";
import {AuthorService} from "./service/author.service";
import {UserService} from "./service/user.service";
import {CommentService} from "./service/comment.service";
import {BookService} from "./service/book.service";
import {AuthorEditorComponent} from "./components/author-list/author-editor/author-editor.component";
import {ConfirmationComponent} from "./components/modals/confirmation/confirmation.component";
import {UserListComponent} from "./components/user-list/user-list.component";
import {UserEditorComponent} from "./components/user-list/user-editor/user-editor.component";
import {GenreEditorComponent} from "./components/genre-list/genre-editor/genre-editor.component";
import {BookEditorComponent} from "./components/book-list/book-editor/book-editor.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookListComponent,
    BookEditorComponent,
    BookDetailsComponent,
    AuthorListComponent,
    AuthorEditorComponent,
    UserListComponent,
    UserEditorComponent,
    GenreListComponent,
    GenreEditorComponent,
    PageNotFoundComponent,
    ConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CollapseModule.forRoot(),
    ModalModule.forRoot(),
    BsDropdownModule.forRoot()
  ],
  providers: [
    GenreService,
    AuthorService,
    UserService,
    CommentService,
    BookService
  ],
  entryComponents: [
    AuthorEditorComponent,
    UserEditorComponent,
    GenreEditorComponent,
    BookEditorComponent,
    ConfirmationComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
