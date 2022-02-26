import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookedDoctorTicketComponent } from './booked-doctor-ticket.component';

describe('BookedDoctorTicketComponent', () => {
  let component: BookedDoctorTicketComponent;
  let fixture: ComponentFixture<BookedDoctorTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookedDoctorTicketComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookedDoctorTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
