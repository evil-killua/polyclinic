import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../service/auth.service';
//import { GreetingService } from '../service/greeting.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
selector: 'app-home',
templateUrl: './home.component.html',
styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

	isSignedin = false;
	isAdmin = false;
	isDoctor = false;
	signedinUser: string = '';
	userId!: number;

	greeting: any[] = [];
	role!:any[];

	constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private authService: AuthService) {}

	ngOnInit() {
		this.isSignedin = this.authService.isUserSignedin();
		this.signedinUser = this.authService.getSignedinUser();
		this.userId = this.authService.getId();

		console.log(this.userId);
		

		if(!this.authService.isUserSignedin()) {
			this.router.navigateByUrl('signin');
		}

		this.getRole();
		console.log("type")
		console.log(typeof this.role);
		for(let r of this.role){
			console.log(r);
			if(r === 'ROLE_ADMIN'){
				this.isAdmin = true;
			}else if(r === 'ROLE_DOCTOR'){
				this.isDoctor = true;
			}
		}


	}

	bookedTicket(){
		this.router.navigate(['booked-ticket',this.signedinUser]);
	}

	userDetails(){
		this.router.navigate(['user-details',this.userId]);
	}

/*	view(){
		this.router.navigate(['view',this.signedinUser]);
	}*/	

	tickets(){
		this.router.navigate(['view-ticket',this.signedinUser]);
	}

	doSignout() {
		this.authService.signout();
	}

	speciality(){
		this.router.navigateByUrl('speciality');
	}

	showUsers(){
		this.router.navigateByUrl('users');
	}

	addTicket(){
		this.router.navigateByUrl('add-ticket');
	}

	getRole(){
		this.role = this.authService.getRole();
	}

	deleteTicket(){
		this.router.navigateByUrl("delete-ticket");
	}

}