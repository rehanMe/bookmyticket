import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookings } from 'app/shared/model/bookings.model';

@Component({
  selector: 'jhi-bookings-detail',
  templateUrl: './bookings-detail.component.html'
})
export class BookingsDetailComponent implements OnInit {
  bookings: IBookings;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookings }) => {
      this.bookings = bookings;
    });
  }

  previousState() {
    window.history.back();
  }
}
