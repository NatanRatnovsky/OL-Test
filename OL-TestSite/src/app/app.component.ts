import {Component, OnInit, ViewChild} from '@angular/core';
import {Item} from './models/Item';
import {ItemService} from './services/item.service';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MatDialog} from '@angular/material';
import {DialogBoxComponent} from './components/dialog-box/dialog-box.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class AppComponent implements OnInit {
  public items: Item[];
  public showItem = false;
  displayedColumns: string[] = ['id', 'name', 'amount', 'inventoryCode', 'action'];
  dataSource;
  public item: Item = new Item();
  public addItemButtonValue = 'Add Item';

  @ViewChild(MatTable, {static: true}) table: MatTable<any>;

  constructor(private itemService: ItemService, private dialog: MatDialog) {
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this.itemService.getAllItems().subscribe(items => {
        this.items = items;
        this.dataSource = new MatTableDataSource(this.items);
      }, error1 => {
        alert(error1);
      }
    );
  }

  public addItemFunc(): void {
    this.itemService.addItem(this.item).subscribe(i => {
      this.ngOnInit();
    }, error1 => {
      error1(error1.message);
    });
  }

  public updateItem(updateID): void {
    this.item.id = updateID;
    this.itemService.updateItem(this.item).subscribe(i => {
      this.ngOnInit();
    }, error1 => {
      error1(error1.message);
    });
  }
  public openDialog(action, obj) {
    obj.action = action;
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '450px',
      data: obj,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.event === 'Add') {
        this.ngOnInit();
      } else if (result.event === 'Update') {
        this.ngOnInit();
      } else if (result.event === 'Delete') {
        this.ngOnInit();
      }
    });
  }
}
