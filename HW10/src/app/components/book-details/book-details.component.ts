import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Book} from "../../model/book";
import {BookService} from "../../service/book.service";
import {Comment} from "../../model/comment";
import {CommentService} from "../../service/comment.service";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";

@Component({
  selector: 'book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent {
  book: Book;
  users: Array<User>;

  newComment = {};

  constructor(private route: ActivatedRoute,
              private bookService: BookService,
              private commentService: CommentService,
              private userService: UserService) {
  }

  ngOnInit() {
    let bookId = parseInt(this.route.snapshot.paramMap.get('id'));
    this.getBook(bookId);
    this.getUsers();
  }

  getBook(id: number) {
    this.bookService.getBook(id)
      .subscribe(data =>
        this.book = data
      );
  }

  getUsers() {
    this.userService.getUsers()
      .subscribe(data =>
        this.users = data
      );
  }

  addComment(comment: Comment) {
    comment.bookId = this.book.id;

    this.commentService.create(comment)
      .subscribe(data => {
          if (this.book.comments == null) this.book.comments = [];
          this.book.comments.push(data);
          this.newComment = {}
        }
      );
  }

  joinToString(namedItems: Array<any>) {
    if (namedItems) return namedItems.map(it => it.name).join(", ")
  }
}
