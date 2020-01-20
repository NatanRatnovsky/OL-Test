import {Component, Inject, Optional} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialog} from '@angular/material';
import {Item} from '../../models/Item';
import {ItemService} from '../../services/item.service';

export interface UsersData {
  name: string;
  id: number;
}


@Component({
  selector: 'app-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.css']
})
export class DialogBoxComponent {

  action: string;
  localData: any;
  public item: Item = new Item();

  constructor(
    public dialogRef: MatDialogRef<DialogBoxComponent>,
    private itemService: ItemService,
    public dialog: MatDialog,
    // @Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: UsersData) {
    console.log(data);
    this.localData = {...data};
    this.item = new Item(this.localData.id,
      this.localData.name,
      this.localData.amount,
      this.localData.inventoryCode);
    this.action = this.localData.action;
  }


  doAction() {
    if (this.action === 'Add') {
      this.itemService.addItem(this.item).subscribe(c => {
        console.log(this.item);
      }, error1 => {
        error1(error1.message);
      });
    }
    this.dialogRef.close({event: this.action, data: this.localData});
  }

  closeDialog() {
    this.dialogRef.close({event: 'Cancel'});
  }

  public updateItem(updateID): void {
    this.item.id = updateID;
    console.log(this.item);
    this.itemService.updateItem(this.item).subscribe(c => {
      console.log(this.item);
    }, error1 => {
      error1(error1.message);
    });
    this.dialogRef.close({event: this.action, data: this.localData});
  }

  public deleteItem(updateID) {
    this.itemService.deleteItem(updateID).subscribe(data => {
    }, error1 => {
      error1(error1.message);
    });
    this.dialogRef.close({event: this.action, data: this.localData});
  }

}
