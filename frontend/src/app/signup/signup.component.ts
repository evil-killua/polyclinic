import { Component, OnInit, Output } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Request } from '../model/request.model';
import { catchError } from 'rxjs/operators';
import { User } from '../model/user.model';

//import {user_roles} from '../model/role';

@Component({
selector: 'app-signup',
templateUrl: './signup.component.html',
styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

	constructor(private authService: AuthService) { }

	id!: number;
	username: string = '';
	password: string = '';
	firstName: string = '';
	lastName: string = '';
	birthday!:Date;
	address: string = '';
	medicalCardNumber:number = 0;
	email:string='';
	phone:string='';

	/*user_roles: any = [
		{name:'User', value:'ROLE_USER', selected: false},
		{name:'Admin', value:'ROLE_ADMIN', selected: false},
		{name:'Anonymous', value:'ROLE_ANONYMOUS', selected: false},
	]*/

	selectedRoles: string[] = [];
	

	error: string = '';
	success: string = '';

	ngOnInit(): void {
		this.selectedRoles.push('ROLE_USER');
	}

	/*onChangeCategory(event: any, role: any) {
		this.selectedRoles.push(role.value);
	}*/

// this.birthday!==null && this.address !== '' && this.address !== null &&

	doSignup() {
		if(this.username !== '' && this.username !== null && this.password !== '' && this.password !== null && 
				this.firstName !== '' && this.firstName !== null && this.lastName !== '' && this.lastName !== null &&
					 this.medicalCardNumber!==0 &&
						this.email !== '' && this.email !== null && this.phone !== '' && this.phone !== null) {
			
			const request: Request = {id: this.id, userName: this.username, userPwd: this.password, roles: this.selectedRoles};
			const reqUser: User = {id: this.id, userName: this.username, userPwd: this.password, roles:this.selectedRoles,
				firstName:this.firstName,lastName:this.lastName,birthday:this.birthday.toString(),address:this.address,
					medicalCardNumber:this.medicalCardNumber,email:this.email,phone:this.phone};
			this.authService.signup(reqUser /*request*/).subscribe((result: string)=> {
				//console.log(result);
				//this.success = 'Signup successful';
				this.success = result;
			}, (err: any) => {
				//console.log(err);
				this.error = 'Something went wrong during signup';
			});
		} else {
			this.error = 'All fields are mandatory';
		}
	}

	onChangeDate(date: Date){
		console.log("дата");
		console.log(date);
	
		this.birthday = date;
			
	  }

}