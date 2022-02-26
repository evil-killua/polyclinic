import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user.model';

@Component({
  selector: 'app-update-user-data',
  templateUrl: './update-user-data.component.html',
  styleUrls: ['./update-user-data.component.css']
})
export class UpdateUserDataComponent implements OnInit {

  id!: number;
  user:User = new User();
  checkPas!:string
  error: string = '';

  constructor(private userService:UserService,private route:ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {

    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(data =>{
      console.log(data)
      this.user=data;
      this.checkPas = this.user.userPwd;
    },
    error => console.log(error));

  }

  onSubmit(){

    if(this.user.userName !== '' && this.user.userName !== null && this.user.userPwd !== '' && this.user.userPwd !== null && this.user.userPwd == this.checkPas && 
    this.user.firstName !== '' && this.user.firstName !== null && this.user.lastName !== '' && this.user.lastName !== null &&
      this.user.address !== '' && this.user.address !== null && this.user.medicalCardNumber!==0 &&
        this.user.email !== '' && this.user.email !== null && this.user.phone !== '' && this.user.phone !== null) {

      this.userService.updateUser(this.id,this.user).subscribe(data =>{
        console.log("date after update user: " + data);
        this.userDetails();
      });

    }else {
			this.error = 'All fields are mandatory';
    } 
  }

  private goToUserView(){
    this.router.navigate(['view',this.user.userName]);
  }

  private userDetails(){
		this.router.navigate(['user-details',this.user.id]);
	}


}
