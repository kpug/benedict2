import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class EsService {
  constructor(private http: HttpClient) {}

  sayHi(query): Observable<String[]> {
    return this.http.get<String[]>(
      `http://35.243.85.1:8081/method/suggest/${query}`
    );
  }
}
