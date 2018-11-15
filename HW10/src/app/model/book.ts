import {Author} from "./author";
import {Comment} from "./comment";
import {Genre} from "./genre";

export class Book {
  constructor(public name: string,
              public originalName?: string,
              public paperback?: number,
              public authors?: Array<Author>,
              public genres?: Array<Genre>,
              public comments?: Array<Comment>,
              public id?: number) {
  }
}
