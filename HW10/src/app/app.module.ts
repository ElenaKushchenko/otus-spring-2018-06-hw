import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {CollapseModule} from 'ngx-bootstrap/collapse';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {BookListComponent} from "./components/book-list/book-list.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";
import {AuthorListComponent} from "./components/author-list/author-list.component";
import {GenreListComponent} from "./components/genre-list/genre-list.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookListComponent,
    BookDetailsComponent,
    AuthorListComponent,
    GenreListComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CollapseModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
