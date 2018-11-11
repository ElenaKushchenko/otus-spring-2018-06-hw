import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BookListComponent} from "./components/book-list/book-list.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";
import {AuthorListComponent} from "./components/author-list/author-list.component";
import {GenreListComponent} from "./components/genre-list/genre-list.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

const routes: Routes = [
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: 'books', component: BookListComponent },
  { path: 'books/:id', component: BookDetailsComponent },
  { path: 'authors', component: AuthorListComponent },
  { path: 'genres', component: GenreListComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
