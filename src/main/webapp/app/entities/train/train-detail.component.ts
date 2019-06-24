import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrain } from 'app/shared/model/train.model';

@Component({
  selector: 'jhi-train-detail',
  templateUrl: './train-detail.component.html'
})
export class TrainDetailComponent implements OnInit {
  train: ITrain;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ train }) => {
      this.train = train;
    });
  }

  previousState() {
    window.history.back();
  }
}
