import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirplane } from 'app/shared/model/airplane.model';

@Component({
  selector: 'jhi-airplane-detail',
  templateUrl: './airplane-detail.component.html'
})
export class AirplaneDetailComponent implements OnInit {
  airplane: IAirplane;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ airplane }) => {
      this.airplane = airplane;
    });
  }

  previousState() {
    window.history.back();
  }
}
