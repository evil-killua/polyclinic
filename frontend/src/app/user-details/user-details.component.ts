import { Component, OnInit } from '@angular/core';
import { Request } from '../model/request.model';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user.model';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  id!:number;
  user!:User;

  constructor(private route:ActivatedRoute,private userService:UserService,private router:Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.user = new User();
    this.userService.getUserById(this.id).subscribe(data=>{
      this.user = data
    });
  }

  updateUser(id:number){
    this.router.navigate(['update-user-data',id]);
  }

}
