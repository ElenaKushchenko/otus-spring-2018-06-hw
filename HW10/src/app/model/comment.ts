import {User} from "./user";

export class Comment {
  constructor(public text: string,
              public user: User,
              public bookId: number,
              public date?: Date,
              public id?: number) {
  }
}
