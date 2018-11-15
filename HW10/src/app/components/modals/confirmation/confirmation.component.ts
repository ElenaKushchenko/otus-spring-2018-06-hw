import {Component} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {Subject} from "rxjs";

@Component({
  selector: 'confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent {
  public output: Subject<boolean>;

  constructor(public bsModalRef: BsModalRef) {}

  public ngOnInit(): void {
    this.output = new Subject();
  }

  public onConfirm(): void {
    this.output.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.output.next(false);
    this.bsModalRef.hide();
  }
}
