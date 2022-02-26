import { Time } from "@angular/common";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Answer } from "../model/answer.model";
import { Request } from "../model/request.model";
import { Ticket } from "../model/ticket.model";
import { map } from 'rxjs/operators';

@Injectable({
    providedIn:"root"
})
export class BookService{

    private baseURL = "http://localhost:8080/book";

    constructor(private httpClient: HttpClient){}

    getSpeciality():Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseURL}/speciality`)
    }

    getDoctors(spec:string):Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseURL}/doctor/${spec}`);
    }

    getTicket(doc:string,date:Date):Observable<Ticket[]>{
        return this.httpClient.get<Ticket[]>(`${this.baseURL}/ticket/${doc}/${date}`);
    }

    getBookedTicket(doc:string,date:Date):Observable<Ticket[]>{
        return this.httpClient.get<Ticket[]>(`${this.baseURL}/ticket/${doc}/by/${date}`);
    }

    getTicketByUserName(userName:string):Observable<Ticket[]>{
        return this.httpClient.get<Ticket[]>(`${this.baseURL}/view-ticket/${userName}`);
    }

    bookTicket(ticket:Ticket):Observable<any>{
        return this.httpClient.put<any>(`${this.baseURL}/ticket`,ticket, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
    }

    refuseTicket(ticket:Ticket):Observable<any>{
        return this.httpClient.put(`${this.baseURL}/refuse-ticket`,ticket, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
    }

    addTicket(ticket:Ticket):Observable<any>{
        return this.httpClient.post<any>(`${this.baseURL}/ticket`,ticket, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
    }

    deleteTicket(id:number):Observable<any>{
        return this.httpClient.delete(`${this.baseURL}/ticket/${id}`, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
    }

}