import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Item} from '../model/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  readonly BASE_URL = '/items';

  constructor(protected readonly http: HttpClient) { }

  public retrieveAllItems(currency: string): Observable<Array<Item>> {

    let params = new HttpParams();
    if (currency) {
      params = params.set('currency', currency);
    }

    return this.http.get<Item[]>(this.BASE_URL, {params});
  }

}
