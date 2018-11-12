import {Author} from "./author";
import {Genre} from "./genre";

export class ShortBook {
  constructor(public name: string,
              public authors?: Array<Author>,
              public genres?: Array<Genre>,
              public id?: number) {
  }
}
