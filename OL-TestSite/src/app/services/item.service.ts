import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Item} from '../models/Item';

@Injectable({
  providedIn: 'root'
})

export class ItemService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private httpClient: HttpClient) {
  }

  public getAllItems(): Observable<Item[]> {
    return this.httpClient.get<Item[]>('api/allItems');
  }

  public addItem(item: Item): Observable<Item> {
    return this.httpClient.post<Item>('api/addItem', item);
  }

  public updateItem(item: Item): Observable<Item> {
    return this.httpClient.put<Item>('api/updateItem', item);
  }

  public deleteItem(id: number) {
    return this.httpClient.delete('api/rmItem/' + id, this.httpOptions);
  }

  public getItem(id: number): Observable<Item> {
    return this.httpClient.get<Item>('api/item' + id);
  }
}
