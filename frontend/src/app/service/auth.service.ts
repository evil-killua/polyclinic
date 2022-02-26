import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Request } from '../model/request.model';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user.model';

@Injectable({
	providedIn: 'root'
})
export class AuthService {
	
	private baseUrl = 'http://localhost:8080/';

	constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient) { }

	signin(request: Request): Observable<any> {
		return this.http.post<any>(this.baseUrl + 'signin', request, {headers: new HttpHeaders({ 'Content-Type': 'application/json' })}).pipe(map((resp) => {
			sessionStorage.setItem('user', request.userName);
			sessionStorage.setItem('token', 'HTTP_TOKEN ' + resp.token);
			sessionStorage.setItem("roles", JSON.stringify(resp.roles));
			sessionStorage.setItem("id",resp.id)
			return resp;
		}));
	}

	signup(request: User): Observable<any> {
		return this.http.post<any>(this.baseUrl + 'signup', request, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), responseType: 'text' as 'json'}).pipe(map((resp) => {                                                         
			return resp;
		}));
	}

	signout() {
		sessionStorage.removeItem('user');
		sessionStorage.removeItem('token');
		sessionStorage.removeItem('roles');
		sessionStorage.removeItem('id')

		this.router.navigateByUrl('signin');
	}

	isUserSignedin() {
		return sessionStorage.getItem('token') !== null;
	}

	getSignedinUser() {
		return sessionStorage.getItem('user') as string;
	}

	getToken() {
		let token = sessionStorage.getItem('token') as string;
		return token;
	}

	getRole(){
		return JSON.parse(sessionStorage.getItem('roles') || ' '); 
	}

	getId(){
		return parseInt(sessionStorage.getItem('id')!,10);
	}

}