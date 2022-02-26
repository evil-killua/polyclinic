import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { Ticket } from '../model/ticket.model';
import { BookService } from '../service/book.service';
import { ActivatedRoute } from '@angular/router';
import { DatePipe, formatDate } from '@angular/common';

@Component({
  selector: 'app-view-tickets',
  templateUrl: './view-tickets.component.html',
  styleUrls: ['./view-tickets.component.css']
})
export class ViewTicketsComponent implements OnInit {

  userName!:string;
  tickets!:Ticket[];
  actual!:Ticket[];
  overdue!:Ticket[]; 
  nowDate:Date = new Date(); 
  //param!:any;

	error: string = '';
	success: string = '';

  constructor(private route:ActivatedRoute,private bookService:BookService,
    private authService:AuthService,private router:Router) { }

  ngOnInit(): void {

    this.userName = this.authService.getSignedinUser();

    this.getTicket();

  //  console.log(typeof this.tickets)
    /*for(let t of this.tickets){
      let d = new Date(t.date);

      if(d.getTime()>=new Date().getTime()){
        this.actual.push(t)
      }else{
        this.overdue.push(t);
      }
    }*/
//    console.log(this.actual.length + " " + this.overdue.length);

  }

  check(ticket:Ticket){
    let d = new Date(ticket.date);   

    if(d.getTime() >= new Date().getTime()){
        
      return true;
    }    
    return false;
  }

  getTicket(){
    this.bookService.getTicketByUserName(this.userName).subscribe(data=>{
      console.log("data ticket")
      console.log(data)
        this.tickets = data;
    });
  }

  refuseTicket(ticket:Ticket){
    if(confirm("Are you sure you want to cancel this ticket?")) {
      console.log("refuse ticket: " + ticket);
      
      this.bookService.refuseTicket(ticket).subscribe((data:string)=>{
//        console.log("data after refuse ticket: " + data);
//        this.param = data;

        this.success = data;
        this.getTicket();
      }, (err: any) => {
				//console.log(err);
				this.error = 'Something went wrong during refuse';
			}); 
      
    }
  }

}
