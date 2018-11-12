import {Component} from '@angular/core';
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {UserEditorComponent} from "./user-editor/user-editor.component";
import {ConfirmationComponent} from "../modals/confirmation/confirmation.component";

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent {
  bsModalRef: BsModalRef;

  users: Array<User>;

  constructor(private modalService: BsModalService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.getUsers()
  }

  getUsers() {
    this.userService.getUsers()
      .subscribe(data =>
        this.users = data,
      );
  }

  deleteUser(id: number) {
    this.userService.delete(id)
      .subscribe(data =>
        this.users = this.users.filter(it => it.id != id)
      );
  }

  createUser(user: User) {
    this.userService.create(user)
      .subscribe(data =>
        this.users.push(data)
      );
  }

  updateUser(id: number, user: User) {
    this.userService.update(id, user)
      .subscribe(data => {
          this.users = this.users.filter(it => it.id != id);
          this.users.push(data);
        }
      )
    ;
  }

  openEditorComponent(toUpdate: User) {
    const initialState = {
      user: Object.assign({}, toUpdate)
    };

    this.bsModalRef = this.modalService.show(UserEditorComponent, {initialState});
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (!!toUpdate)
          this.updateUser(toUpdate.id, updated);
        else
          this.createUser(updated)
      })
  }

  openConfirmationComponent(toDelete: number) {
    this.bsModalRef = this.modalService.show(ConfirmationComponent);
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (updated) this.deleteUser(toDelete);
      })
  }
}
