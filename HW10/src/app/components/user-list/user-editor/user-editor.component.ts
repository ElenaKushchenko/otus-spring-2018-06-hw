import {Component} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {Subject} from "rxjs";
import {User} from "../../../model/user";

@Component({
  selector: 'user-editor',
  templateUrl: './user-editor.component.html',
  styleUrls: ['./user-editor.component.scss']
})
export class UserEditorComponent {
  public output: Subject<User>;

  user: User;

  constructor(public bsModalRef: BsModalRef) {}

  public ngOnInit(): void {
    this.output = new Subject();
  }

  public onConfirm(): void {
    this.output.next(this.user);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.bsModalRef.hide();
  }
}
