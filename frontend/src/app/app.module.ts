import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ViewTicketsComponent } from './view-tickets/view-tickets.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UpdateUserDataComponent } from './update-user-data/update-user-data.component';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { DeleteTicketComponent } from './delete-ticket/delete-ticket.component';
import { BookTicketComponent } from './book-ticket/book-ticket.component';
import { AddTicketComponent } from './add-ticket/add-ticket.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorService } from './service/httpInterceptor.service';
import { FormsModule } from '@angular/forms';
import { BookedDoctorTicketComponent } from './booked-doctor-ticket/booked-doctor-ticket.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ViewTicketsComponent,
    UserListComponent,
    UserDetailsComponent,
    UpdateUserDataComponent,
    SignupComponent,
    SigninComponent,
    DeleteTicketComponent,
    BookTicketComponent,
    AddTicketComponent,
    BookedDoctorTicketComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
