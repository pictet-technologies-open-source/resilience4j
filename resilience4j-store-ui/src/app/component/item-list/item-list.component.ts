import { Component, OnInit } from '@angular/core';
import {Item} from '../../model/item';
import {ItemService} from '../../services/item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.scss']
})
export class ItemListComponent implements OnInit {

  private static readonly DEFAULT_CURRENCY = 'EUR';

  currencyConversionEnabled = false;
  currency: string;
  items: Item[] = [];

  constructor(private readonly itemService: ItemService) { }

  ngOnInit(): void {
    this.reloadItems();
  }

  currencyChanged(value) {
    this.currency = value;
    this.reloadItems();
  }

  toggleCurrencyConversion() {
    if (this.currencyConversionEnabled) {
      this.currencyConversionEnabled = false;
      this.currency = null;
    } else {
      this.currencyConversionEnabled = true;
      this.currency = ItemListComponent.DEFAULT_CURRENCY;
    }
    this.reloadItems();
  }

  private reloadItems() {
    this.itemService.retrieveAllItems(this.currency).subscribe(items => {
      this.items = items;
    });
  }
}
