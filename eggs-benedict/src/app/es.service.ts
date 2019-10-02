import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EsService {
  constructor(private http: HttpClient) { }

  sayHi(query) {
    return this.http.get(`http://localhost:8081/method/suggest/${query}`)
  }
}