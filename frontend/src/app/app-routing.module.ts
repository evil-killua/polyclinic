import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTicketComponent } from './add-ticket/add-ticket.component';
import { BookTicketComponent } from './book-ticket/book-ticket.component';
import { BookedDoctorTicketComponent } from './booked-doctor-ticket/booked-doctor-ticket.component';
import { DeleteTicketComponent } from './delete-ticket/delete-ticket.component';
import { HomeComponent } from './home/home.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { UpdateUserDataComponent } from './update-user-data/update-user-data.component';
//import { UpdateUserComponent } from './update-user/update-user.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UserListComponent } from './user-list/user-list.component';
//import { UserViewComponent } from './user-view/user-view.component';
import { ViewTicketsComponent } from './view-tickets/view-tickets.component';

const routes: Routes = [
	{ path: '', redirectTo: '/home', pathMatch: 'full' },
	{ path: 'home', component: HomeComponent },
	{ path: 'signin', component: SigninComponent },
	{ path: 'signup', component: SignupComponent },
	{ path: 'users', component: UserListComponent},
//	{path:'update-user/:id',component:UpdateUserComponent},
	{path:'update-user-data/:id',component:UpdateUserDataComponent},
	{path:'user-details/:id',component:UserDetailsComponent},
	{path:'speciality',component:BookTicketComponent},
//	{path:'view/:userName',component:UserViewComponent},
	{path:'view-ticket/:userName',component:ViewTicketsComponent},
	{path:'add-ticket',component:AddTicketComponent},
	{path:'delete-ticket',component:DeleteTicketComponent},
	{path:'booked-ticket/:userName',component:BookedDoctorTicketComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
