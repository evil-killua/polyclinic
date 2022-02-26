import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Request } from "../model/request.model";
import { User } from "../model/user.model";
import { map } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class UserService{

    private baseURL = "http://localhost:8080/users";

    constructor(private httpClient: HttpClient){}

    getUserList():Observable<User[]> {
        return this.httpClient.get<User[]>(`${this.baseURL}`);
    }

    getUserById(id:number):Observable<User>{
        return this.httpClient.get<User>(`${this.baseURL}/${id}`);
    }

    getUserByUserName(userName:string):Observable<User>{
        return this.httpClient.get<User>(`${this.baseURL}/view/${userName}`);
    }

    updateUser(id:number,user:User):Observable<User>{
        return this.httpClient.put<User>(`${this.baseURL}/${id}`,user);
    }

    deleteUser(id:number):Observable<any>{
        return this.httpClient.delete(`${this.baseURL}/${id}`, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
    }
}