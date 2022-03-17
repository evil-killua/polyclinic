import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { BookService } from '../service/book.service';
import {formatDate, Time} from '@angular/common';
import { Ticket } from '../model/ticket.model';
import { Answer } from '../model/answer.model';

@Component({
  selector: 'app-book-ticket',
  templateUrl: './book-ticket.component.html',
  styleUrls: ['./book-ticket.component.css']
})
export class BookTicketComponent implements OnInit {
  
  speciality!: string[];
  selectSpec: string = 'Выберите специализацию';
  selectDoc: string = 'Выберите врача';
  doctors!: string[];
  selectDate!:Date;
  limitDate = new Date();
  timeList!:Ticket[];
  time!:string;
  selectTicket!:Time;
//  param!:Answer;
//  mes:string = '';
//  des:string = '';
  error: string = '';
  success: string = '';
  
  // formatDate(Date.now(),'yyyy-MM-dd','en-US')

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

  onChangeTicket(ticketTime:Ticket){

    const ticket: Ticket= {userName: this.authService.getSignedinUser(),date: this.selectDate.toString(),
            time: ticketTime.time.toString(),doctorName: this.selectDoc};
        console.log("ticket: " + ticket);

        this.bookService.bookTicket(ticket).subscribe(data=>{
          console.log("data after booking ticket: ");
          
//          this.param = data;
          console.log(data);
//          this.mes = this.param.message;
//          this.des = this.param.description;
          this.time = ticketTime.time;
          this.success = "successfully booked";
          this.getTicket(this.selectDate,this.selectDoc);          
        }, (err: any) => {
          //console.log(err);
          this.error = 'Something went wrong during booking';
        });   
  }

  onChangeDate(date: Date){
    console.log("дата: " + date);

    this.getTicket(date,this.selectDoc);
    
  }

  private getTicket(date:Date, doc:string){
    this.bookService.getTicket(doc,date).subscribe(date=>{
      console.log("time: " + date);
      this.timeList = date;
    });
  }

  onChangeSpeciality(change:string){
    if(change!='Выберите специализацию'){
      console.log(change);
      this.getDoctors(change);
    }
  }

  onChangeDoctor(change: string){
    if(change!='Выберите врача'){
      console.log(change);
      
    }

  }

  private getDoctors(spec:string){
    return this.bookService.getDoctors(spec).subscribe(data=>{
      console.log("doctor: " + data)
        this.doctors = data; 
    });
  }

  private getSpeciality(){
    this.bookService.getSpeciality().subscribe(data=>{
      this.speciality=data;
    });
  }

}
