import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exchange-rates',
  templateUrl: './exchange-rates.component.html',
  styleUrls: ['./exchange-rates.component.scss']
})
export class ExchangeRatesComponent {
  amount = 1;
  baseCurrency = 'EUR';

  constructor() { }

}
