import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { BookService } from '../service/book.service';
import {formatDate, Time} from '@angular/common';
import { Ticket } from '../model/ticket.model';
import { Answer } from '../model/answer.model';

@Component({
  selector: 'app-add-ticket',
  templateUrl: './add-ticket.component.html',
  styleUrls: ['./add-ticket.component.css']
})
export class AddTicketComponent implements OnInit {

  speciality!: string[];
  selectSpec: string = 'Выберите специализацию';
  selectDoc: string = 'Выберите врача';
  doctors!: string[];
  selectDate!:Date;
  selectTime!:Date;
  limitDate = new Date();
//  param!:Answer;
//  mes:string = '';
//  des:string = '';

error: string = '';
success: string = '';

  constructor(private bookService: BookService,private router:Router,
    private authService: AuthService) { }

  ngOnInit(): void {
    if(!this.authService.isUserSignedin()) {
			console.log("move to signin in userlist component")
      this.router.navigateByUrl('signin');
		}else{
			console.log("move to getUsers in userlist component")
      this.getSpeciality();
    }
  }

  private getSpeciality(){
    this.bookService.getSpeciality().subscribe(data=>{
      this.speciality=data;
    });
  }

  onChangeSpeciality(change:string){
    if(change!='Выберите специализацию'){
      console.log(change);
      this.getDoctors(change);
    }
  }

  private getDoctors(spec:string){
    return this.bookService.getDoctors(spec).subscribe(data=>{
      console.log("doctor: " + data)
        this.doctors = data; 
    });
  }

  onChangeDoctor(change: string){
    if(change!='Выберите врача'){
      console.log(change);
      
    }

  }

  onChangeDate(date: Date){
    console.log("дата");
    console.log(date);

    this.selectDate = date;
        
  }

  onChangeTime(time:Date){
    console.log("time: " + time.toString());
  }

  onChangeTicket(){
    if(this.selectDoc!="Выберите врача" && this.selectDoc!="" && this.selectDoc!=null &&
          this.selectSpec!="Выберите специализацию" && this.selectSpec!="" && this.selectSpec!=null &&
              this.selectDate!=null && this.selectTime!=null){
                const userName:string = this.authService.getSignedinUser();
                const ticket:Ticket = {userName:userName, doctorName:this.selectDoc,
                      date:this.selectDate.toString(),time:this.selectTime.toString()};
               
                console.log(ticket);
            
            
                this.bookService.addTicket(ticket).subscribe(data=>{
                  console.log("data from add: ");
                  console.log(data)
                  //this.param = data;
                  //this.mes = this.param.message;
                  //this.des = this.param.description;
                  this.success = "successfully created";

                }, (err: any) => {
				          //console.log(err);
				          this.error = 'Something went wrong during signup';
			          });
              }else{
                this.error="not all options are selected";
              }




//    console.log(this.param);
    

  }
}
