import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ticket } from '../model/ticket.model';
import { AuthService } from '../service/auth.service';
import { BookService } from '../service/book.service';

@Component({
  selector: 'app-booked-doctor-ticket',
  templateUrl: './booked-doctor-ticket.component.html',
  styleUrls: ['./booked-doctor-ticket.component.css']
})
export class BookedDoctorTicketComponent implements OnInit {

  userName!:string;
  tickets!:Ticket[];
  selectDate!:Date;

  constructor(private route:ActivatedRoute,private bookService:BookService,
    private authService:AuthService,private router:Router) { }

  ngOnInit(): void {
    this.userName = this.authService.getSignedinUser();


  }

  onChangeDate(date: Date){
    console.log("дата: " + date);

    this.getBookedTicket(date,this.userName);
  }

  private getBookedTicket(date:Date, doc:string){
    this.bookService.getBookedTicket(doc,date).subscribe(date=>{
      console.log("time: " + date);
      this.tickets = date;
    });
  }

}
