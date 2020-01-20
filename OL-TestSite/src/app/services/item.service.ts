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
    return this.httpClient.get<Item[]>('http://localhost:8080/api/allItems');
  }

  public addItem(item: Item): Observable<Item> {
    return this.httpClient.post<Item>('http://localhost:8080/api/addItem', item);
  }

  public updateItem(item: Item): Observable<Item> {
    return this.httpClient.put<Item>('http://localhost:8080/api/updateItem', item);
  }

  public deleteItem(id: number) {
    return this.httpClient.delete('http://localhost:8080/api/rmItem/' + id, this.httpOptions);
  }

  public getItem(id: number): Observable<Item> {
    return this.httpClient.get<Item>('http://localhost:8080/api/item' + id);
  }
}
