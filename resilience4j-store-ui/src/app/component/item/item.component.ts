import {Component, Input} from '@angular/core';
import {Item} from "../../model/item";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent {

  @Input()
  item: Item;

  constructor() { }


}
