import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Request } from '../model/request.model';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { User } from '../model/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users!: User[];

  error: string = '';
	success: string = '';

  constructor(private userService: UserService,private router:Router,
    private authService: AuthService) { }

  ngOnInit(): void {

    if(!this.authService.isUserSignedin()) {
			console.log("move to signin in userlist component")
      this.router.navigateByUrl('signin');
		}else{
			console.log("move to getUsers in userlist component")
      this.getUsers();
    }
    
  }

  private getUsers(){
    this.userService.getUserList().subscribe(data=>{
      console.log("users: ");
      console.log(data);
      
      this.users = data;
    });
  }

  userDetails(id:number){
    this.router.navigate(['user-details',id]);
  }

  updateUser(id:number){
    this.router.navigate(['update-user-data',id]);
  }

  deleteUser(id:number){
    this.userService.deleteUser(id).subscribe((data:string) =>{
      console.log(data);

      this.success = data;
      this.getUsers();
    }, (err: any) => {
				//console.log(err);
				this.error = 'Something went wrong during signup';
			});
  }

}
