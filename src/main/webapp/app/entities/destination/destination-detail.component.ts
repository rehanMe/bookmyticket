import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDestination } from 'app/shared/model/destination.model';

@Component({
  selector: 'jhi-destination-detail',
  templateUrl: './destination-detail.component.html'
})
export class DestinationDetailComponent implements OnInit {
  destination: IDestination;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ destination }) => {
      this.destination = destination;
    });
  }

  previousState() {
    window.history.back();
  }
}
